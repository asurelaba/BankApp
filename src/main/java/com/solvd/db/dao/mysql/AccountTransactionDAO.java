package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.IAccountTransactionDAO;
import com.solvd.db.model.Account;
import com.solvd.db.model.AccountTransaction;
import com.solvd.db.model.TransactionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountTransactionDAO implements IAccountTransactionDAO {

    public static final Logger LOGGER = LogManager.getLogger(AccountTransactionDAO.class);

    private AccountTransaction resultSetToAccountTransaction(ResultSet resultSet) {
        AccountTransaction accountTransaction = new AccountTransaction();
        Account account = new Account();
        try {
            account.setAccountNumber(resultSet.getInt("account_number"));
            accountTransaction.setAccount(account);
            accountTransaction.setTransactionId(resultSet.getInt("transaction_id"));
            accountTransaction.setTransactionDate(resultSet.getDate("transaction_date"));
            TransactionType transactionType = new TransactionType();
            transactionType.setTransactionTypeId(resultSet.getInt("transaction_type_id"));
            accountTransaction.setTransactionType(transactionType);
            accountTransaction.setAmount(resultSet.getInt("amount"));
            return accountTransaction;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public List<AccountTransaction> getTransactionByAccountNumber(int accountNumber) {
        Connection connection = CONNECTION_POOL.getConnection();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE account_number = ?";
        List<AccountTransaction> accountTransactions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, accountNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    AccountTransaction accountTransaction = resultSetToAccountTransaction(resultSet);
                    accountTransactions.add(accountTransaction);
                }
            } finally {
                CONNECTION_POOL.releaseConnection(connection);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return accountTransactions;
    }

    @Override
    public void insert(AccountTransaction accountTransaction) {
        String query = "INSERT INTO " + TABLE_NAME + " (amount, transaction_date, account_number, transaction_type_id)" +
                "VALUES (?,?,?,?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, accountTransaction.getTransactionId());
            preparedStatement.setInt(2, accountTransaction.getAmount());
            preparedStatement.setDate(3, accountTransaction.getTransactionDate());
            preparedStatement.setInt(4, accountTransaction.getAccount().getAccountNumber());
            preparedStatement.setInt(5, accountTransaction.getTransactionType().getTransactionTypeId());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    accountTransaction.setTransactionId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("could not insert account" + accountTransaction + "into table:: " + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public AccountTransaction getById(int id) {
        Connection connection = CONNECTION_POOL.getConnection();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE transaction_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToAccountTransaction(resultSet);
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
    public void update(AccountTransaction accountTransaction) {
        Connection connection = CONNECTION_POOL.getConnection();
        String query = "UPDATE " + TABLE_NAME + " SET  transaction_date = ? ," +
                "amount = ?, account_number = ? , transaction_type_id = ? WHERE transaction_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, accountTransaction.getTransactionDate());
            preparedStatement.setInt(2, accountTransaction.getAmount());
            preparedStatement.setInt(3, accountTransaction.getAccount().getAccountNumber());
            preparedStatement.setInt(4, accountTransaction.getTransactionType().getTransactionTypeId());
            preparedStatement.setInt(5, accountTransaction.getTransactionId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(AccountTransaction accountTransaction) {
        Connection connection = CONNECTION_POOL.getConnection();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE transaction_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, accountTransaction.getTransactionId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public List<AccountTransaction> getAll() {
        Connection connection = CONNECTION_POOL.getConnection();
        List<AccountTransaction> accountTransactions = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    AccountTransaction accountTransaction = resultSetToAccountTransaction(resultSet);
                    accountTransactions.add(accountTransaction);
                }
            } finally {
                CONNECTION_POOL.releaseConnection(connection);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return accountTransactions;
    }
}
