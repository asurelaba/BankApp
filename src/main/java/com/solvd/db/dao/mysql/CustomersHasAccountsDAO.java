package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.ICustomersHasAccountsDAO;
import com.solvd.db.model.Account;
import com.solvd.db.model.Customer;
import com.solvd.db.model.CustomerHasAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomersHasAccountsDAO implements ICustomersHasAccountsDAO {

    @Override
    public void insert(CustomerHasAccount customerHasAccount) {
        String query = "INSERT INTO " + TABLE_NAME + "(customer_id, account_number) VALUES (?,?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerHasAccount.getCustomer_id());
            preparedStatement.setInt(2, customerHasAccount.getAccount_number());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    //will not be implemented as getbycustomerId and getbyAccount_number returns multiple rows
    @Override
    public CustomerHasAccount getById(int id) {
        return null;
    }

    @Override
    public void update(CustomerHasAccount customerHasAccount) {
        String query = "UPDATE " + TABLE_NAME + "SET customer_id =? WHERE account_number = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerHasAccount.getCustomer_id());
            preparedStatement.setInt(2, customerHasAccount.getAccount_number());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(CustomerHasAccount customerHasAccount) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE account_number = ? AND customer_id = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerHasAccount.getAccount_number());
            preparedStatement.setInt(2, customerHasAccount.getCustomer_id());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public List<CustomerHasAccount> getAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        Connection connection = CONNECTION_POOL.getConnection();
        List<CustomerHasAccount> customerHasAccounts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CustomerHasAccount customerHasAccount = new CustomerHasAccount();
                    customerHasAccount.setCustomer_id(resultSet.getInt("customer_id"));
                    customerHasAccount.setAccount_number(resultSet.getInt("account_number"));
                    customerHasAccounts.add(customerHasAccount);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return customerHasAccounts;
    }

    @Override
    public List<Account> getAccountsByCustomerID(int customerId) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE customer_id =?";
        Connection connection = CONNECTION_POOL.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = new Account();
                    account.setAccountNumber(resultSet.getInt("account_number"));
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return accounts;
    }

    @Override
    public List<Customer> getCustomersByAccountNumber(int accountNumber) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE customer_id =?";
        Connection connection = CONNECTION_POOL.getConnection();
        List<Customer> customers = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, accountNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Customer customer = new Customer();
                    customer.setCustomerId(resultSet.getInt("customer_id"));
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return customers;
    }
}
