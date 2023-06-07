package com.solvd.db.factory;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IBaseDAO;
import com.solvd.db.dao.mysql.*;
import com.solvd.db.model.Employee;

public class JdbcDAOFactory extends AbstractDAOFactory {
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
                throw new DAONotFoundException();
        }
    }
}
