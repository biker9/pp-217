package ru.rsreu.novikov.lab8.interfaces;

import java.util.concurrent.TimeoutException;

public interface Latch {

    void countDown();

    void await() throws InterruptedException;

    void await(long timeout) throws InterruptedException;

}
