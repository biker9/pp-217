package ru.rsreu.novikov.lab8.interfaces;

import java.util.concurrent.TimeoutException;

public interface Lock {

    void lock() throws InterruptedException;

    void lock(long timeout) throws InterruptedException;

    void unlock();

}
