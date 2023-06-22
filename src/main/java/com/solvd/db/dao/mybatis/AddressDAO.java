package com.solvd.db.dao.mybatis;

import com.solvd.db.dao.interfaces.IAddressDAO;
import com.solvd.db.model.Address;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class AddressDAO extends MyBatisBaseDAO<Address> implements IAddressDAO {

    @Override
    public List<Address> getAddressByZipCode(String zipCode) {
        try (SqlSession sqlSession = SQL_SESSION_FACTORY.openSession()) {
            return sqlSession.getMapper(IAddressDAO.class).getAddressByZipCode(zipCode);
        }
    }
}
