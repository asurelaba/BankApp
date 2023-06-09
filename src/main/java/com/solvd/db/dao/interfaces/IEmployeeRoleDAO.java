package com.solvd.db.dao.interfaces;

import com.solvd.db.model.EmployeeRole;

public interface IEmployeeRoleDAO extends IBaseDAO<EmployeeRole> {

    String TABLE_NAME = "employee_roles";

    EmployeeRole getRoleByName(String roleName);
}
