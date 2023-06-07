package com.solvd.db.dao.interfaces;

import com.solvd.db.model.Account;
import com.solvd.db.model.Customer;
import com.solvd.db.model.CustomerHasAccount;

import java.util.List;

public interface ICustomersHasAccountsDAO extends IBaseDAO<CustomerHasAccount> {
    String TABLE_NAME = "customers_has_accounts";

    List<Account> getAccountsByCustomerID(int customerId);

    List<Customer> getCustomersByAccountNumber(int accountNumber);
}