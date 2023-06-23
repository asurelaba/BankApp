package com.solvd.db.dao.mybatis;

import com.solvd.db.dao.interfaces.ICountryDAO;
import com.solvd.db.model.Country;
import org.apache.ibatis.session.SqlSession;

public class CountryDAO extends MyBatisBaseDAO<Country> implements ICountryDAO {

    @Override
    public Country getCountryByName(String countryName) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            return sqlSession.getMapper(ICountryDAO.class).getCountryByName(countryName);
        }
    }
}
