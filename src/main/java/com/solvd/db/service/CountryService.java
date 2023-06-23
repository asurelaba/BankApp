package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.ICountryDAO;
import com.solvd.db.factory.AbstractDAOFactory;
import com.solvd.db.factory.DAOFactoryManager;
import com.solvd.db.model.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CountryService {

    private static final Logger LOGGER = LogManager.getLogger(CountryService.class);
    private AbstractDAOFactory daoFactory = DAOFactoryManager.getDAOFactoryInstance();
    private ICountryDAO countryDAO;

    public CountryService() throws DAONotFoundException {
        countryDAO = (ICountryDAO) daoFactory.getDAO(Country.class.getSimpleName());
    }

    public List<Country> getAllCountries() {
        return countryDAO.getAll();
    }

    public void createCountry(Country country) {
        countryDAO.insert(country);
    }

    public Country getCountryByName(String countryName) {
        return countryDAO.getCountryByName(countryName);
    }
}
