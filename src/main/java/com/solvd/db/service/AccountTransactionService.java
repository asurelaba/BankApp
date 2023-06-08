package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IAccountTransactionDAO;
import com.solvd.db.dao.mysql.AccountTransactionDAO;
import com.solvd.db.factory.JdbcDAOFactory;
import com.solvd.db.model.AccountTransaction;

import java.util.ArrayList;
import java.util.List;

public class AccountTransactionService {

    private JdbcDAOFactory jdbcDAOFactory = new JdbcDAOFactory();

    public List<AccountTransaction> getAccountTransactionsByAccountNumber(int accountNumber) {
        List<AccountTransaction> accountTransactions = new ArrayList<>();
        try {
            IAccountTransactionDAO accountTransactionDAO = (AccountTransactionDAO) jdbcDAOFactory.getDAO(AccountTransaction.class.getSimpleName());
            accountTransactions = accountTransactionDAO.getTransactionByAccountNumber(accountNumber);
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
        return accountTransactions;
    }

}
