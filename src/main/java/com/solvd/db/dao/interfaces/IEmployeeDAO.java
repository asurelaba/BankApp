package com.solvd.db.dao.interfaces;

import com.solvd.db.model.Employee;
import com.solvd.db.model.EmployeeRole;

import java.util.List;

public interface IEmployeeDAO extends IBaseDAO<Employee> {

    String TABLE_NAME = "employees";

    List<Employee> getEmployeesByRoleID(int roleId);
}
