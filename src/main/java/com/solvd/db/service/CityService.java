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
    private ICityDAO cityDAO;

    public CityService() throws DAONotFoundException {
        cityDAO = (ICityDAO) daoFactory.getDAO(City.class.getSimpleName());
    }

    public List<City> getAllCities() {
        return cityDAO.getAll();
    }

    public City getCityByIdWithCountry(int id) {
        City city = cityDAO.getById(id);
        ICountryDAO countryDAO = null;
        try {
            countryDAO = (ICountryDAO) daoFactory.getDAO(Country.class.getSimpleName());
            city.setCountry(countryDAO.getById(city.getCountry().getCountryId()));
            return city;
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
        return null;
    }

    public void createCity(City city) throws DAONotFoundException {
        CountryService countryService = new CountryService();
        Country country = countryService.getCountryByName(city.getCountry().getCountryName());
        if (country == null) {
            countryService.createCountry(city.getCountry());
            country = countryService.getCountryByName(city.getCountry().getCountryName());
        }
        city.setCountry(country);
        cityDAO.insert(city);
    }
}
