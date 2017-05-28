package ru.rsreu.novikov.lab8;

import ru.rsreu.novikov.lab8.interfaces.Latch;

public class CustomLatch implements Latch {

    private int count;

    public CustomLatch(int count) {
        this.count = count;
    }

    @Override
    public synchronized void countDown() {
        count--;

        if (count <= 0) {
            notifyAll();
        }
    }

    @Override
    public synchronized void await() throws InterruptedException {
        if (count > 0) {
            wait();
        }
    }
}
