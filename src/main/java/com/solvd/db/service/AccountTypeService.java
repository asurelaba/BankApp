package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IAccountDAO;
import com.solvd.db.dao.interfaces.IAccountTypeDAO;
import com.solvd.db.factory.AbstractDAOFactory;
import com.solvd.db.factory.DAOFactoryManager;
import com.solvd.db.model.Account;
import com.solvd.db.model.AccountType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountTypeService {

    private static final Logger LOGGER = LogManager.getLogger(AccountTypeService.class);
    private AbstractDAOFactory daoFactory = DAOFactoryManager.getDAOFactoryInstance();
    private IAccountTypeDAO accountTypeDAO;

    public AccountTypeService() throws DAONotFoundException {
        accountTypeDAO = (IAccountTypeDAO) daoFactory.getDAO(AccountType.class.getSimpleName());
    }

    public AccountType getAccountTypeForAccount(Account account) {
        try {
            IAccountDAO accountDAO = (IAccountDAO) daoFactory.getDAO(Account.class.getName());
            AccountType accountType = accountTypeDAO.getById(account.getAccountType().getAccountTypeId());
            account.setAccountType(accountType);
            return accountType;
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
        return null;
    }
}
