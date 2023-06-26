package com.solvd.observer.monitor;

import com.solvd.db.model.Bank;
import com.solvd.db.service.AccountService;
import com.solvd.observer.Observer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InterestMonitor extends Monitor<Double> {

    private static final Logger LOGGER = LogManager.getLogger(InterestMonitor.class);
    private Bank bank;

    public InterestMonitor(Bank bank) {
        this.bank = bank;
    }

    @Override
    public Double getState() {
        return bank.getInterestSavingsAccount();
    }

    public void setState(Double interestSavingsAccount) {
        bank.setInterestSavingsAccount(interestSavingsAccount);
        notifyAllObservers();
    }

    @Override
    public void subscribe(Observer observer) {
        super.observers.add(observer);
    }
}
