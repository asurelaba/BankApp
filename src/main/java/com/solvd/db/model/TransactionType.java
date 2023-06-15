package com.solvd.db.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonRootName("transactionType")
public class TransactionType {

    @XmlAttribute(name = "id")
    @JsonProperty("transactionTypeId")
    private int transactionTypeId;

    @XmlElement(name = "transactionType")
    @JsonProperty("transactionType")
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
