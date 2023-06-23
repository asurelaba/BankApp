package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IEmployeeRoleDAO;
import com.solvd.db.factory.AbstractDAOFactory;
import com.solvd.db.factory.DAOFactoryManager;
import com.solvd.db.model.EmployeeRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class EmployeeRoleService {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeRoleService.class);
    private AbstractDAOFactory daoFactory = DAOFactoryManager.getDAOFactoryInstance();
    private IEmployeeRoleDAO employeeRoleDAO;

    public EmployeeRoleService() throws DAONotFoundException {
        employeeRoleDAO = (IEmployeeRoleDAO) daoFactory.getDAO(EmployeeRole.class.getSimpleName());
    }

    public List<EmployeeRole> getAllEmployeeRoles() {
        return employeeRoleDAO.getAll();
    }
}
