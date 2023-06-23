package com.solvd.db.dao.mybatis;

import com.solvd.db.dao.interfaces.ITransactionTypeDAO;
import com.solvd.db.model.TransactionType;
import org.apache.ibatis.session.SqlSession;

public class TransactionTypeDAO extends MyBatisBaseDAO<TransactionType> implements ITransactionTypeDAO {

    @Override
    public TransactionType getTransactionTypeByName(String name) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            return sqlSession.getMapper(ITransactionTypeDAO.class).getTransactionTypeByName(name);
        }
    }
}
