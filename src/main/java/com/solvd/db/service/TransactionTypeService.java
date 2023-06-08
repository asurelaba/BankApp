package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.ITransactionTypeDAO;
import com.solvd.db.factory.JdbcDAOFactory;
import com.solvd.db.model.TransactionType;

public class TransactionTypeService {

    private JdbcDAOFactory jdbcDAOFactory = new JdbcDAOFactory();

    public void deleteTransactionType(String type) {
        try {
            ITransactionTypeDAO transactionTypeDAO = (ITransactionTypeDAO) jdbcDAOFactory.getDAO(TransactionType.class.getSimpleName());
            transactionTypeDAO.getTransactionTypeByName(type);
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
    }
}
