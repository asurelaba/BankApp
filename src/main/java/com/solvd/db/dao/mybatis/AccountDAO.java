package com.solvd.db.dao.mybatis;

import com.solvd.db.dao.interfaces.IAccountDAO;
import com.solvd.db.model.Account;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AccountDAO extends MyBatisBaseDAO<Account> implements IAccountDAO {

    @Override
    public List<Account> getAccountByTypeId(int accountTypeId) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
           return sqlSession.getMapper(IAccountDAO.class).getAccountByTypeId(accountTypeId);
        }
    }
}
