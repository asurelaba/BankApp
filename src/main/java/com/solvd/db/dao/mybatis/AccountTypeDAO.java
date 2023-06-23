package com.solvd.db.dao.mybatis;

import com.solvd.db.dao.interfaces.IAccountTypeDAO;
import com.solvd.db.model.AccountType;
import org.apache.ibatis.session.SqlSession;

public class AccountTypeDAO extends MyBatisBaseDAO<AccountType> implements IAccountTypeDAO {

    @Override
    public AccountType getByName(String accountType) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            return sqlSession.getMapper(IAccountTypeDAO.class).getByName(accountType);
        }
    }
}
