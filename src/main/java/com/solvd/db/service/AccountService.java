package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IAccountDAO;
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

    public void createAccount(Account account){
        try {
            IAccountDAO accountDAO = (IAccountDAO) jdbcDAOFactory.getDAO(Account.class.getSimpleName());
            accountDAO.insert(account);
        } catch (DAONotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(Account account){
        try {
            IAccountDAO accountDAO = (IAccountDAO) jdbcDAOFactory.getDAO(Account.class.getSimpleName());
            accountDAO.delete(account);
        } catch (DAONotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
