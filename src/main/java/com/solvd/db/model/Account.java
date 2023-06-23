package com.solvd.db.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Account {

    @XmlElement(name = "accountNumber")
    @JsonProperty("accountNumber")
    private int accountNumber;

    @XmlElement(name = "balance")
    @JsonProperty("balance")
    private double balance;

    @XmlElement(name = "minbalance")
    @JsonProperty("minBalance")
    private double minBalance;

    @XmlElement(name = "accountType")
    @JsonProperty("accountType")
    private AccountType accountType;

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(double minBalance) {
        this.minBalance = minBalance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                ", minBalance=" + minBalance +
                ", accountType=" + accountType +
                '}';
    }
}
