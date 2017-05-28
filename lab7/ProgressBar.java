package ru.rsreu.novikov.lab7;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class ProgressBar {

    public static final double MIN_PROGRESS = 0.0;
    public static final double MAX_PROGRESS = 100.0;

    private double progress;


    private Lock lock;

    private double notificationStep;
    private double notificationCounter;

    private Consumer<Double> onChangeListener;

    public ProgressBar(double notificationStep) {
        this.lock = new ReentrantLock();
        this.notificationCounter = 0;
        this.setNotificationStep(notificationStep);
        this.reset();
    }

    public ProgressBar() {
        this(0);
    }

    public double getProgress() {
        return progress;
    }

    private void setNotificationStep(double notificationStep) {
        if (notificationStep < 0) {
            notificationStep = 0;
        }

        this.notificationStep = notificationStep;
    }

    public void increase(double value) {
        lock.lock();
        try {
            if (value == 0) {
                return;
            }

            final double changed = progress + value > MAX_PROGRESS ? MAX_PROGRESS - progress : value;

            progress += changed;
            notificationCounter += changed;

            if (onChangeListener != null && (notificationStep == 0 || notificationCounter >= notificationStep)) {
                onChangeListener.accept(progress);
                notificationCounter = notificationCounter - notificationStep;
            }
        } finally {
            lock.unlock();
        }
    }

    public void reset() {
        lock.lock();
        try {
            progress = MIN_PROGRESS;
        } finally {
            lock.unlock();
        }
    }

    public void setOnChangeListener(double notificationStep, Consumer<Double> onChangeListener) {
        this.setNotificationStep(notificationStep);
        this.onChangeListener = onChangeListener;
    }
}