package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.ICountryDAO;
import com.solvd.db.factory.JdbcDAOFactory;
import com.solvd.db.model.Country;

import java.util.List;

public class CountryService {

    private JdbcDAOFactory jdbcDAOFactory = new JdbcDAOFactory();

    public List<Country> getAllCountries() {
        try {
            ICountryDAO countryDAO = (ICountryDAO) jdbcDAOFactory.getDAO(Country.class.getSimpleName());
            return countryDAO.getAll();
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
        return null;
    }

    public void createCountry(Country country) {
        try {
            ICountryDAO countryDAO = (ICountryDAO) jdbcDAOFactory.getDAO(Country.class.getSimpleName());
            countryDAO.insert(country);
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
    }

    public Country getCountryByName(String countryName) {
        try {
            ICountryDAO countryDAO = (ICountryDAO) jdbcDAOFactory.getDAO(Country.class.getSimpleName());
            return countryDAO.getCountryByName(countryName);
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
        return null;
    }
}
