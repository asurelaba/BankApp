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
        String query = "select * from accounts where account_type_id = ?";
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
        String query = "INSERT INTO ACCOUNTS (balance,minbalance,account_type_id)  VALUES (?,?,?)";
        try {
            Connection connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setDouble(2, account.getMinBalance());
            preparedStatement.setInt(3, account.getAccountType().getAccountTypeId());
            preparedStatement.execute();
            CONNECTION_POOL.releaseConnection(connection);
        } catch (SQLException e) {
            System.out.println("could not insert account" + account + "into table:: " + e);
        }
    }

    @Override
    public Account getById(int id) {
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ACCOUNTS WHERE account_number = ?");
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
            throw new RuntimeException(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void update(Account account) {
        String query = "UPDATE accounts " +
                "SET accounts.balance = ? " +
                "WHERE account_number = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getAccountNumber());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public void delete(Account account) {
        String query = "DELETE FROM accounts " +
                "WHERE account_number = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, account.getAccountNumber());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Account> getAll() {
        Connection connection = CONNECTION_POOL.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement("select * from accounts").executeQuery();
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
