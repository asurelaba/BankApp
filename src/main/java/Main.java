import com.solvd.db.dao.mysql.AccountDAO;
import com.solvd.db.model.Account;
import com.solvd.db.model.AccountType;
import com.solvd.db.model.Person;
import com.solvd.db.service.PersonService;
import com.solvd.utilities.ConnectionPool;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws InterruptedException, SQLException {

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

        //Using services
        PersonService personService = new PersonService();
        Person person = personService.getPersonWithAddress(4);
        System.out.println(person);
    }
}