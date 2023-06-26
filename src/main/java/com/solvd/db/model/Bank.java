package com.solvd.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bank {

    @XmlElementWrapper(name = "customers")
    @XmlElement(name = "customer", type = Customer.class)
    @JsonProperty("customers")
    List<Customer> customers;

    @XmlElementWrapper(name = "employees")
    @XmlElement(name = "employee", type = Employee.class)
    @JsonProperty("employees")
    List<Employee> employees;

    @XmlElementWrapper(name = "accounts")
    @XmlElement(name = "account", type = Account.class)
    @JsonProperty("accounts")
    List<Account> accounts;

    @XmlTransient
    @JsonIgnore
    private Double interestSavingsAccount;

    public Bank() {
    }

    public Bank(BankBuilder bankBuilder) {
        this.accounts = bankBuilder.accounts;
        this.employees = bankBuilder.employees;
        this.customers = bankBuilder.customers;
    }

    public static class BankBuilder {
        List<Customer> customers;
        List<Employee> employees;
        List<Account> accounts;

        public BankBuilder withCustomers(List<Customer> customers) {
            this.customers = customers;
            return this;
        }

        public BankBuilder withEmployees(List<Employee> employees) {
            this.employees = employees;
            return this;
        }

        public BankBuilder withAccounts(List<Account> accounts) {
            this.accounts = accounts;
            return this;
        }

        public Bank build() {
            return new Bank(this);
        }
    }

    public Double getInterestSavingsAccount() {
        return interestSavingsAccount;
    }

    public void setInterestSavingsAccount(Double interestSavingsAccount) {
        this.interestSavingsAccount = interestSavingsAccount;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "customers=" + customers + "\n" +
                ", employees=" + employees + "\n" +
                ", accounts=" + accounts + "\n" +
                '}';
    }
}
