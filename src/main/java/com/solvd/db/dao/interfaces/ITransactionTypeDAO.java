package com.solvd.db.dao.interfaces;

import com.solvd.db.model.TransactionType;

public interface ITransactionTypeDAO extends IBaseDAO<TransactionType> {
    public TransactionType getTransactionTypeByName(String name);
}
