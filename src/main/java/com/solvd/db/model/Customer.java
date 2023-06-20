package com.solvd.db.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {

    @XmlAttribute(name = "id")
    @JsonProperty("customerId")
    private int customerId;

    @XmlElement(name = "person")
    @JsonProperty("person")
    private Person person;

    @XmlElementWrapper(name = "accounts")
    @XmlElement(name = "account", type = Account.class)
    @JsonProperty("accounts")
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
                "customerId=" + customerId + "\n" +
                ", person=" + person + "\n" +
                ", accounts=" + accounts + "\n" +
                '}';
    }
}
