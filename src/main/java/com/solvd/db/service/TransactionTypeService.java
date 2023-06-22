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

    public void deleteTransactionType(String type) {
        try {
            ITransactionTypeDAO transactionTypeDAO = (ITransactionTypeDAO) daoFactory.getDAO(TransactionType.class.getSimpleName());
            transactionTypeDAO.getTransactionTypeByName(type);
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
    }
}
