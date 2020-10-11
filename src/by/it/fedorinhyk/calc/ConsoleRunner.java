package by.it.fedorinhyk.calc;

import java.util.Scanner;

public class ConsoleRunner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Parser parser = new Parser();
        Printer printer = new Printer();
        for (; ; ) {
            for (; ; ) {
                String expression = sc.nextLine();
                if (expression.equals("printvar")) {
                    printer.printvar(Var.getVarMap()); continue;
                }
                if (expression.equals("end")) break;
                Var result = parser.calc(expression);
                printer.print(result);
            }
        }
    }
}
