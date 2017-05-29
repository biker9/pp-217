package ru.rsreu.novikov.lab8;

import ru.rsreu.novikov.lab8.interfaces.Lock;

import java.util.concurrent.TimeoutException;

public class CustomLock implements Lock {

    private boolean locked;

    public CustomLock() {
        this.locked = false;
    }

    @Override
    public synchronized void lock() throws InterruptedException {
        if (locked) {
            wait();
        }

        locked = true;
    }

    @Override
    public synchronized void lock(long timeout) throws InterruptedException {
        if (locked) {
            wait(timeout);
        }

        locked = true;
    }

    @Override
    public synchronized void unlock() {
        locked = false;
        notify();
    }
}
