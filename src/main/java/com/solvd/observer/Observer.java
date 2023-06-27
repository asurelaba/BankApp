package com.solvd.observer;

import com.solvd.observer.monitor.Monitor;

public abstract class Observer<T> {

    protected Monitor<T> monitor;

    public abstract void update();
}
