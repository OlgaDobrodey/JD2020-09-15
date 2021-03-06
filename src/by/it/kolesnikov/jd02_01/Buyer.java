package by.it.kolesnikov.jd02_01;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Buyer extends Thread implements IBuyer, IUseBasket {
    public static final Lock lock = new ReentrantLock();;

    public Buyer(int number){

        this.setName("Buyer №"+number);
    }

    @Override
    public void run() {
        enterToMarket();
        takeBasket();
        choseGoods();
        putGoodsToBasket();
        goOut();
    }

    @Override
    public void takeBasket(){
        System.out.println(this+" took basket");
    }

    @Override
    public void putGoodsToBasket(){
        StringBuffer sb =new StringBuffer();
        List<String> goods = new ArrayList<>();
        List<String> basketGoods = new ArrayList<>();
        List<Integer> prices = new ArrayList<>();
        List<Integer> price = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : Good.goods().entrySet()){
            String el = entry.getKey();
            Integer pr = entry.getValue();
            goods.add(el);
            prices.add(pr);
        }
        int count = Helper.getRandom(1,4);
        String delimiter = "";
        int sum=0;
        for (int i = 0; i < count; i++) {
            int rnd = Helper.getRandom(3);
            String good = goods.get(rnd);
            int pr = prices.get(rnd);
            basketGoods.add(good);
            price.add(pr);
            sum=sum+price.get(i);
            sb.append(delimiter);
            sb.append(basketGoods.get(i));
            delimiter=", ";
        }
        System.out.println(this+" put next goods to basket: "+sb+": costs $"+sum);
    }

    @Override
    public String toString() {

        return getName();
    }

    @Override
    public void enterToMarket() {
        System.out.println(this+" entered to Market");
    }

    @Override
    public void choseGoods() {
        System.out.println(this+" started to choose goods");
        int timeout=Helper.getRandom(100, 200);
        Helper.timeOut(timeout);
        System.out.println(this+" finished to choose goods");
    }

    @Override
    public void goOut() {
        System.out.println(this+" went out of Market");
    }

}
