package com.solvd.db.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private int customerId;
    private Person person;
    private List<Account> accounts = new ArrayList<>();

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", person=" + person +
                ", accounts=" + accounts +
                '}';
    }
}
