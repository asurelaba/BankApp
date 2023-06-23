package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IAccountDAO;
import com.solvd.db.factory.AbstractDAOFactory;
import com.solvd.db.factory.DAOFactoryManager;
import com.solvd.db.model.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountService {

    private static final Logger LOGGER = LogManager.getLogger(AccountService.class);
    private AbstractDAOFactory daoFactory = DAOFactoryManager.getDAOFactoryInstance();
    private IAccountDAO accountDAO;

    public AccountService() throws DAONotFoundException {
        accountDAO = (IAccountDAO) daoFactory.getDAO(Account.class.getSimpleName());
    }

    public Account getAccountByAccountNumber(int accountNumber) {
        return accountDAO.getById(accountNumber);
    }

    public void createAccount(Account account) {
        accountDAO.insert(account);
    }

    public void deleteAccount(Account account) {
        accountDAO.delete(account);
    }
}
