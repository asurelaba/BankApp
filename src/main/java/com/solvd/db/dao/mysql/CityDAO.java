package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.ICityDAO;
import com.solvd.db.model.City;
import com.solvd.db.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDAO implements ICityDAO {
    private static final String TABLE_NAME = "cities";

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
            System.out.println(e);
        }
        return city;
    }

    @Override
    public void insert(City city) {
        String query = "insert into " + TABLE_NAME + "(city_name, country_id)  values (?,?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, city.getCityName());
            preparedStatement.setInt(2, city.getCountry().getCountryId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public City getById(int id) {
        String query = "select * from " + TABLE_NAME + " where city_id = ?";
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
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public void update(City city) {
        String query = "update " + TABLE_NAME + " set city_name = ?, country_id = ? where city_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, city.getCityName());
            preparedStatement.setInt(2, city.getCityId());
            preparedStatement.setInt(3, city.getCityId());
            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(City city) {
        String query = "delete from " + TABLE_NAME + "where address_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, city.getCityId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("delete from cities failed" + e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<City> getAll() {
        String query = "select * from " + TABLE_NAME;
        Connection connection = CONNECTION_POOL.getConnection();
        List<City> cities = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                cities.add(resultSetToCity(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return cities;
    }

    @Override
    public List<City> getCityByName(String cityName) {
        String query = "select * from " + TABLE_NAME + " where city_name = ?";
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
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return cities;
    }
}
