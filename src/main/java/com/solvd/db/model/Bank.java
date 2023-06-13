package com.solvd.db.model;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bank {

    @XmlElementWrapper(name = "customers")
    @XmlElement(name = "customer", type = Customer.class)
    List<Customer> customers;

    @XmlElementWrapper(name = "employees")
    @XmlElement(name = "employee", type = Employee.class)
    List<Employee> employees;

    @XmlElementWrapper(name = "accounts")
    @XmlElement(name = "account", type = Account.class)
    List<Account> accounts;

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
}
