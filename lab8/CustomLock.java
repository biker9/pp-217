package ru.rsreu.novikov.lab8;

import ru.rsreu.novikov.lab8.interfaces.Lock;

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
    public synchronized void unlock() {
        locked = false;
        notify();
    }
}
