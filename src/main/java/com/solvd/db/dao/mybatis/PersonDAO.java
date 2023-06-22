package com.solvd.db.dao.mybatis;

import com.solvd.db.dao.interfaces.IPersonDAO;
import com.solvd.db.model.Person;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class PersonDAO extends MyBatisBaseDAO<Person> implements IPersonDAO {

    @Override
    public List<Person> getPersonByLastName(String lastName) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            return sqlSession.getMapper(IPersonDAO.class).getPersonByLastName(lastName);
        }
    }
}
