package com.solvd.db.dao.interfaces;

import com.solvd.db.model.Account;

import java.util.List;

public interface IAccountDAO extends IBaseDAO<Account> {
    public List<Account> getAccountByTypeId(int accountTypeId);
}
