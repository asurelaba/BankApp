package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.ITransactionTypeDAO;
import com.solvd.db.model.TransactionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionTypeDAO implements ITransactionTypeDAO {
    private static final String TABLE_NAME = "transaction_types";

    private TransactionType resultSetToTransactionType(ResultSet resultSet) {
        TransactionType transactionType = new TransactionType();
        try {
            transactionType.setTransactionTypeId(resultSet.getInt(1));
            transactionType.setTransactionType(resultSet.getString(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactionType;
    }

    @Override
    public void insert(TransactionType transactionType) {
        String query = "insert into " + TABLE_NAME + "(transaction_type)   values (?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, transactionType.getTransactionType());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public TransactionType getById(int id) {
        String query = "select * from " + TABLE_NAME + " where transaction_type_id = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = connection.prepareStatement(query).executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToTransactionType(resultSet);
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
    public void update(TransactionType transactionType) {
        String query = "update " + TABLE_NAME + " set transaction_type = ? where transaction_type_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, transactionType.getTransactionType());
            preparedStatement.setInt(2, transactionType.getTransactionTypeId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(TransactionType transactionType) {
        String query = "delete from " + TABLE_NAME + "where transaction_type_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, transactionType.getTransactionTypeId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("delete from transaction_types failed" + e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<TransactionType> getAll() {
        String query = "select * from " + TABLE_NAME;
        Connection connection = CONNECTION_POOL.getConnection();
        List<TransactionType> transactionTypes = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                transactionTypes.add(resultSetToTransactionType(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return transactionTypes;
    }

    @Override
    public TransactionType getTransactionTypeByName(String name) {
        String query = "select * from " + TABLE_NAME + " where transaction_type = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = connection.prepareStatement(query).executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToTransactionType(resultSet);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }
}
