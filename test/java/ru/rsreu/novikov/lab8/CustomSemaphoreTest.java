package ru.rsreu.novikov.lab8;

import org.junit.Assert;
import org.junit.Test;
import ru.rsreu.novikov.lab8.interfaces.Semaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomSemaphoreTest {
    @Test
    public void acquire() throws Exception {
        final int threadLimit = 2;

        Semaphore semaphore = new CustomSemaphore(threadLimit);
        AtomicInteger counter = new AtomicInteger(0);
        AtomicInteger max = new AtomicInteger(0);

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadLimit + 2; i++) {
            threads.add(new Thread(() -> {
                try {
                    semaphore.acquire();
                    counter.incrementAndGet();

                    max.updateAndGet(v -> Integer.max(v, counter.get()));

                    semaphore.release();
                    counter.decrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));

            threads.get(threads.size() - 1).start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        Assert.assertTrue(max.get() <= threadLimit);
    }

}