package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IAccountDAO;
import com.solvd.db.dao.interfaces.ICustomerDAO;
import com.solvd.db.factory.JdbcDAOFactory;
import com.solvd.db.model.Account;
import com.solvd.db.model.Customer;

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
}
