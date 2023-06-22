package com.solvd.db.dao.mybatis;

import com.solvd.db.dao.interfaces.IAccountDAO;
import com.solvd.db.dao.interfaces.IAccountTransactionDAO;
import com.solvd.db.factory.MyBatisDAOFactory;
import com.solvd.db.model.AccountTransaction;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AccountTransactionDAO extends MyBatisBaseDAO<AccountTransaction> implements IAccountTransactionDAO {

    @Override
    public List<AccountTransaction> getTransactionByAccountNumber(int accountNumber) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            return sqlSession.getMapper(IAccountTransactionDAO.class).getTransactionByAccountNumber(accountNumber);
        }
    }
}
