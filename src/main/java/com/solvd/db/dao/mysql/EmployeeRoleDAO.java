package com.solvd.db.dao.mysql;

import com.solvd.db.dao.interfaces.IEmployeeRoleDAO;
import com.solvd.db.model.EmployeeRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRoleDAO implements IEmployeeRoleDAO {

    private EmployeeRole resultSetToEmployeeRole(ResultSet resultSet) {
        EmployeeRole employeeRole = new EmployeeRole();
        try {
            employeeRole.setRoleId(resultSet.getInt("employee_role_id"));
            employeeRole.setRoleName(resultSet.getString("role_name"));
            employeeRole.setJobDescription(resultSet.getString("job_description"));
            employeeRole.setSalary(resultSet.getInt(resultSet.getInt("salary")));
        } catch (SQLException e) {
            System.out.println(e);
        }
        return employeeRole;
    }

    @Override
    public void insert(EmployeeRole employeeRole) {
        String query = "INSERT INTO " + TABLE_NAME + "(role_name, job_description, salary) VALUES (?,?,?)";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employeeRole.getRoleName());
            preparedStatement.setString(2, employeeRole.getJobDescription());
            preparedStatement.setInt(3, employeeRole.getSalary());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    employeeRole.setRoleId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public EmployeeRole getById(int id) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE employee_role_id = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToEmployeeRole(resultSet);
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
    public void update(EmployeeRole employeeRole) {
        String query = "UPDATE " + TABLE_NAME + " SET role_name = ? , job_description = ?, salary = ? WHERE employee_role_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employeeRole.getRoleName());
            preparedStatement.setString(2, employeeRole.getJobDescription());
            preparedStatement.setInt(3, employeeRole.getSalary());
            preparedStatement.setInt(4, employeeRole.getRoleId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Update failed" + e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(EmployeeRole employeeRole) {
        String query = "DELETE FROM " + TABLE_NAME + "WHERE employee_role_id = ?";
        Connection connection = null;
        try {
            connection = CONNECTION_POOL.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, employeeRole.getRoleId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("delete from employee_roles failed" + e);
        } finally {
            if (connection != null) {
                CONNECTION_POOL.releaseConnection(connection);
            }
        }
    }

    @Override
    public List<EmployeeRole> getAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        Connection connection = CONNECTION_POOL.getConnection();
        List<EmployeeRole> employeeRoles = new ArrayList<>();
        try {
            ResultSet resultSet = connection.prepareStatement(query).executeQuery();
            while (resultSet.next()) {
                employeeRoles.add(resultSetToEmployeeRole(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return employeeRoles;
    }

    @Override
    public EmployeeRole getRoleByName(String roleName) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE role_name = ?";
        Connection connection = CONNECTION_POOL.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, roleName);
            try (ResultSet resultSet = connection.prepareStatement(query).executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToEmployeeRole(resultSet);
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
