package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IAccountDAO;
import com.solvd.db.factory.JdbcDAOFactory;
import com.solvd.db.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountService {

    private static final Logger LOGGER = LogManager.getLogger(AccountService.class);
    private JdbcDAOFactory jdbcDAOFactory = new JdbcDAOFactory();

    public Account getAccountByAccountNumber(int accountNumber) {
        Account account = new Account();
        try {
            IAccountDAO accountDAO = (IAccountDAO) jdbcDAOFactory.getDAO(Account.class.getSimpleName());
            account = accountDAO.getById(accountNumber);
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
        return account;
    }

    public void createAccount(Account account) {
        try {
            IAccountDAO accountDAO = (IAccountDAO) jdbcDAOFactory.getDAO(Account.class.getSimpleName());
            accountDAO.insert(account);
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
    }

    public void deleteAccount(Account account) {
        try {
            IAccountDAO accountDAO = (IAccountDAO) jdbcDAOFactory.getDAO(Account.class.getSimpleName());
            accountDAO.delete(account);
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
    }
}
