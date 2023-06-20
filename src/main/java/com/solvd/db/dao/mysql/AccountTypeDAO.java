package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.IAccountTypeDAO;
import com.solvd.db.model.AccountType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountTypeDAO implements IAccountTypeDAO {

    public static final Logger LOGGER = LogManager.getLogger(AccountTypeDAO.class);

    private AccountType resultSetToAccountType(ResultSet resultSet) {
        AccountType accountType = new AccountType();
        try {
            accountType.setAccountTypeId(resultSet.getInt(1));
            accountType.setAccountType(resultSet.getString(2));
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public AccountType getByName(String accountType) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE account_type = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountType);
            try (ResultSet resultSet = connection.prepareStatement(query).executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToAccountType(resultSet);
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
    public void insert(AccountType accountType) {
        String query = "INSERT INTO " + TABLE_NAME + "(account_type) VALUES (?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, accountType.getAccountType());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    accountType.setAccountTypeId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public AccountType getById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE account_type_id = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = connection.prepareStatement(query).executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToAccountType(resultSet);
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
    public void update(AccountType accountType) {
        String query = "UPDATE " + TABLE_NAME + " SET account_type = ? WHERE account_type_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountType.getAccountType());
            preparedStatement.setInt(2, accountType.getAccountTypeId());
            preparedStatement.execute();

        } catch (SQLException e) {
            LOGGER.error("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(AccountType accountType) {
        String query = "DELETE FROM " + TABLE_NAME + "WHERE account_type_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, accountType.getAccountTypeId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("delete on accounttype failed" + e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<AccountType> getAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        List<AccountType> accountTypes = new ArrayList<>();
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                accountTypes.add(resultSetToAccountType(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return accountTypes;
    }
}
