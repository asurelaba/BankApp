package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.IPersonDAO;
import com.solvd.db.model.Address;
import com.solvd.db.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements IPersonDAO {
    
    private static final Logger LOGGER = LogManager.getLogger(PersonDAO.class);

    private Person resultSetToPerson(ResultSet resultSet) {
        Person person = new Person();
        try {
            person.setPersonId(resultSet.getInt("person_id"));
            person.setFirstName(resultSet.getString("first_name"));
            person.setLastName(resultSet.getString("last_name"));
            person.setDateOfBirth(resultSet.getDate("date_of_birth"));
            person.setPhoneNumber(resultSet.getString("phone_number"));
            person.setEmail(resultSet.getString("email"));
            Address address = new Address();
            address.setAddressId(resultSet.getInt("address_id"));
            person.setAddress(address);
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return person;
    }

    @Override
    public void insert(Person person) {
        String query = "INSERT INTO " + TABLE_NAME + "(first_name, last_name, date_of_birth, phone_number, email, address_id) VALUES (?,?,?,?,?,?)";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setDate(3, person.getDateOfBirth());
            preparedStatement.setString(4, person.getPhoneNumber());
            preparedStatement.setString(5, person.getEmail());
            preparedStatement.setInt(6, person.getAddress().getAddressId());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    person.setPersonId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public Person getById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE person_id = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToPerson(resultSet);
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
    public void update(Person person) {
        String query = "UPDATE " + TABLE_NAME + " SET first_name = ? , last_name = ?, date_of_birth = ?, phone_number =?, email =?, address_id =? WHERE person_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setDate(3, person.getDateOfBirth());
            preparedStatement.setString(4, person.getPhoneNumber());
            preparedStatement.setString(5, person.getEmail());
            preparedStatement.setInt(6, person.getAddress().getAddressId());
            preparedStatement.setInt(7, person.getPersonId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(Person person) {
        String query = "DELETE FROM " + TABLE_NAME + "WHERE person_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, person.getPersonId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("delete from persons failed" + e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Person> getAll() {
        String query = "SElECT * FROM " + TABLE_NAME;
        Connection connection = CONNECTION_POOL.getConnection();
        List<Person> personList = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                personList.add(resultSetToPerson(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return personList;
    }

    @Override
    public List<Person> getPersonByLastName(String lastName) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE last_name = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        List<Person> personList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, lastName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                personList.add(resultSetToPerson(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return personList;
    }
}
