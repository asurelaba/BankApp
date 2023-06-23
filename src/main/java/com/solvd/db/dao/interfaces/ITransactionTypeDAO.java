package com.solvd.db.dao.interfaces;

import com.solvd.db.model.TransactionType;

public interface ITransactionTypeDAO extends IBaseDAO<TransactionType> {

    String TABLE_NAME = "transaction_types";

    TransactionType getTransactionTypeByName(String name);
}
