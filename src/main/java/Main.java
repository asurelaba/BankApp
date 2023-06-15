import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solvd.db.dao.mysql.AccountDAO;
import com.solvd.db.dao.mysql.CustomersHasAccountsDAO;
import com.solvd.db.jackson.ParseJson;
import com.solvd.db.jaxbxml.ParseXMLJaxB;
import com.solvd.db.model.*;
import com.solvd.db.service.*;
import com.solvd.db.validateparsexml.ParseXMl;
import com.solvd.db.validateparsexml.ValidateXml;

import javax.xml.transform.Source;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        File customerSchema = new File("src/main/resources/xsdschema/customerxsd.xsd");
        File customerXML = new File("src/main/resources/inputxml/customer.xml");
        if (new ValidateXml().isXmlValid(customerXML, customerSchema)) {
            System.out.println(new ParseXMl().getCustomersFromXML(customerXML));
        }

        //marshall and unmarshall using JAXB
        ParseXMLJaxB<Customer> parseXMLJaxB = new ParseXMLJaxB();
        File customerJaxBXml = new File("src/main/resources/inputxml/customerJaxbInput.xml");
        Customer customer1 = parseXMLJaxB.unmarshall(Customer.class, customerJaxBXml);
        System.out.println("Customer object from XML: " + customer1);
        File outputFile = new File("target/customer.xml");
        parseXMLJaxB.marshall(customer1, outputFile);

        EmployeeService employeeService = new EmployeeService();
        List<Employee> employees = employeeService.getAllEmployeesWithManager();
        Bank bank = new Bank();
        bank.setEmployees(employees);
        ParseXMLJaxB<Bank> parseXMLJaxBEmployee = new ParseXMLJaxB();
        File employeesOutputFile = new File("target/employee.xml");
        parseXMLJaxBEmployee.marshall(bank, employeesOutputFile);

        //serialize and deserialize json with Jackson
        AccountService accountService = new AccountService();
        Account account1 = accountService.getAccountByAccountNumber(20230001);
        ParseJson<Account> parseJson = new ParseJson<>();
        File ouputJsonFile = new File("target/account.json");
        parseJson.serialize(account1, ouputJsonFile);

        File outputJsonCustomer = new File("target/customer.json");
        ParseJson<Customer> parseJsonCustomer = new ParseJson<>();
        parseJsonCustomer.serialize(customer1, outputJsonCustomer);

        AccountTransactionService accountTransactionService = new AccountTransactionService();
        List<AccountTransaction> accountTransactions = accountTransactionService.getAccountTransactionsByAccountNumber(20230001);
        ParseJson<AccountTransaction> accountTransactionParseJson = new ParseJson<>();
        File outputJsonTransactions = new File("target/transaction.json");
        accountTransactionParseJson.serialize(accountTransactions, outputJsonTransactions);

        ParseJson<Bank> bankParseJson = new ParseJson<>();
        File bankInputFile = new File("src/main/resources/inputjson/bank.json");
        System.out.println(bankParseJson.deserialize(bankInputFile, Bank.class));
    }
}