package com.solvd.db.dao.interfaces;

import com.solvd.db.model.Account;
import com.solvd.db.model.Customer;
import com.solvd.db.model.CustomerHasAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public interface ICustomersHasAccountsDAO extends IBaseDAO<CustomerHasAccount> {

    Logger LOGGER = LogManager.getLogger(ICustomersHasAccountsDAO.class);
    String TABLE_NAME = "customers_has_accounts";

    List<Account> getAccountsByCustomerID(int customerId);

    List<Customer> getCustomersByAccountNumber(int accountNumber);

    //will not be implemented as getbycustomerId and getbyAccount_number returns multiple rows
    @Override
    default  CustomerHasAccount getById(int id) {
        LOGGER.error("getById is not supported by CustomersHasAccountsDAO");
        return null;
    }
}
