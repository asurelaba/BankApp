package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.IAddressDAO;
import com.solvd.db.model.Address;
import com.solvd.db.model.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO implements IAddressDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(AddressDAO.class);

    private Address resultSetToAddress(ResultSet resultSet) {
        Address address = new Address();
        try {
            address.setAddressId(resultSet.getInt(1));
            address.setLine1(resultSet.getString(2));
            address.setLine2(resultSet.getString(3));
            address.setZipCode(resultSet.getString(4));
            City city = new City();
            city.setCityId(resultSet.getInt(5));
            address.setCity(city);
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return address;
    }

    @Override
    public List<Address> getAddressByZipCode(String zipCode) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE zip_code = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        List<Address> addresses = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, zipCode);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    addresses.add(resultSetToAddress(resultSet));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return addresses;
    }

    @Override
    public void insert(Address address) {
        String query = "INSERT INTO " + TABLE_NAME + "(line1, line2, zip_code, city_id) VALUES (?,?,?,?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, address.getLine1());
            preparedStatement.setString(2, address.getLine2());
            preparedStatement.setString(3, address.getZipCode());
            preparedStatement.setInt(4, address.getCity().getCityId());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    address.setAddressId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public Address getById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE address_id = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToAddress(resultSet);
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
    public void update(Address address) {
        String query = "UPDATE " + TABLE_NAME + " SET line1 = ?, line2 = ?, zip_code =?, city_id = ? WHERE address_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, address.getLine1());
            preparedStatement.setString(2, address.getLine2());
            preparedStatement.setString(3, address.getZipCode());
            preparedStatement.setInt(4, address.getCity().getCityId());
            preparedStatement.setInt(5, address.getAddressId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(Address address) {
        String query = "DELETE FROM " + TABLE_NAME + "WHERE address_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, address.getAddressId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("delete from addresses failed" + e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Address> getAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        Connection connection = CONNECTION_POOL.getConnection();
        List<Address> addresses = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                addresses.add(resultSetToAddress(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return addresses;
    }
}
