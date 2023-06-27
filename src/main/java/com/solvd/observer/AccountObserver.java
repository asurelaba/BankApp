package com.solvd.observer;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.model.Account;
import com.solvd.db.service.AccountService;
import com.solvd.observer.monitor.InterestMonitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AccountObserver extends Observer<Double> {

    private static final Logger LOGGER = LogManager.getLogger(AccountObserver.class);

    public AccountObserver(InterestMonitor interestMonitor) {
        monitor = interestMonitor;
        monitor.subscribe(this);
    }

    //update all accounts to add the new interest
    public void update() {
        System.out.println("in observer update " + monitor.getState());
        try {
            AccountService accountService = new AccountService();
            List<Account> accounts = accountService.getAllAccounts();

            for (Account account : accounts) {
                double newBalance = account.getBalance() + account.getBalance() * monitor.getState();
                account.setBalance(newBalance);
                accountService.updateAccount(account);
            }
        } catch (DAONotFoundException e) {
            LOGGER.error("Cannot update the accounts, account service is not working" + e);
        }
    }
}
