package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.ICityDAO;
import com.solvd.db.model.City;
import com.solvd.db.model.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAO implements ICityDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(CityDAO.class);

    private City resultSetToCity(ResultSet resultSet) {
        City city = null;
        try {
            city = new City();
            city.setCityId(resultSet.getInt("city_id"));
            city.setCityName(resultSet.getString("city_name"));
            Country country = new Country();
            country.setCountryId(resultSet.getInt("country_id"));
            city.setCountry(country);
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return city;
    }

    @Override
    public void insert(City city) {
        String query = "INSERT INTO " + TABLE_NAME + "(city_name, country_id)  VALUES (?,?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, city.getCityName());
            preparedStatement.setInt(2, city.getCountry().getCountryId());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    city.setCityId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public City getById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE city_id = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToCity(resultSet);
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
    public void update(City city) {
        String query = "UPDATE " + TABLE_NAME + " SET city_name = ?, country_id = ? WHERE city_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, city.getCityName());
            preparedStatement.setInt(2, city.getCityId());
            preparedStatement.setInt(3, city.getCityId());
            preparedStatement.execute();

        } catch (SQLException e) {
            LOGGER.error("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(City city) {
        String query = "DELETE FROM " + TABLE_NAME + "WHERE address_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, city.getCityId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("delete from cities failed" + e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<City> getAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        Connection connection = CONNECTION_POOL.getConnection();
        List<City> cities = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                cities.add(resultSetToCity(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return cities;
    }

    @Override
    public List<City> getCityByName(String cityName) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE city_name = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        List<City> cities = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cityName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cities.add(resultSetToCity(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return cities;
    }
}
