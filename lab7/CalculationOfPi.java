package ru.rsreu.novikov.lab7;

public class CalculationOfPi {

    private ProgressBar progressBar;

    public CalculationOfPi() {
        progressBar = new ProgressBar();
    }

    public static double calculationPi(long generatedPoints, long enteredPoints) {
        return 4 * (double) enteredPoints / generatedPoints;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public double calcPi(long pointCount) {
        long enteredPoints = countEntry(pointCount);
        return calculationPi(pointCount, enteredPoints);
    }

    public long countEntry(long pointCount) {
        long counter = 0;
        for (int i = 0; i < pointCount; i++) {
            if (Thread.currentThread().isInterrupted()) {
                return counter;
            }

            Double x = Math.random() * 2 - 1;
            Double y = Math.random() * 2 - 1;

            if (Math.pow(x, 2) + Math.pow(y, 2) <= 1) {
                counter++;
            }

            progressBar.increase(1.0 / pointCount * ProgressBar.MAX_PROGRESS);
        }

        return counter;
    }

}
