package com.solvd.db.dao.interfaces;

import com.solvd.db.model.Account;

public interface ICustomersHasAccounts extends IBaseDAO<Account> {
    String TABLE_NAME = "customers_has_accounts";
}
