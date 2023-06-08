package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.IAccountDAO;
import com.solvd.db.model.Account;
import com.solvd.db.model.AccountType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements IAccountDAO {

    @Override
    public List<Account> getAccountByTypeId(int accountTypeId) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE account_type_id = ?";
        List<Account> accounts = new ArrayList<>();
        try {
            Connection connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, accountTypeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = new Account();
                    account.setAccountNumber(resultSet.getInt("account_number"));
                    account.setBalance(resultSet.getDouble("balance"));
                    account.setMinBalance(resultSet.getDouble("minbalance"));
                    AccountType accountType = new AccountType();
                    accountType.setAccountTypeId(resultSet.getInt("account_type_id"));
                    account.setAccountType(accountType);
                    accounts.add(account);
                }
            } finally {
                CONNECTION_POOL.releaseConnection(connection);
            }
        } catch (SQLException e) {
            System.out.println("problem executing getAccountByTypeId" + e);
        }
        return accounts;
    }

    @Override
    public void insert(Account account) {
        String query = "INSERT INTO " + TABLE_NAME + " (balance,minbalance,account_type_id)  VALUES (?,?,?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setDouble(2, account.getMinBalance());
            preparedStatement.setInt(3, account.getAccountType().getAccountTypeId());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    account.setAccountNumber(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("could not insert account" + account + "into table:: " + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public Account getById(int id) {
        Connection connection = CONNECTION_POOL.getConnection();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE account_number = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Account account = new Account();
                account.setAccountNumber(resultSet.getInt("account_number"));
                account.setBalance(resultSet.getDouble("balance"));
                account.setMinBalance(resultSet.getDouble("minbalance"));
                account.setAccountType(new AccountType());
                account.getAccountType().setAccountTypeId(resultSet.getInt("account_type_id"));
                return account;
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void update(Account account) {
        String query = "UPDATE " + TABLE_NAME +
                "SET accounts.balance = ? " +
                "WHERE account_number = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getAccountNumber());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public void delete(Account account) {
        String query = "DELETE FROM " + TABLE_NAME +
                "WHERE account_number = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, account.getAccountNumber());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Account> getAll() {
        Connection connection = CONNECTION_POOL.getConnection();
        String query = "SELECT * FROM " + TABLE_NAME;
        List<Account> accounts = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                Account account = new Account();
                account.setAccountNumber(resultSet.getInt("account_number"));
                account.setBalance(resultSet.getDouble("balance"));
                account.setMinBalance(resultSet.getDouble("minbalance"));
                AccountType accountType = new AccountType();
                accountType.setAccountTypeId(resultSet.getInt("account_type_id"));
                account.setAccountType(accountType);
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }
}
