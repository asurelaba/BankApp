package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IAddressDAO;
import com.solvd.db.factory.JdbcDAOFactory;
import com.solvd.db.model.Address;
import com.solvd.db.model.City;

import java.util.ArrayList;
import java.util.List;

public class AddressService {

    private JdbcDAOFactory jdbcDAOFactory = new JdbcDAOFactory();

    public List<Address> getAllAddressesByCity(City city) {
        List<Address> result = new ArrayList<>();
        try {
            IAddressDAO addressDAO = (IAddressDAO) jdbcDAOFactory.getDAO(Address.class.getSimpleName());
            List<Address> addresses = addressDAO.getAll();
            for (Address address : addresses) {
                if (address.getCity().getCityId() == city.getCityId()) {
                    result.add(address);
                }
            }
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
        return result;
    }

    public Address getAddressById(int addressId) {
        try {
            IAddressDAO addressDAO = (IAddressDAO) jdbcDAOFactory.getDAO(Address.class.getSimpleName());
            return addressDAO.getById(addressId);
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
        return null;
    }

    public void createAddress(Address address) {
        try {
            CityService cityService = new CityService();
            cityService.createCity(address.getCity());
            City city = new CityService().getCityByIdWithCountry(2);
            IAddressDAO addressDAO = (IAddressDAO) jdbcDAOFactory.getDAO(Address.class.getSimpleName());
            address.setCity(city);
            addressDAO.insert(address);
        } catch (DAONotFoundException e) {
            System.out.println(e);
        }
    }
}
