package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IEmployeeRoleDAO;
import com.solvd.db.factory.AbstractDAOFactory;
import com.solvd.db.factory.DAOFactoryManager;
import com.solvd.db.model.EmployeeRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRoleService {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeRoleService.class);
    private AbstractDAOFactory daoFactory = DAOFactoryManager.getDAOFactoryInstance();

    public List<EmployeeRole> getAllEmployeeRoles() {
        List<EmployeeRole> employeeRoles = new ArrayList<>();
        try {
            IEmployeeRoleDAO employeeRoleDAO = (IEmployeeRoleDAO) daoFactory.getDAO(EmployeeRole.class.getSimpleName());
            for (EmployeeRole employeeRole : employeeRoleDAO.getAll()) {
                employeeRoles.add(employeeRole);
            }
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
        return employeeRoles;
    }
}
