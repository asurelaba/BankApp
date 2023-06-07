package com.solvd.db.dao.interfaces;

import com.solvd.db.model.Address;

import java.util.List;

public interface IAddressDAO extends IBaseDAO<Address> {
    String TABLE_NAME = "addresses";

    public List<Address> getAddressByZipCode(String zipCode);
}
