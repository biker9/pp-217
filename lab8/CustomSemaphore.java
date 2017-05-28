package ru.rsreu.novikov.lab8;

import ru.rsreu.novikov.lab8.interfaces.Semaphore;

public class CustomSemaphore implements Semaphore {

    private int permits;

    public CustomSemaphore(int permits) {
        this.permits = permits;
    }

    @Override
    public synchronized void acquire() throws InterruptedException {
        if (permits <= 0) {
            wait();
        }

        permits--;
    }

    @Override
    public synchronized void release() {
        permits++;

        notify();
    }
}
