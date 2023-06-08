package com.solvd.db.dao.interfaces;

import com.solvd.db.model.AccountTransaction;

import java.util.List;

public interface IAccountTransactionDAO extends IBaseDAO<AccountTransaction> {

    String TABLE_NAME = "account_transactions";

    public List<AccountTransaction> getTransactionByAccountNumber(int accountNumber);
}
