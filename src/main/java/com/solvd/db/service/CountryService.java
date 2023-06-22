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

    public List<Country> getAllCountries() {
        try {
            ICountryDAO countryDAO = (ICountryDAO) daoFactory.getDAO(Country.class.getSimpleName());
            return countryDAO.getAll();
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
        return null;
    }

    public void createCountry(Country country) {
        try {
            ICountryDAO countryDAO = (ICountryDAO) daoFactory.getDAO(Country.class.getSimpleName());
            countryDAO.insert(country);
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
    }

    public Country getCountryByName(String countryName) {
        try {
            ICountryDAO countryDAO = (ICountryDAO) daoFactory.getDAO(Country.class.getSimpleName());
            return countryDAO.getCountryByName(countryName);
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
        return null;
    }
}
