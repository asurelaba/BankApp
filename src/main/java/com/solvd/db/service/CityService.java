package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.ICityDAO;
import com.solvd.db.dao.interfaces.ICountryDAO;
import com.solvd.db.factory.AbstractDAOFactory;
import com.solvd.db.factory.DAOFactoryManager;
import com.solvd.db.model.City;
import com.solvd.db.model.Country;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CityService {

    private static final Logger LOGGER = LogManager.getLogger(CityService.class);
    private AbstractDAOFactory daoFactory = DAOFactoryManager.getDAOFactoryInstance();

    public List<City> getAllCities() {
        try {
            ICityDAO cityDAO = (ICityDAO) daoFactory.getDAO(City.class.getSimpleName());
            return cityDAO.getAll();
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
        return null;
    }

    public City getCityByIdWithCountry(int id) {
        try {
            ICityDAO cityDAO = (ICityDAO) daoFactory.getDAO(City.class.getSimpleName());
            City city = cityDAO.getById(id);
            ICountryDAO countryDAO = (ICountryDAO) daoFactory.getDAO(Country.class.getSimpleName());
            city.setCountry(countryDAO.getById(city.getCountry().getCountryId()));
            return city;
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
        return null;
    }

    public void createCity(City city) {
        try {
            ICityDAO cityDAO = (ICityDAO) daoFactory.getDAO(City.class.getSimpleName());
            CountryService countryService = new CountryService();
            Country country = countryService.getCountryByName(city.getCountry().getCountryName());
            if (country == null) {
                countryService.createCountry(city.getCountry());
                country = countryService.getCountryByName(city.getCountry().getCountryName());
            }
            city.setCountry(country);
            cityDAO.insert(city);
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
    }
}
