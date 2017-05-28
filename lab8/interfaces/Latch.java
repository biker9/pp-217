package ru.rsreu.novikov.lab8.interfaces;

public interface Latch {

    void countDown();

    void await() throws InterruptedException;

}
