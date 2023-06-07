package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IPersonDAO;
import com.solvd.db.factory.JdbcDAOFactory;
import com.solvd.db.model.Address;
import com.solvd.db.model.City;
import com.solvd.db.model.Person;

public class PersonService {
    private JdbcDAOFactory jdbcDAOFactory = new JdbcDAOFactory();

    public Person getPersonWithAddress(int personId) {
        Person person = null;
        try {
            System.out.println("looking for " + Person.class.getSimpleName());
            IPersonDAO personDAO = (IPersonDAO) jdbcDAOFactory.getDAO(Person.class.getSimpleName());

            person = personDAO.getById(personId);
            AddressService addressService = new AddressService();
            Address address = addressService.getAddressById(person.getAddress().getAddressId());
            CityService cityService = new CityService();
            City city = cityService.getCityByIdWithCountry(address.getCity().getCityId());
            address.setCity(city);
            person.setAddress(address);
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
        return person;
    }
}
