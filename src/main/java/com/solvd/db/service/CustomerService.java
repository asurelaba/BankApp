package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.ICustomerDAO;
import com.solvd.db.dao.interfaces.ICustomersHasAccountsDAO;
import com.solvd.db.factory.JdbcDAOFactory;
import com.solvd.db.model.*;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private JdbcDAOFactory jdbcDAOFactory = new JdbcDAOFactory();

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            ICustomerDAO customerDAO = (ICustomerDAO) jdbcDAOFactory.getDAO(Customer.class.getSimpleName());
            for (Customer customer : customerDAO.getAll()) {
                customers.add(customer);
            }
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
        return customers;
    }

    public void createCustomer(Customer customer) {
        try {
            PersonService personService = new PersonService();
            personService.createPerson(customer.getPerson());
            ICustomerDAO customerDAO = (ICustomerDAO) jdbcDAOFactory.getDAO(Customer.class.getSimpleName());
            customerDAO.insert(customer);
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
    }

    public Customer getCustomerWithAccounts(int customerId) {
        Customer customer = new Customer();
        try {
            ICustomerDAO customerDAO = (ICustomerDAO) jdbcDAOFactory.getDAO(Customer.class.getSimpleName());
            customer = customerDAO.getById(customerId);
            ICustomersHasAccountsDAO customersHasAccountsDAO = (ICustomersHasAccountsDAO) jdbcDAOFactory.getDAO(CustomerHasAccount.class.getSimpleName());
            List<Account> accounts = customersHasAccountsDAO.getAccountsByCustomerID(customerId);
            customer.setAccounts(accounts);
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
        return customer;
    }
}
