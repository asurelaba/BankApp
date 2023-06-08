package com.solvd.db.factory;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IBaseDAO;

public abstract class AbstractDAOFactory {

    public abstract IBaseDAO getDAO(String tableName) throws DAONotFoundException;

}
