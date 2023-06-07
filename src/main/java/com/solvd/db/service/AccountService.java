package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IAccountDAO;
import com.solvd.db.dao.interfaces.IBaseDAO;
import com.solvd.db.factory.JdbcDAOFactory;
import com.solvd.db.model.Account;

public class AccountService {
    private JdbcDAOFactory jdbcDAOFactory = new JdbcDAOFactory();

    public Account getAccountByAccountNumber(int accountNumber) {
        try {
            IAccountDAO accountDAO = (IAccountDAO) jdbcDAOFactory.getDAO(Account.class.getSimpleName());
            return accountDAO.getById(accountNumber);
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
        return null;
    }
}
