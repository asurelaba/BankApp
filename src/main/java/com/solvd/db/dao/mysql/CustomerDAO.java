package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.ICustomerDAO;
import com.solvd.db.model.Customer;
import com.solvd.db.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements ICustomerDAO {

    private static final Logger LOGGER = LogManager.getLogger(Customer.class);

    private Customer resultSetToCustomer(ResultSet resultSet) {
        Customer customer = new Customer();
        try {
            customer.setCustomerId(resultSet.getInt("customer_id"));
            Person person = new Person();
            person.setPersonId(resultSet.getInt("person_id"));
            customer.setPerson(person);
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return customer;
    }

    @Override
    public void insert(Customer customer) {
        String query = "INSERT INTO " + TABLE_NAME + "(person_id)   VALUES (?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, customer.getPerson().getPersonId());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    customer.setCustomerId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public Customer getById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE customer_id = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToCustomer(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void update(Customer customer) {
        String query = "UPDATE " + TABLE_NAME + " SET person_id = ? WHERE customer_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customer.getPerson().getPersonId());
            preparedStatement.setInt(2, customer.getCustomerId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(Customer customer) {
        String query = "DELETE FROM " + TABLE_NAME + "WHERE customer_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customer.getCustomerId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("delete from customers failed" + e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Customer> getAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        Connection connection = CONNECTION_POOL.getConnection();
        List<Customer> customers = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                customers.add(resultSetToCustomer(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return customers;
    }

    @Override
    public Customer getCustomerByPhone(String phoneNumber) {
        String query = "SELECT * FROM " + TABLE_NAME + " JOIN persons ON customers.person_id = persons.person_id" +
                " WHERE phone_number = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, phoneNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToCustomer(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }
}
