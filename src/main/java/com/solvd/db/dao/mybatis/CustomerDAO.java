package com.solvd.db.dao.mybatis;

import com.solvd.db.dao.interfaces.ICustomerDAO;
import com.solvd.db.model.Customer;
import org.apache.ibatis.session.SqlSession;

public class CustomerDAO extends MyBatisBaseDAO<Customer> implements ICustomerDAO {

    @Override
    public Customer getCustomerByPhone(String phoneNumber) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            return sqlSession.getMapper(ICustomerDAO.class).getCustomerByPhone(phoneNumber);
        }
    }
}
