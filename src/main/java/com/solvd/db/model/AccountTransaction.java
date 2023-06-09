package com.solvd.db.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.xml.bind.annotation.*;
import java.sql.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountTransaction {

    @XmlAttribute(name = "id")
    @JsonProperty("transactionId")
    private int transactionId;

    @XmlElement(name = "amount")
    @JsonProperty("amount")
    private int amount;

    @XmlElement(name = "transactionDate")
    @JsonProperty("transactionDate")
    @JsonFormat()
    private Date transactionDate;

    @XmlElement(name = "account")
    @JsonProperty("account")
    private Account account;

    @XmlElement(name = "transactionType")
    @JsonProperty("transactionType")
    private TransactionType transactionType;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "AccountTransaction{" +
                "transactionId=" + transactionId +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", account=" + account +
                ", transactionType=" + transactionType +
                '}';
    }
}
