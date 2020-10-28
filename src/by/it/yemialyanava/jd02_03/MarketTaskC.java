package by.it.yemialyanava.jd02_03;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MarketTaskC {
    private static ExecutorService fixedThreadPoolCashers;
    private static List<Cashier> cachiersList = new ArrayList<>();
    private static List<Thread> buyers = new ArrayList<>();
    //private static Thread consoleWriterThrtead = new ConsoleWriter(cachiersList);
    private static int buyerNumber = 0;

    public static void main(String[] args) {

        System.out.println("Market opened");
        fixedThreadPoolCashers = createCashiers();
        int minute = 0;

        while (minute <= 2 && Supervisor.marketIsOpened()) {
            for (int second = 1; second <= 60; second++) {
                //BUYERS
                int actualCountNewBuyers = calculateNewBuyersForCurrentSecond(second);
                createBuyers(actualCountNewBuyers);
                //System.out.println(second + " - second, buyers : " + Supervisor.countActualNumberOfBuyer()
                  //      + " buyers correction " + actualCountNewBuyers);
                //CASHIERS
                int countCashier = calculateDemandedCashiersForCurrentQueue();
                synchronized (Cashier.pause) {
                    for (int i = 1; i <= countCashier; i++) {
                        Cashier.pause.notify();
                    }
                }
                Helper.timeout(1000);
            }
            minute++;
        }

        awaitCashierAndBuyersTermination();
        System.out.println("Market closed");
    }

    private static int calculateDemandedCashiersForCurrentQueue() {
        int countCashier;
        int commonBuyersInTwoQueue = QueueBuyers.countBuyerInQueue() + QueueBuyers.countBuyerInQueuePensioneer();
        int buyerDemandNewCasher = commonBuyersInTwoQueue - (Supervisor.cashierWorkNow() * 5);
        int demandedCasher = buyerDemandNewCasher / 5 + 1;
        if (Supervisor.cashierWorkNow() + demandedCasher <= 5) {
            countCashier = demandedCasher;
        } else {
            countCashier = 5 - Supervisor.cashierWorkNow();
        }
        return countCashier;
    }

    private static void awaitCashierAndBuyersTermination() {
        for (Thread t : buyers) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        while (true) {
            try {
                if (fixedThreadPoolCashers.awaitTermination(1, TimeUnit.SECONDS))
                    break;
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        /*try {
            consoleWriterThrtead.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
    }

    private static void createBuyers(int actualCountNewBuyers) {
        for (int i = 0; i < actualCountNewBuyers; i++) {
            if (Supervisor.marketIsOpened()) {
                boolean pensionerLiOn = (buyerNumber + 1) % 4 == 0;
                Buyer buyer = new Buyer(++buyerNumber, pensionerLiOn);
                buyer.start();
                buyers.add(buyer);
            }
        }
    }

    private static int calculateNewBuyersForCurrentSecond(int second) {
        int actualCountNewBuyers = 0;
        if (second == 1) {
            actualCountNewBuyers = 10;
        } else if ((second > 1 && second <= 30) ) {
            actualCountNewBuyers = second + 10 - Supervisor.countActualNumberOfBuyer();
        } else if ((second > 30 && second <= 60)) {
            actualCountNewBuyers = 40 + (30 - second) - Supervisor.countActualNumberOfBuyer();
        }
        return actualCountNewBuyers;
    }

    private static ExecutorService createCashiers() {
        ExecutorService fixedThreadPoolCashers = Executors.newFixedThreadPool(5);
        for (int i = 1; i <= 5; i++) {
            Cashier cashier = new Cashier(i);
            fixedThreadPoolCashers.execute(cashier);
            cachiersList.add(cashier);
        }
        fixedThreadPoolCashers.shutdown();
        return fixedThreadPoolCashers;

    }
}


