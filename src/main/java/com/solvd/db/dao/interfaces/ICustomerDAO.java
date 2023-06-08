package com.solvd.db.dao.interfaces;

import com.solvd.db.model.Customer;

public interface ICustomerDAO extends IBaseDAO<Customer> {

    String TABLE_NAME = "customers";
}
