package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IEmployeeDAO;
import com.solvd.db.factory.JdbcDAOFactory;
import com.solvd.db.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private JdbcDAOFactory jdbcDAOFactory = new JdbcDAOFactory();

    public List<Employee> getAllEmployeesWithManager() {
        List<Employee> employees = new ArrayList<>();
        try {
            IEmployeeDAO employeeDAO = (IEmployeeDAO) jdbcDAOFactory.getDAO(Employee.class.getSimpleName());
            IEmployeeDAO managerDAO = (IEmployeeDAO) jdbcDAOFactory.getDAO(Employee.class.getSimpleName());
            for (Employee employee : employeeDAO.getAll()) {
                Employee manager = managerDAO.getById(employee.getManager().getEmployeeId());
                employee.setManager(manager);
                employees.add(employee);
            }
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
        return employees;
    }
}
