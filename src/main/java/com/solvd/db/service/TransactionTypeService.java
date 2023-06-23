package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.ITransactionTypeDAO;
import com.solvd.db.factory.AbstractDAOFactory;
import com.solvd.db.factory.DAOFactoryManager;
import com.solvd.db.model.TransactionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TransactionTypeService {

    private static final Logger LOGGER = LogManager.getLogger(TransactionTypeService.class);
    private AbstractDAOFactory daoFactory = DAOFactoryManager.getDAOFactoryInstance();
    private ITransactionTypeDAO transactionTypeDAO;

    public TransactionTypeService() throws DAONotFoundException {
        transactionTypeDAO = (ITransactionTypeDAO) daoFactory.getDAO(TransactionType.class.getSimpleName());
    }

    public void deleteTransactionType(String type) {
        transactionTypeDAO.getTransactionTypeByName(type);
    }
}
