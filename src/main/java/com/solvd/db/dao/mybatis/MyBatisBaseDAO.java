package com.solvd.db.dao.mybatis;

import com.solvd.db.dao.interfaces.IBaseDAO;
import com.solvd.db.factory.MyBatisDAOFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class MyBatisBaseDAO<Entity> implements IBaseDAO<Entity> {

    private static final Logger LOGGER = LogManager.getLogger(MyBatisBaseDAO.class);
    protected final SqlSessionFactory SQL_SESSION_FACTORY = MyBatisDAOFactory.getSqlSessionFactory();
    private final String interfaceName = this.getClass().getInterfaces()[0].getName();

    @Override
    public void insert(Entity entity) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            LOGGER.debug("in MyBatisBaseDAO calling class" + interfaceName);
            sqlSession.insert(interfaceName + ".insert", entity);
            sqlSession.commit();
        }
    }

    @Override
    public Entity getById(int id) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            return sqlSession.selectOne(interfaceName + ".getById", id);
        }
    }

    @Override
    public void update(Entity entity) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            sqlSession.update(interfaceName + ".update", entity);
            sqlSession.commit();
        }
    }

    @Override
    public void delete(Entity entity) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            sqlSession.update(interfaceName + ".delete", entity);
            sqlSession.commit();
        }
    }

    @Override
    public List<Entity> getAll() {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            return sqlSession.selectList(interfaceName + ".getAll");
        }
    }
}
