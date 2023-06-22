package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IAccountTransactionDAO;
import com.solvd.db.dao.mysql.AccountTransactionDAO;
import com.solvd.db.factory.AbstractDAOFactory;
import com.solvd.db.factory.DAOFactoryManager;
import com.solvd.db.model.AccountTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AccountTransactionService {

    private static final Logger LOGGER = LogManager.getLogger(AccountTransactionService.class);
    private AbstractDAOFactory daoFactory = DAOFactoryManager.getDAOFactoryInstance();

    public List<AccountTransaction> getAccountTransactionsByAccountNumber(int accountNumber) {
        List<AccountTransaction> accountTransactions = new ArrayList<>();
        try {
            IAccountTransactionDAO accountTransactionDAO = (IAccountTransactionDAO) daoFactory.getDAO(AccountTransaction.class.getSimpleName());
            accountTransactions = accountTransactionDAO.getTransactionByAccountNumber(accountNumber);
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
        return accountTransactions;
    }

}
