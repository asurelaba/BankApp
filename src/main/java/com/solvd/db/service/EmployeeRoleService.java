package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IEmployeeRoleDAO;
import com.solvd.db.factory.JdbcDAOFactory;
import com.solvd.db.model.Customer;
import com.solvd.db.model.EmployeeRole;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRoleService {
    private JdbcDAOFactory jdbcDAOFactory = new JdbcDAOFactory();

    public List<EmployeeRole> getAllEmployeeRoles() {
        List<EmployeeRole> employeeRoles = new ArrayList<>();
        try {
            IEmployeeRoleDAO employeeRoleDAO = (IEmployeeRoleDAO) jdbcDAOFactory.getDAO(EmployeeRole.class.getSimpleName());
            for (EmployeeRole employeeRole : employeeRoleDAO.getAll()) {
                employeeRoles.add(employeeRole);
            }
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
        return employeeRoles;
    }
}
