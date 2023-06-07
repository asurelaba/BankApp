package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.ICityDAO;
import com.solvd.db.dao.interfaces.ICountryDAO;
import com.solvd.db.factory.JdbcDAOFactory;
import com.solvd.db.model.City;
import com.solvd.db.model.Country;

import java.util.List;

public class CityService {
    private JdbcDAOFactory jdbcDAOFactory = new JdbcDAOFactory();

    public List<City> getAllCities(){
        try {
            ICityDAO cityDAO = (ICityDAO) jdbcDAOFactory.getDAO(City.class.getSimpleName());
            return cityDAO.getAll();
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
        return null;
    }

    public City getCityByIdWithCountry(int id){
        try {
            ICityDAO cityDAO = (ICityDAO) jdbcDAOFactory.getDAO(City.class.getSimpleName());
            City city =  cityDAO.getById(id);
            ICountryDAO countryDAO = (ICountryDAO) jdbcDAOFactory.getDAO(Country.class.getSimpleName());
            city.setCountry(countryDAO.getById(city.getCountry().getCountryId()));
            return city;
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
        return null;
    }
}
