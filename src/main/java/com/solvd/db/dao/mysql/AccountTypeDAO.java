package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.IAccountTypeDAO;
import com.solvd.db.model.AccountType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountTypeDAO implements IAccountTypeDAO {
    private String tableName = "account_types";

    private AccountType resultSetToAccountType(ResultSet resultSet) {
        AccountType accountType = new AccountType();
        try {
            accountType.setAccountTypeId(resultSet.getInt(1));
            accountType.setAccountType(resultSet.getString(2));
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public AccountType getByName(String accountType) {
        String query = "select * from " + tableName + " where account_type = ?";
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
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void insert(AccountType accountType) {
        String query = "insert into " + tableName + "(account_type) values (?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountType.getAccountType());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public AccountType getById(int id) {
        String query = "select * from " + tableName + " where account_type_id = ?";
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
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void update(AccountType accountType) {
        String query = "update " + tableName + " set account_type = ? where account_type_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, accountType.getAccountType());
            preparedStatement.setInt(2, accountType.getAccountTypeId());
            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(AccountType accountType) {
        String query = "delete from " + tableName + "where account_type_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, accountType.getAccountTypeId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("delete on accounttype failed" + e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<AccountType> getAll() {
        String query = "select * from " + tableName;
        List<AccountType> accountTypes = new ArrayList<>();
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                accountTypes.add(resultSetToAccountType(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return accountTypes;
    }
}
