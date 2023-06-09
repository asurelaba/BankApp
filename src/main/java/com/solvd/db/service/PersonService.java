package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IPersonDAO;
import com.solvd.db.factory.AbstractDAOFactory;
import com.solvd.db.factory.DAOFactoryManager;
import com.solvd.db.model.Address;
import com.solvd.db.model.City;
import com.solvd.db.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersonService {

    private static final Logger LOGGER = LogManager.getLogger(PersonService.class);
    private AbstractDAOFactory daoFactory = DAOFactoryManager.getDAOFactoryInstance();
    private IPersonDAO personDAO;

    public PersonService() throws DAONotFoundException {
        personDAO = (IPersonDAO) daoFactory.getDAO(Person.class.getSimpleName());
    }

    public Person getPersonWithAddress(int personId) {
        Person person = null;
        try {
            LOGGER.info("looking for " + Person.class.getSimpleName());
            person = personDAO.getById(personId);
            AddressService addressService = new AddressService();
            Address address = addressService.getAddressById(person.getAddress().getAddressId());
            CityService cityService = new CityService();
            City city = cityService.getCityByIdWithCountry(address.getCity().getCityId());
            address.setCity(city);
            person.setAddress(address);
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
        return person;
    }

    public void createPerson(Person person) {
        try {
            new AddressService().createAddress(person.getAddress());
            personDAO.insert(person);
        } catch (DAONotFoundException e) {
            LOGGER.error(e);
        }
    }
}
