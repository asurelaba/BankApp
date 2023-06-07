package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.ICountryDAO;
import com.solvd.db.model.City;
import com.solvd.db.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO implements ICountryDAO {
    private static final String TABLE_NAME = "countries";

    private Country resultSetToCountry(ResultSet resultSet) {
        Country country = new Country();
        try {
            country.setCountryId(resultSet.getInt("country_id"));
            country.setCountryName(resultSet.getString("country_name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return country;
    }

    @Override
    public void insert(Country country) {
        String query = "insert into " + TABLE_NAME + "(country_name)   values (?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, country.getCountryName());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public Country getById(int id) {
        String query = "select * from " + TABLE_NAME + " where country_id = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToCountry(resultSet);
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
    public void update(Country country) {
        String query = "update " + TABLE_NAME + " set country_name = ? where city_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, country.getCountryName());
            preparedStatement.setInt(2, country.getCountryId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(Country country) {
        String query = "delete from " + TABLE_NAME + "where country_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, country.getCountryId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("delete from countries failed" + e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Country> getAll() {
        String query = "select * from " + TABLE_NAME;
        Connection connection = CONNECTION_POOL.getConnection();
        List<Country> countries = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                countries.add(resultSetToCountry(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return countries;
    }

    @Override
    public Country getCountryByName(String countryName) {
        String query = "select * from " + TABLE_NAME + " where country_name = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, countryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSetToCountry(resultSet);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }
}
