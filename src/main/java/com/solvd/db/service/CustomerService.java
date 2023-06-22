package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.ICustomerDAO;
import com.solvd.db.dao.interfaces.ICustomersHasAccountsDAO;
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

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            ICustomerDAO customerDAO = (ICustomerDAO) daoFactory.getDAO(Customer.class.getSimpleName());
            for (Customer customer : customerDAO.getAll()) {
                customers.add(customer);
            }
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
        return customers;
    }

    public void createCustomer(Customer customer) {
        try {
            PersonService personService = new PersonService();
            personService.createPerson(customer.getPerson());
            ICustomerDAO customerDAO = (ICustomerDAO) daoFactory.getDAO(Customer.class.getSimpleName());
            customerDAO.insert(customer);
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
    }

    public Customer getCustomerWithAccounts(int customerId) {
        Customer customer = new Customer();
        try {
            ICustomerDAO customerDAO = (ICustomerDAO) daoFactory.getDAO(Customer.class.getSimpleName());
            customer = customerDAO.getById(customerId);
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
        return customer;
    }

    public void deleteCustomer(Customer customer){
        try {
            ICustomerDAO customerDAO = (ICustomerDAO) daoFactory.getDAO(Customer.class.getSimpleName());
            customerDAO.delete(customer);
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
    }

    public Customer getCustomerByPhone(String phoneNumber){
        try {
            ICustomerDAO customerDAO = (ICustomerDAO) daoFactory.getDAO(Customer.class.getSimpleName());
            return customerDAO.getCustomerByPhone(phoneNumber);
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
        return null;
    }
}
