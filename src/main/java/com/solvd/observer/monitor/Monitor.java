package com.solvd.observer.monitor;

import com.solvd.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Monitor<T> {

    List<Observer> observers = new ArrayList<>();

    public abstract T getState();

    public abstract void subscribe(Observer observer);

    public void notifyAllObservers() {
        observers.forEach(Observer::update);
    }
}
