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
}
