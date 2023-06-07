package com.solvd.db.dao.interfaces;

import com.solvd.db.model.AccountType;

public interface IAccountTypeDAO extends IBaseDAO<AccountType> {
    String TABLE_NAME = "account_types";
    public AccountType getByName(String accountType);
}
