package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.ICountryDAO;
import com.solvd.db.model.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDAO implements ICountryDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(CountryDAO.class);

    private Country resultSetToCountry(ResultSet resultSet) {
        Country country = new Country();
        try {
            country.setCountryId(resultSet.getInt("country_id"));
            country.setCountryName(resultSet.getString("country_name"));
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return country;
    }

    @Override
    public void insert(Country country) {
        String query = "INSERT INTO " + TABLE_NAME + "(country_name)   VALUES (?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, country.getCountryName());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    country.setCountryId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public Country getById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE country_id = ?";
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
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void update(Country country) {
        String query = "UPDATE " + TABLE_NAME + " SET country_name = ? WHERE city_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, country.getCountryName());
            preparedStatement.setInt(2, country.getCountryId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(Country country) {
        String query = "DELETE FROM " + TABLE_NAME + "WHERE country_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, country.getCountryId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("delete from countries failed" + e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Country> getAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        Connection connection = CONNECTION_POOL.getConnection();
        List<Country> countries = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                countries.add(resultSetToCountry(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return countries;
    }

    @Override
    public Country getCountryByName(String countryName) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE country_name = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, countryName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSetToCountry(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }
}
