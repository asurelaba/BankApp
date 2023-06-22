package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IEmployeeDAO;
import com.solvd.db.factory.AbstractDAOFactory;
import com.solvd.db.factory.DAOFactoryManager;
import com.solvd.db.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.rolling.action.IfAll;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    private static final Logger LOGGER = LogManager.getLogger(EmployeeService.class);
    private AbstractDAOFactory daoFactory = DAOFactoryManager.getDAOFactoryInstance();

    public List<Employee> getAllEmployeesWithManager() {
        List<Employee> employees = new ArrayList<>();
        try {
            IEmployeeDAO employeeDAO = (IEmployeeDAO) daoFactory.getDAO(Employee.class.getSimpleName());
            IEmployeeDAO managerDAO = (IEmployeeDAO) daoFactory.getDAO(Employee.class.getSimpleName());
            for (Employee employee : employeeDAO.getAll()) {
                if(employee.getManager() != null){
                    Employee manager = managerDAO.getById(employee.getManager().getEmployeeId());
                    employee.setManager(manager);
                }
                employees.add(employee);
            }
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
        return employees;
    }
}
