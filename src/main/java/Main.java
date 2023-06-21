import com.solvd.db.dao.interfaces.IAccountDAO;
import com.solvd.db.dao.interfaces.IAccountTypeDAO;

import com.solvd.db.dao.mysql.AccountDAO;
import com.solvd.db.dao.mysql.CustomersHasAccountsDAO;
import com.solvd.db.model.*;
import com.solvd.db.service.*;
import com.solvd.utilities.jackson.JsonParserUtil;
import com.solvd.utilities.jaxbxml.XmlJAXBParser;
import com.solvd.utilities.validateparsexml.ValidateXmlUtil;
import com.solvd.utilities.validateparsexml.XMLParserUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        //Initial usage of DAO in main
        LOGGER.info(new AccountDAO().getById(20230004));
        Account account = new Account();
        account.setMinBalance(400);
        account.setBalance(1000);
        AccountType a = new AccountType();
        a.setAccountTypeId(2);
        account.setAccountType(a);
        new AccountDAO().insert(account);
        LOGGER.info(new AccountDAO().getAccountByTypeId(2));

        //Using services, person service inturn uses address, city, country DAO
        Person person = new PersonService().getPersonWithAddress(4);
        LOGGER.info(person);

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
        LOGGER.info(new CustomersHasAccountsDAO().getById(1));

        //validate and parse Customers xml
        File customerSchema = new File("src/main/resources/xsdschema/customerxsd.xsd");
        File customerXML = new File("src/main/resources/inputxml/customer.xml");
        if (new ValidateXmlUtil().isXmlValid(customerXML, customerSchema)) {
            LOGGER.info(new XMLParserUtil().getCustomersFromXML(customerXML));
        }

        //marshall and unmarshall using JAXB
        XmlJAXBParser<Customer> parseXMLJaxB = new XmlJAXBParser();
        File customerJaxBXml = new File("src/main/resources/inputxml/customerJaxbInput.xml");
        Customer customer1 = parseXMLJaxB.unmarshall(Customer.class, customerJaxBXml);
        LOGGER.info("Customer object from XML: " + customer1);
        File outputFile = new File("target/customer.xml");
        parseXMLJaxB.marshall(customer1, outputFile);

        List<Employee> employees = new EmployeeService().getAllEmployeesWithManager();
        Bank bank = new Bank();
        bank.setEmployees(employees);
        XmlJAXBParser<Bank> parseXMLJaxBEmployee = new XmlJAXBParser();
        File employeesOutputFile = new File("target/employee.xml");
        parseXMLJaxBEmployee.marshall(bank, employeesOutputFile);

        //serialize and deserialize json with Jackson
        Account account1 = new AccountService().getAccountByAccountNumber(20230001);
        JsonParserUtil<Account> parseJson = new JsonParserUtil<>();
        File ouputJsonFile = new File("target/account.json");
        parseJson.serialize(account1, ouputJsonFile);

        File outputJsonCustomer = new File("target/customer.json");
        new JsonParserUtil<Customer>().serialize(customer1, outputJsonCustomer);

        List<AccountTransaction> accountTransactions = new AccountTransactionService().getAccountTransactionsByAccountNumber(20230001);
        File outputJsonTransactions = new File("target/transaction.json");
        new JsonParserUtil<AccountTransaction>().serialize(accountTransactions, outputJsonTransactions);

        File bankInputFile = new File("src/main/resources/inputjson/bank.json");
        LOGGER.info(new JsonParserUtil<Bank>().deserialize(bankInputFile, Bank.class));

        LOGGER.info("--------------Using Mybatis DAO-------------");
        AccountService accountService = new AccountService();
        LOGGER.info(accountService.getAccountByAccountNumber(20230003));

        Account account2 = new Account();
        System.out.println(account2);
        AccountType aa = new AccountType();
        aa.setAccountTypeId(1);
        aa.setAccountType("saving");
        account2.setAccountType(aa);
        account2.setBalance(10000000);
        account2.setMinBalance(90);
        accountService.createAccount(account2);
        System.out.println(account2);
    }
}