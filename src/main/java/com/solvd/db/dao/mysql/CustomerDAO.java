package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.ICustomerDAO;
import com.solvd.db.model.Customer;
import com.solvd.db.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO implements ICustomerDAO {
    private static final String TABLE_NAME = "customers";

    private Customer resultSetToCustomer(ResultSet resultSet) {
        Customer customer = new Customer();
        try {
            customer.setCustomerId(resultSet.getInt("customer_id"));
            Person person = new Person();
            person.setPersonId(resultSet.getInt("person_id"));
            customer.setPerson(person);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public void insert(Customer customer) {
        String query = "insert into " + TABLE_NAME + "(person_id)   values (?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customer.getPerson().getPersonId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public Customer getById(int id) {
        String query = "select * from " + TABLE_NAME + " where customer_id = ?";
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
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void update(Customer customer) {
        String query = "update " + TABLE_NAME + " set person_id = ? where customer_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customer.getPerson().getPersonId());
            preparedStatement.setInt(2, customer.getCustomerId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(Customer customer) {
        String query = "delete from " + TABLE_NAME + "where customer_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customer.getCustomerId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("delete from customers failed" + e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Customer> getAll() {
        String query = "select * from " + TABLE_NAME;
        Connection connection = CONNECTION_POOL.getConnection();
        List<Customer> customers = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                customers.add(resultSetToCustomer(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return customers;
    }
}
