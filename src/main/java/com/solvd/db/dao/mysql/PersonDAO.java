package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.IPersonDAO;
import com.solvd.db.model.Address;
import com.solvd.db.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements IPersonDAO {
    private static final String TABLE_NAME = "persons";

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
            System.out.println(person);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    @Override
    public void insert(Person person) {
        String query = "insert into " + TABLE_NAME + "(first_name, last_name, date_of_birth, phone_number, email, address_id)   values (?,?,?,?,?,?)";
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
            System.out.println("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public Person getById(int id) {

        String query = "select * from " + TABLE_NAME + " where person_id = ?";

        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                //System.out.println("in getbyId personDAO" + preparedStatement.toString() + resultSet.next() );
                if (resultSet.next()) {
                    System.out.println("resultset" + resultSet.getString(2));
                    return resultSetToPerson(resultSet);
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
    public void update(Person person) {
        String query = "update " + TABLE_NAME + " set first_name = ? , last_name = ?, date_of_birth = ?, phone_number =?, email =?, address_id =? where person_id = ?";
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
            System.out.println("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(Person person) {
        String query = "delete from " + TABLE_NAME + "where person_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, person.getPersonId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("delete from persons failed" + e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Person> getAll() {
        String query = "select * from " + TABLE_NAME;
        Connection connection = CONNECTION_POOL.getConnection();
        List<Person> personList = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                personList.add(resultSetToPerson(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return personList;
    }

    @Override
    public List<Person> getPersonByLastName(String lastName) {
        String query = "select * from " + TABLE_NAME + " where last_name = ?";
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
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return personList;
    }
}
