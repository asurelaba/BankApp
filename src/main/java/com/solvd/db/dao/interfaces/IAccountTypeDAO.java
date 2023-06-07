package com.solvd.db.dao.interfaces;

import com.solvd.db.model.AccountType;

public interface IAccountTypeDAO extends IBaseDAO<AccountType> {
    public AccountType getByName(String accountType);
}
