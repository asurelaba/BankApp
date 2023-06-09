import com.solvd.db.dao.mysql.AccountDAO;
import com.solvd.db.dao.mysql.CustomersHasAccountsDAO;
import com.solvd.db.model.*;
import com.solvd.db.service.CustomerService;
import com.solvd.db.service.PersonService;
import com.solvd.db.validateparsexml.ParseXMl;
import com.solvd.db.validateparsexml.ValidateXml;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {

        //Initial usage of DAO in main
        System.out.println(new AccountDAO().getById(20230004));
        Account account = new Account();
        account.setMinBalance(400);
        account.setBalance(1000);
        AccountType a = new AccountType();
        a.setAccountTypeId(2);
        account.setAccountType(a);
        new AccountDAO().insert(account);
        System.out.println(new AccountDAO().getAccountByTypeId(2));

        //Using services, person service inturn uses address, city, country DAO
        PersonService personService = new PersonService();
        Person person = personService.getPersonWithAddress(4);
        System.out.println(person);

        Customer customer = new Customer();
        Person person1 = new Person();
        person1.setFirstName("person10");
        person1.setLastName("person10Last");
        person1.setEmail("person10@abcd.com");
        person1.setPhoneNumber("1231231234");
        LocalDate d = LocalDate.parse("1999-09-09", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        person1.setDateOfBirth(Date.valueOf(d));
        Address address = new Address();
        address.setLine1("123 person10 lane");
        address.setLine2("apt 0");
        address.setZipCode("12345");
        City city = new City();
        city.setCityName("houston");
        Country country = new Country();
        country.setCountryName("usa");
        city.setCountry(country);
        address.setCity(city);
        person1.setAddress(address);
        customer.setPerson(person1);
        CustomerService customerService = new CustomerService();
        customerService.createCustomer(customer);

        //calling unsupported operations
        System.out.println(new CustomersHasAccountsDAO().getById(1));

        //validate and parse Customers xml
        if (new ValidateXml().isXmlValid()) {
            System.out.println(new ParseXMl().getCustomersFromXML());
        }
    }
}