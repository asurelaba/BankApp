package com.solvd.db.dao.mybatis;

import com.solvd.db.dao.interfaces.IAccountTransactionDAO;
import com.solvd.db.model.AccountTransaction;
import org.apache.ibatis.session.SqlSession;


import java.util.List;

public class AccountTransactionDAO extends MyBatisBaseDAO<AccountTransaction> implements IAccountTransactionDAO {

    @Override
    public List<AccountTransaction> getTransactionByAccountNumber(int accountNumber) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            return sqlSession.getMapper(IAccountTransactionDAO.class).getTransactionByAccountNumber(accountNumber);
        }
    }
}
