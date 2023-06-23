package com.solvd.db.dao.mybatis;

import com.solvd.db.dao.interfaces.ICityDAO;
import com.solvd.db.model.City;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CityDAO extends MyBatisBaseDAO<City> implements ICityDAO {

    @Override
    public List<City> getCityByName(String cityName) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            return sqlSession.getMapper(ICityDAO.class).getCityByName(cityName);
        }
    }
}
