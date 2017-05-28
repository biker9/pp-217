package ru.rsreu.novikov.lab8.interfaces;

public interface Semaphore {

    void acquire() throws InterruptedException;

    void release();

}
