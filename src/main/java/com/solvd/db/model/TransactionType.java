package com.solvd.db.model;

public class TransactionType {

    private int transactionTypeId;
    private String transactionType;

    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "TransactionType{" +
                "transactionTypeId=" + transactionTypeId +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
