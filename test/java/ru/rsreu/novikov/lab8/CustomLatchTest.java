package ru.rsreu.novikov.lab8;

import org.junit.Assert;
import org.junit.Test;
import ru.rsreu.novikov.lab8.interfaces.Latch;

import java.util.concurrent.atomic.AtomicInteger;


public class CustomLatchTest {

    @Test
    public void await() throws Exception {

        Latch latch = new CustomLatch(5);
        AtomicInteger counter = new AtomicInteger(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                latch.countDown();
                counter.decrementAndGet();
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Assert.assertTrue(counter.get() == 0);
            }).start();
        }
    }
}