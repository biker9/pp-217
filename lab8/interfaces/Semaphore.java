package ru.rsreu.novikov.lab8.interfaces;

import java.util.concurrent.TimeoutException;

public interface Semaphore {

    void acquire() throws InterruptedException;

    void acquire(long timeout) throws InterruptedException;

    void release();

}
