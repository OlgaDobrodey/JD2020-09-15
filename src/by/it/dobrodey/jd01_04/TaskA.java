package by.it.dobrodey.jd01_04;

import java.util.Arrays;
import java.util.Scanner;

public class TaskA {
    static void printMulTable() {
        for (int i = 2; i < 10; i++) {
            for (int j = 2; j < 10; j++) {
                System.out.printf("%1d*%1d=%-3d", i, j, i * j);
            }
            System.out.println();
        }
    }

    static void buildOneDimArray(String line) {
        double[] array = InOut.getArray(line);
        InOut.printArray(array, "V", 5);
        double first = array[0];
        double finish = array[array.length - 1];
        Helper.sort(array);
        InOut.printArray(array, "V", 4);
        int indexFirst = Arrays.binarySearch(array, first);
        System.out.println("Index of first element=" + indexFirst);
        int indexFinish = Arrays.binarySearch(array, finish);
        System.out.println("Index of last element=" + indexFinish);
    }

    public static void main(String[] args) {
        System.out.println("Input line:");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        printMulTable();
        buildOneDimArray(line);
    }
}
