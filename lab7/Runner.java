package ru.rsreu.novikov.lab7;

public class Runner {

    public static void main(String[] args) {
        MultiThreadingCalcOfPi monteCarlo = new MultiThreadingCalcOfPi(10000000L, 10);
        try {
            System.out.println(monteCarlo.startCalcPi(10));
        } catch (InterruptedException e) {
            System.out.println("The thread was interrupted");
        }

        System.exit(0);
    }

}
