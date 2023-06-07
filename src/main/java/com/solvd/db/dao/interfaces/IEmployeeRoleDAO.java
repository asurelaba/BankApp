package com.solvd.db.dao.interfaces;

import com.solvd.db.model.EmployeeRole;

public interface IEmployeeRoleDAO extends IBaseDAO<EmployeeRole> {
    public EmployeeRole getRoleByName(String roleName);
}
