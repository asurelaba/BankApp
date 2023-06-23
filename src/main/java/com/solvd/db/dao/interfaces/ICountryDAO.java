package com.solvd.db.dao.interfaces;

import com.solvd.db.model.Country;

public interface ICountryDAO extends IBaseDAO<Country> {

    String TABLE_NAME = "countries";

    Country getCountryByName(String countryName);
}
