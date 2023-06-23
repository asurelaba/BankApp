package com.solvd.db.service;

import com.solvd.db.customexception.DAONotFoundException;
import com.solvd.db.dao.interfaces.IAddressDAO;
import com.solvd.db.factory.AbstractDAOFactory;
import com.solvd.db.factory.DAOFactoryManager;
import com.solvd.db.model.Address;
import com.solvd.db.model.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AddressService {

    private static final Logger LOGGER = LogManager.getLogger(AddressService.class);
    private AbstractDAOFactory daoFactory = DAOFactoryManager.getDAOFactoryInstance();
    private IAddressDAO addressDAO;

    public AddressService() throws DAONotFoundException {
        addressDAO = (IAddressDAO) daoFactory.getDAO(Address.class.getSimpleName());
    }

    public List<Address> getAllAddressesByCity(City city) {
        List<Address> result = new ArrayList<>();
        List<Address> addresses = addressDAO.getAll();
        for (Address address : addresses) {
            if (address.getCity().getCityId() == city.getCityId()) {
                result.add(address);
            }
        }
        return result;
    }

    public Address getAddressById(int addressId) {
        return addressDAO.getById(addressId);
    }

    public void createAddress(Address address) throws DAONotFoundException {
        CityService cityService = new CityService();
        cityService.createCity(address.getCity());
        City city = new CityService().getCityByIdWithCountry(2);
        address.setCity(city);
        addressDAO.insert(address);
    }
}
