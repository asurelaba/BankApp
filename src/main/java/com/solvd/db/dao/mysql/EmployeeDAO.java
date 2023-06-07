package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.IEmployeeDAO;
import com.solvd.db.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO implements IEmployeeDAO {
    private Employee resultSetToEmployee(ResultSet resultSet) {
        Employee employee = new Employee();
        try {
            employee.setEmployeeId(resultSet.getInt("employee_id"));
            Person person = new Person();
            person.setPersonId(resultSet.getInt("person_id"));
            employee.setPerson(person);
            EmployeeRole employeeRole = new EmployeeRole();
            employeeRole.setRoleId(resultSet.getInt("employee_role_id"));
            employee.setEmployeeRole(employeeRole);
            Employee manager = new Employee();
            manager.setEmployeeId(resultSet.getInt("manager_id"));
            employee.setManager(manager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employee;
    }

    @Override
    public void insert(Employee employee) {
        String query = "INSERT INTO " + TABLE_NAME + "(person_id, employee_role_id, manager_id)   VALUES (?,?,?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employee.getPerson().getPersonId());
            preparedStatement.setInt(2, employee.getEmployeeRole().getRoleId());
            preparedStatement.setInt(3, employee.getManager().getEmployeeId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public Employee getById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE employee_id = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToEmployee(resultSet);
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
    public void update(Employee employee) {
        String query = "UPDATE " + TABLE_NAME + " SET person_id = ? , employee_role_id = ?, manager_id = ? WHERE employee_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employee.getPerson().getPersonId());
            preparedStatement.setInt(2, employee.getEmployeeRole().getRoleId());
            preparedStatement.setInt(3, employee.getManager().getEmployeeId());
            preparedStatement.setInt(4, employee.getEmployeeId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(Employee employee) {
        String query = "DELETE FROM " + TABLE_NAME + "WHERE employee_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employee.getEmployeeId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("delete from employees failed" + e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<Employee> getAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        Connection connection = CONNECTION_POOL.getConnection();
        List<Employee> employees = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                employees.add(resultSetToEmployee(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return employees;
    }

    @Override
    public List<Employee> getEmployeesByRoleID(int roleId) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE employee_role_id = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        List<Employee> employees = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, roleId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employees.add(resultSetToEmployee(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return employees;
    }
}
