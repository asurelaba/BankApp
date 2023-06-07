package com.solvd.db.dao.interfaces;

import com.solvd.db.model.AccountTransaction;

import java.util.List;

public interface IAccountTransactionDAO extends IBaseDAO<AccountTransaction> {
    public List<AccountTransaction> getTransactionByAccountNumber(int accountNumber);
}
