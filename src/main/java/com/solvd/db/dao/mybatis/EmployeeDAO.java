package com.solvd.db.dao.mybatis;

import com.solvd.db.dao.interfaces.IEmployeeDAO;
import com.solvd.db.model.Employee;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class EmployeeDAO extends MyBatisBaseDAO<Employee> implements IEmployeeDAO {

    @Override
    public List<Employee> getEmployeesByRoleID(int roleId) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            return sqlSession.getMapper(IEmployeeDAO.class).getEmployeesByRoleID(roleId);
        }
    }
}
