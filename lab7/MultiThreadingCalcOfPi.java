package ru.rsreu.novikov.lab7;

import ru.rsreu.novikov.lab8.CustomLatch;
import ru.rsreu.novikov.lab8.CustomSemaphore;
import ru.rsreu.novikov.lab8.interfaces.Latch;
import ru.rsreu.novikov.lab8.interfaces.Semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadingCalcOfPi {

    private ProgressBar overallProgress;

    private long complexity;
    private int threadCount;

    private ExecutorService service;

    public MultiThreadingCalcOfPi(long complexity, int threadCount) {
        this.overallProgress = new ProgressBar();

        this.overallProgress.setOnChangeListener(10,
                p -> System.out.printf("The calculation is %d%%\n", Math.round(p)));

        this.complexity = complexity;
        this.threadCount = threadCount;
        this.service = Executors.newFixedThreadPool(threadCount);
    }

    public double startCalcPi() throws InterruptedException {
        return this.startCalcPi(threadCount);
    }

    public double startCalcPi(int threadLimit) throws InterruptedException {
        long threadComplexity = complexity / threadCount;

        List<Future<Long>> results = new ArrayList<>();

        Latch latch = new CustomLatch(threadCount);
        Semaphore semaphore = new CustomSemaphore(threadLimit);

        for (int i = 0; i < threadCount; i++) {
            Future<Long> future = service.submit(() -> {
                long entry;
                try {
                    semaphore.acquire();

                    System.out.printf("Task in thread %s started\n", Thread.currentThread().getName());

                    CalculationOfPi monteCarlo = new CalculationOfPi();

                    monteCarlo.getProgressBar().setOnChangeListener(5,
                            p -> overallProgress.increase(5 / (double) threadCount));

                    entry = monteCarlo.countEntry(threadComplexity);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return 0L;
                } finally {
                    semaphore.release();
                }

                System.out.printf("Task in thread %s completed\n", Thread.currentThread().getName());

                latch.countDown();
                long start = System.currentTimeMillis();
                try {
                    latch.await();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return entry;
                }
                System.out.printf("For task in thread %s, interval = %d ms.\n",
                        Thread.currentThread().getName(), System.currentTimeMillis() - start);

                return entry;
            });

            results.add(future);
        }

        long totalResult = 0;
        for (int i = 0; i < results.size(); i++) {
            try {
                Future<Long> future = results.get(i);

                totalResult += future.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        service.shutdown();

        return CalculationOfPi.calculationPi(complexity, totalResult);
    }

}
