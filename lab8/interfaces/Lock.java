package ru.rsreu.novikov.lab8.interfaces;

public interface Lock {

    void lock() throws InterruptedException;

    void unlock();

}
