package com.solvd.db.dao.mybatis;

import com.solvd.db.dao.interfaces.IAccountDAO;
import com.solvd.db.factory.MyBatisDAOFactory;
import com.solvd.db.model.Account;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class AccountDAO implements IAccountDAO {

    public static final Logger LOGGER = LogManager.getLogger(AccountDAO.class);
    private static SqlSessionFactory sqlSessionFactory = MyBatisDAOFactory.getSqlSessionFactory();

//    static {
//        try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
//            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//    }

    @Override
    public List<Account> getAccountByTypeId(int accountTypeId) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IAccountDAO accountDAO = sqlSession.getMapper(IAccountDAO.class);
            return accountDAO.getAccountByTypeId(accountTypeId);
        }
    }

    @Override
    public void insert(Account account) {
        LOGGER.info("insert method of Mybatis");
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IAccountDAO accountDAO = sqlSession.getMapper(IAccountDAO.class);
            accountDAO.insert(account);
            //account.setAccountNumber(sqlSession.);
            sqlSession.commit();
        }
    }

    @Override
    public Account getById(int id) {
        LOGGER.info("getById method of Mybatis");
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IAccountDAO accountDAO = sqlSession.getMapper(IAccountDAO.class);
            return accountDAO.getById(id);
        }
    }

    @Override
    public void update(Account account) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IAccountDAO accountDAO = sqlSession.getMapper(IAccountDAO.class);
            accountDAO.update(account);
            sqlSession.commit();
        }
    }

    @Override
    public void delete(Account account) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IAccountDAO accountDAO = sqlSession.getMapper(IAccountDAO.class);
            accountDAO.delete(account);
            sqlSession.commit();
        }
    }

    @Override
    public List<Account> getAll() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IAccountDAO accountDAO = sqlSession.getMapper(IAccountDAO.class);
            return accountDAO.getAll();
        }
    }
}
