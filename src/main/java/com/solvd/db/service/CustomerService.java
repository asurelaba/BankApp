package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.ICustomerDAO;
import com.solvd.db.factory.AbstractDAOFactory;
import com.solvd.db.factory.DAOFactoryManager;
import com.solvd.db.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private static final Logger LOGGER = LogManager.getLogger(CustomerService.class);
    private AbstractDAOFactory daoFactory = DAOFactoryManager.getDAOFactoryInstance();
    private ICustomerDAO customerDAO;

    public CustomerService() throws DAONotFoundException {
        customerDAO = (ICustomerDAO) daoFactory.getDAO(Customer.class.getSimpleName());
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (Customer customer : customerDAO.getAll()) {
            customers.add(customer);
        }
        return customers;
    }

    public void createCustomer(Customer customer) throws DAONotFoundException {
        PersonService personService = new PersonService();
        personService.createPerson(customer.getPerson());
        customerDAO.insert(customer);
    }

    public Customer getCustomerWithAccounts(int customerId) {
        return customerDAO.getById(customerId);
    }

    public void deleteCustomer(Customer customer) {
        customerDAO.delete(customer);
    }

    public Customer getCustomerByPhone(String phoneNumber) {
        return customerDAO.getCustomerByPhone(phoneNumber);
    }
}
