package com.solvd.utilities.validateparsexml;

import com.solvd.db.model.*;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class XMLParserUtil {

    public List<Customer> getCustomersFromXML(File customerXML) {
        List<Customer> customers = new ArrayList<>();
        Customer customer = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            InputStream inputStream = new FileInputStream(customerXML);
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(inputStream);
            while (xmlEventReader.hasNext()) {
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    Attribute attributeId = startElement.getAttributeByName(new QName("id"));
                    switch (startElement.getName().getLocalPart().toLowerCase()) {
                        case "customers":
                            break;
                        case "customer":
                            customer = new Customer();
                            customer.setPerson(new Person());
                            customer.setCustomerId(Integer.parseInt(attributeId.getValue()));
                            customers.add(customer);
                            break;
                        case "person":
                            Person person = customer.getPerson();
                            person.setPersonId(Integer.parseInt(attributeId.getValue()));
                            person.setAddress(new Address());
                            person.setPersonId(Integer.parseInt(attributeId.getValue()));
                            break;
                        case "firstname":
                            customer.getPerson().setFirstName(xmlEventReader.nextEvent().asCharacters().getData());
                            break;
                        case "lasname":
                            customer.getPerson().setLastName(xmlEventReader.nextEvent().asCharacters().getData());
                            break;
                        case "dob":
                            customer.getPerson().setDateOfBirth(Date.valueOf(LocalDate.parse(xmlEventReader.nextEvent().asCharacters().getData())));
                            break;
                        case "phonenumber":
                            customer.getPerson().setPhoneNumber(xmlEventReader.nextEvent().asCharacters().getData());
                            break;
                        case "email":
                            customer.getPerson().setEmail(xmlEventReader.nextEvent().asCharacters().getData());
                            break;
                        case "address":
                            Address address = customer.getPerson().getAddress();
                            address.setAddressId(Integer.parseInt(attributeId.getValue()));
                            address.setCity(new City());
                            break;
                        case "line1":
                            customer.getPerson().getAddress()
                                    .setLine1(xmlEventReader.nextEvent().asCharacters().getData());
                            break;
                        case "line2":
                            customer.getPerson().getAddress()
                                    .setLine2(xmlEventReader.nextEvent().asCharacters().getData());
                            break;
                        case "zipcode":
                            customer.getPerson().getAddress()
                                    .setZipCode(xmlEventReader.nextEvent().asCharacters().getData());
                            break;
                        case "city":
                            City city = customer.getPerson().getAddress().getCity();
                            city.setCityId(Integer.parseInt(attributeId.getValue()));
                            city.setCountry(new Country());
                            break;
                        case "cityname":
                            customer.getPerson().getAddress().getCity()
                                    .setCityName(xmlEventReader.nextEvent().asCharacters().getData());
                            break;
                        case "country":
                            customer.getPerson().getAddress().getCity().getCountry()
                                    .setCountryId(Integer.parseInt(attributeId.getValue()));
                            break;
                        case "countryname":
                            customer.getPerson().getAddress().getCity().getCountry()
                                    .setCountryName(xmlEventReader.nextEvent().asCharacters().getData());
                            break;
                        case "accounts":
                            List<Account> accounts = new ArrayList<>();
                            customer.setAccounts(accounts);
                            break;
                        case "account":
                            customer.getAccounts().add(new Account());
                            break;
                        case "accountnumber":
                            customer.getAccounts().get(customer.getAccounts().size() - 1)
                                    .setAccountNumber(Integer.parseInt(xmlEventReader.nextEvent().asCharacters().getData()));
                            break;
                        case "balance":
                            customer.getAccounts().get(customer.getAccounts().size() - 1)
                                    .setBalance(Integer.parseInt(xmlEventReader.nextEvent().asCharacters().getData()));
                            break;
                        case "minbalance":
                            customer.getAccounts().get(customer.getAccounts().size() - 1)
                                    .setMinBalance(Integer.parseInt(xmlEventReader.nextEvent().asCharacters().getData()));
                            break;
                        case "accounttype":
                            Account account = customer.getAccounts().get(customer.getAccounts().size() - 1);
                            account.setAccountType(new AccountType());
                            account.getAccountType().setAccountTypeId(Integer.parseInt(attributeId.getValue()));
                            break;
                        case "typename":
                            customer.getAccounts().get(customer.getAccounts().size() - 1).getAccountType()
                                    .setAccountType(xmlEventReader.nextEvent().asCharacters().getData());
                            break;
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return customers;
    }
}
