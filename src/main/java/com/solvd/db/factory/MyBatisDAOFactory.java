package com.solvd.db.factory;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IBaseDAO;
import com.solvd.db.dao.mybatis.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisDAOFactory extends AbstractDAOFactory {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

    @Override
    public IBaseDAO getDAO(String tableName) throws DAONotFoundException {
        switch (tableName.toLowerCase()) {
            case "account":
                return new AccountDAO();
//            case "accounttransaction":
//                return new AccountTransactionDAO();
//            case "accounttype":
//                return new AccountTypeDAO();
//            case "address":
//                return new AddressDAO();
//            case "city":
//                return new CityDAO();
//            case "country":
//                return new CountryDAO();
//            case "customer":
//                return new CustomerDAO();
//            case "employee":
//                return new EmployeeDAO();
//            case "employeerole":
//                return new EmployeeRoleDAO();
//            case "person":
//                return new PersonDAO();
//            case "transactiontype":
//                return new TransactionTypeDAO();
//            case "customerhasaccount":
//                return new CustomersHasAccountsDAO();
            default:
                throw new DAONotFoundException(tableName + "DAO not found");
        }
    }
}
