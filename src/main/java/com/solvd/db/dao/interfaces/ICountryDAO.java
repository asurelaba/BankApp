package com.solvd.db.dao.interfaces;

import com.solvd.db.model.Country;

public interface ICountryDAO extends IBaseDAO<Country> {
    public Country getCountryByName(String countryName);
}
