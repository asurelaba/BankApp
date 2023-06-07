package com.solvd.db.dao.interfaces;

import com.solvd.db.model.City;

import java.util.List;

public interface ICityDAO extends IBaseDAO<City> {
    public List<City> getCityByName(String cityName);
}
