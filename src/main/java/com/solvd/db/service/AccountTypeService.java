package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IAccountDAO;
import com.solvd.db.dao.interfaces.IAccountTransactionDAO;
import com.solvd.db.dao.interfaces.IAccountTypeDAO;
import com.solvd.db.dao.mysql.AccountTransactionDAO;
import com.solvd.db.factory.JdbcDAOFactory;
import com.solvd.db.model.Account;
import com.solvd.db.model.AccountTransaction;
import com.solvd.db.model.AccountType;

import java.util.ArrayList;
import java.util.List;

public class AccountTypeService {
    private JdbcDAOFactory jdbcDAOFactory = new JdbcDAOFactory();

    public AccountType getAccountTypeForAccount(Account account) {
        try {
            IAccountTypeDAO accountTypeDAO = (IAccountTypeDAO) jdbcDAOFactory.getDAO(AccountType.class.getSimpleName());
            IAccountDAO accountDAO = (IAccountDAO) jdbcDAOFactory.getDAO(Account.class.getName());
            AccountType accountType = accountTypeDAO.getById(account.getAccountType().getAccountTypeId());
            account.setAccountType(accountType);
            return accountType;
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
        return null;
    }
}
