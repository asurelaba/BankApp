package com.solvd.db.factory;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IBaseDAO;
import com.solvd.db.dao.mybatis.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Reader;

public class MyBatisDAOFactory extends AbstractDAOFactory {

    public static final Logger LOGGER = LogManager.getLogger(AccountDAO.class);
    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
                System.out.println(sqlSessionFactory);
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
        return sqlSessionFactory;
    }

    @Override
    public IBaseDAO getDAO(String tableName) throws DAONotFoundException {
        switch (tableName.toLowerCase()) {
            case "account":
                return new AccountDAO();
            case "accounttransaction":
                return new AccountTransactionDAO();
            case "accounttype":
                return new AccountTypeDAO();
            case "address":
                return new AddressDAO();
            case "city":
                return new CityDAO();
            case "country":
                return new CountryDAO();
            case "customer":
                return new CustomerDAO();
            case "employee":
                return new EmployeeDAO();
            case "employeerole":
                return new EmployeeRoleDAO();
            case "person":
                return new PersonDAO();
            case "transactiontype":
                return new TransactionTypeDAO();
            default:
                throw new DAONotFoundException(tableName + "DAO not found");
        }
    }
}
