package com.solvd.db.dao.mybatis;

import com.solvd.db.dao.interfaces.IEmployeeRoleDAO;
import com.solvd.db.model.EmployeeRole;
import org.apache.ibatis.session.SqlSession;

public class EmployeeRoleDAO extends MyBatisBaseDAO<EmployeeRole> implements IEmployeeRoleDAO {

    @Override
    public EmployeeRole getRoleByName(String roleName) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            return sqlSession.getMapper(IEmployeeRoleDAO.class).getRoleByName(roleName);
        }
    }
}
