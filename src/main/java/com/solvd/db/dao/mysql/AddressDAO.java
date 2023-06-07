package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.IAddressDAO;
import com.solvd.db.model.Address;
import com.solvd.db.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressDAO implements IAddressDAO {
    private static final String TABLE_NAME = "addresses";

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
            System.out.println(address);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return address;
    }

    @Override
    public List<Address> getAddressByZipCode(String zipCode) {
        String query = "select * from " + TABLE_NAME + " where zip_code = ?";
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
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return addresses;
    }

    @Override
    public void insert(Address address) {
        String query = "insert into " + TABLE_NAME + "(line1, line2, zip_code, city_id) values (?,?,?,?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, address.getLine1());
            preparedStatement.setString(2, address.getLine2());
            preparedStatement.setString(3, address.getZipCode());
            preparedStatement.setInt(4, address.getCity().getCityId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public Address getById(int id) {
        String query = "select * from " + TABLE_NAME + " where address_id = ?";
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
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void update(Address address) {
        String query = "update " + TABLE_NAME + " set line1 = ?, line2 = ?, zip_code =?, city_id = ? where address_id = ?";
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
            System.out.println("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(Address address) {
        String query = "delete from " + TABLE_NAME + "where address_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, address.getAddressId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("delete from addresses failed" + e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Address> getAll() {
        String query = "select * from " + TABLE_NAME;
        Connection connection = CONNECTION_POOL.getConnection();
        List<Address> addresses = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                addresses.add(resultSetToAddress(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return addresses;
    }
}
