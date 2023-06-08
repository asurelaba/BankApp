use bank;

TRUNCATE TABLE account_transactions;
DELETE FROM transaction_types;
DELETE FROM customers_has_accounts;
DELETE FROM accounts;
DELETE FROM account_types;
DELETE FROM customers;
DELETE FROM employees;
DELETE FROM employee_roles;
DELETE FROM persons;
DELETE FROM addresses;
DELETE FROM cities;
DELETE FROM countries;

ALTER TABLE cities AUTO_INCREMENT = 1;
ALTER TABLE addresses AUTO_INCREMENT = 1;
ALTER TABLE customers AUTO_INCREMENT = 1;
ALTER TABLE accounts AUTO_INCREMENT = 20230001;
ALTER TABLE persons AUTO_INCREMENT = 1;
ALTER TABLE cities AUTO_INCREMENT = 1;
ALTER TABLE employees AUTO_INCREMENT = 1;

INSERT INTO countries (country_name) 
VALUE ('mexico'),
	   ('belarus');

INSERT INTO countries (country_name) 
VALUES ('usa'),
	   ('japan'),
       ('canada'),
       ('india');

INSERT INTO cities (city_name, country_id) 
VALUES ('new york', (SELECT country_id FROM countries WHERE country_name = 'usa')),
	   ('houston', (SELECT country_id FROM countries WHERE country_name = 'usa')),
       ('san fransisco', (SELECT country_id FROM countries WHERE country_name = 'usa')),
       ('orlando', (SELECT country_id FROM countries WHERE country_name = 'usa')),
       ('chicago', (SELECT country_id FROM countries WHERE country_name = 'usa')),
       ('mumbai', (SELECT country_id FROM countries WHERE country_name = 'india'));

INSERT INTO employee_roles (role_name, job_description, salary)
VALUES ('branch manager', 'overseas the branch operations', 80000.00),
       ('teller', 'deals with deposit and withdrawal', 30000.00),
       ('relationship manager', 'manages customers with services', 30000.00),
       ('financial analyst', 'analyze financials for business improvement', 80000.00);

       
/*adding employees to the bank*/
INSERT INTO addresses (line1, line2, zip_code, city_id)
VALUES ('111 xyz dr',null, 11111, 1),
       ('222 vvv dr','line2 address', 22222, 2),
       ('333 xyz dr',null, 11111, 1);

INSERT INTO persons (first_name, last_name, date_of_birth, phone_number, email, address_id)
VALUES ('person1', 'person1last', '1999-01-01', '2342342345', 'person1@abcd.com', 1),
       ('person2', 'person2last', '1960-06-30', '2343455678', 'person2@abcd.com', 2),
       ('person3', 'person3last', '1975-11-30', '1231231234', 'person3@abcd.com', 2); 

INSERT INTO employees (person_id, employee_role_id, manager_id)
VALUES (1, (SELECT employee_role_id FROM employee_roles WHERE role_name = 'branch manager'),null),
       (2, (SELECT employee_role_id FROM employee_roles WHERE role_name = 'teller'), 1),
       (3, (SELECT employee_role_id FROM employee_roles WHERE role_name = 'financial analyst'), 1);

/*adding account types and transaction types*/
INSERT INTO transaction_types (transaction_type)
VALUE ('deposit'),
	  ('withdrawal'),
      ('transfer'),
      ('open'),
      ('close');

INSERT INTO account_types (account_type)
VALUE ('saving'),
	  ('checking'),
      ('loan');
       

/*adding customers to the bank*/
INSERT INTO addresses (line1, line2, zip_code, city_id)
VALUES ('555 xyz dr',null, 555555, 4),
       ('666 vvv dr','line2 address', 66666, 5),
       ('777 xyz dr',null, 77777, 6);

INSERT INTO persons (first_name, last_name, date_of_birth, phone_number, email, address_id)
VALUES ('person4', 'person4last', '1999-01-01', '2342342345', 'person4@abcd.com', (SELECT address_id FROM addresses WHERE zip_code = '555555')),
       ('person5', 'person5last', '1960-06-30', '2343455678', 'person5@abcd.com', (SELECT address_id FROM addresses WHERE zip_code = '66666')),
       ('person6', 'person6last', '1975-11-30', '1231231234', 'person6@abcd.com', (SELECT address_id FROM addresses WHERE zip_code = '77777')); 

INSERT INTO customers  (person_id)
VALUES (3),
       (4),
       (5),
       (6);
       
INSERT INTO accounts (balance,minbalance,account_type_id)
VALUES (100,100,(SELECT account_type_id FROM account_types WHERE account_type ='saving')),
       (200,100,(SELECT account_type_id FROM account_types WHERE account_type ='saving')),
       (500,100,(SELECT account_type_id FROM account_types WHERE account_type ='checking')),
       (500,100,(SELECT account_type_id FROM account_types WHERE account_type ='saving')),
       (500,100,(SELECT account_type_id FROM account_types WHERE account_type ='checking'));
       
INSERT INTO customers_has_accounts (customer_id, account_number)
VALUES (1,20230001),
       (1,20230002),
       (2,20230002),
       (3,20230004),
       (4,20230005);
 
INSERT INTO account_transactions (amount, transaction_date, account_number, transaction_type_id)
VALUES (100, NOW(), 20230001, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'open')),
	   (200, NOW(), 20230002, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'open')),
       (500, NOW(), 20230003, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'open')),
       (500, NOW(), 20230004, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'open')),
       (500, NOW(), 20230005, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'open')),
	   (+50, NOW(), 20230001, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'deposit')),
	   (-50, NOW(), 20230001, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'withdrawal')),
       (+50, NOW(), 20230002, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'deposit')),
       (+50, NOW(), 20230003, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'deposit')),
       (-1050, NOW(), 20230005, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'transfer')),
	   (+1050, NOW(), 20230004, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'transfer'));

/*update address of a customer*/
UPDATE addresses
SET line1 = '1001 new street dr',
	zip_code = '12345',
    city_id = 4
WHERE address_id  IN (
	SELECT persons.address_id 
      FROM customers, persons 
	 WHERE customers.customer_id = 2 AND customers.person_id = persons.person_id);

/*change city name mumbai to bombay*/
UPDATE cities 
SET city_name = 'bombay'
WHERE city_name LIKE 'mumbai';

/*update employee phone number*/
UPDATE persons
SET phone_number = '1111111111'
WHERE person_id IN (
	SELECT employees.person_id 
    FROM employees 
    WHERE employee_id = 3);

/*changing role of employee*/
UPDATE employees
SET employees.employee_role_id = (
	SELECT roles.employee_role_id 
    FROM employee_roles as roles 
    WHERE roles.role_name='teller')
WHERE employees.employee_id = 3;

/*update account for deposit*/
UPDATE accounts
SET accounts.balance = accounts.balance + 500
WHERE account_number = 20230002;

/*update loan to autoloan*/
UPDATE account_types
SET account_type = 'autoloan'
WHERE account_type = 'loan';

/*update the base salary for multiple roles*/
UPDATE employee_roles
SET salary = salary + (salary * 0.1) 
WHERE salary < 40000;

/*adding more responsiblities to manager*/
UPDATE employee_roles
SET job_description = CONCAT(job_description, 'adding more responsiblities')
WHERE role_name LIKE '%manager';

/*update the minbalance to all savings account*/
UPDATE accounts
SET minbalance = 1000
WHERE account_type_id IN (
	SELECT account_type_id
    FROM account_types
    WHERE account_type = 'saving');

/*update manager for an employee*/
UPDATE employees AS emp JOIN employees AS manager
SET emp.manager_id = (
	SELECT manager.employee_id FROM (
		SELECT m.employee_id 
		FROM employees AS m JOIN persons AS p ON m.person_id = p.person_id
		WHERE p.email='person2@abcd.com') AS manager)
WHERE emp.employee_id = 3 ;


/*customer 1 wants to close the account 20230002*/
DELETE FROM customers_has_accounts
WHERE  customer_id = 1 AND account_number = 20230002;

/*customer is removed from customers table*/
DELETE FROM customers_has_accounts 
WHERE customer_id = 2;
DELETE FROM customers
WHERE customer_id = 2;


/*Alter table*/
ALTER TABLE transaction_types
ADD description VARCHAR(100);

ALTER TABLE transaction_types
DROP description;

ALTER TABLE employee_roles
RENAME COLUMN employee_role_id TO role_id;

ALTER TABLE employee_roles
RENAME COLUMN role_id TO employee_role_id;

ALTER TABLE account_types MAX_ROWS = 10;

/*1 big statement to join all tables in the database.*/
SELECT *
FROM customers
	JOIN customers_has_accounts ON customers.customer_id = customers_has_accounts.customer_id
    JOIN accounts ON customers_has_accounts.account_number = accounts.account_number
    JOIN account_types ON accounts.account_type_id = account_types.account_type_id
    JOIN account_transactions ON account_transactions.account_number = accounts.account_number
    JOIN transaction_types ON account_transactions.transaction_type_id = transaction_types.transaction_type_id
    JOIN persons ON customers.person_id = persons.person_id
    JOIN addresses ON persons.address_id = addresses.address_id
    JOIN cities ON addresses.city_id = cities.city_id 
    JOIN countries ON cities.country_id  = countries.country_id
    JOIN employees
    JOIN employee_roles ON employees.employee_role_id = employee_roles.employee_role_id;

/*5 statements with left, right, inner, outer joins.*/
/*All employees along with their accounts if they have any*/
SELECT e.employee_id, p.first_name, a.account_number
FROM employees e 
	LEFT JOIN customers c ON e.person_id = c.person_id
    LEFT JOIN customers_has_accounts a ON c.customer_id = a.customer_id
    INNER JOIN persons p ON p.person_id = e.person_id;
    
/*Customers with id and their details*/
SELECT c.customer_id, p.first_name AS 'First name', p.email, p.date_of_birth, p.phone_number, a.line1,cit.city_name, a.zip_code, coun.country_name  
FROM customers c 
	JOIN persons p ON c.person_id = p.person_id
    JOIN addresses a ON p.address_id = a.address_id
    JOIN cities cit ON a.city_id = cit.city_id
	JOIN countries coun ON cit.country_id = coun.country_id;
    
/*all roles and employees with the role*/
SELECT *
FROM employees e
	RIGHT JOIN employee_roles r ON e.employee_role_id = r.employee_role_id;

/*All transaction of a customer*/
INSERT INTO customers_has_accounts (customer_id, account_number)
VALUES (1,20230002);
SELECT *
FROM customers_has_accounts c
	JOIN account_transactions a ON c.account_number = a.account_number
WHERE c.customer_id = 1;

/*full outer join*/
SELECT c.person_id, p.first_name
FROM customers c JOIN persons p ON c.person_id = p.person_id
UNION
SELECT e.person_id, p.first_name
FROM employees e JOIN persons p ON e.person_id = p.person_id;
    
/*7 statements with aggregate functions and group by and without having*/

/*count number of employees in the bank*/
SELECT COUNT(*) AS 'Num of Employees'
FROM employees;

/*count number of employees in the bank per Role*/
SELECT r.role_name AS 'Role', COUNT(e.employee_id) AS 'Num of employees'
FROM employees e RIGHT JOIN employee_roles r ON e.employee_role_id = r.employee_role_id
GROUP BY r.role_name;

/*max transaction amount per account*/
SELECT account_number, max(abs(a.amount))
FROM account_transactions a
GROUP BY a.account_number; 

/*total amount bank pays in salaries*/	
SELECT SUM(employee_roles.salary)
FROM employees JOIN employee_roles ON employees.employee_role_id = employee_roles.employee_role_id;

/*account is joint account?*/
INSERT INTO customers_has_accounts (customer_id, account_number)
VALUES (3,20230001);
SELECT c.account_number, CASE WHEN (COUNT(c.customer_id) > 1) THEN 'YES' ELSE 'NO' END
FROM customers_has_accounts c
GROUP BY c.account_number;

/*Number of transactions per account per customer*/
SELECT c.customer_id, a.account_number, COUNT(a.amount) As 'num_of_transactions' 
	  FROM account_transactions a, customers_has_accounts c
      WHERE c.account_number = a.account_number
	  GROUP BY c.customer_id, a.account_number
      ORDER BY c.customer_id;

/* Average age of employees in the bank*/
SELECT AVG(datediff(now(), p.date_of_birth)/365)
FROM employees e JOIN persons p ON e.person_id = p.person_id;


/*7 statements with aggregate functions and group by and with having.*/

/*count number of employees in the bank per Role with atleast 1 employee for a role*/
SELECT r.role_name AS 'Role', COUNT(e.employee_id) AS 'Num_of_employees'
FROM employees e RIGHT JOIN employee_roles r ON e.employee_role_id = r.employee_role_id
GROUP BY r.role_name HAVING Num_of_employees > 0;

/*Customers with more than 1 account*/
SELECT c.customer_id
FROM customers_has_accounts AS c
GROUP BY c.customer_id HAVING COUNT(c.account_number)>1;

/*account with least number of transactions*/
SELECT a.account_number, COUNT(*)
FROM account_transactions a
GROUP BY account_number HAVING COUNT(*) = (SELECT MIN(transaction_count.num_of_transactions) 
											FROM (SELECT  a.account_number, COUNT(a.amount) As 'num_of_transactions' 
												FROM account_transactions a
												GROUP BY a.account_number) AS transaction_count); 
                                                
/*customers who has more money with the bank*/
SELECT customers.customer_id, SUM(accounts.balance)
FROM customers_has_accounts
    JOIN accounts ON customers_has_accounts.account_number = accounts.account_number
    JOIN customers ON customers.customer_id = customers_has_accounts.customer_id
GROUP BY customers.customer_id HAVING SUM(accounts.balance) > 500;

/*Cities with more than 2 customers*/
INSERT INTO persons (first_name, last_name, date_of_birth, phone_number, email, address_id)
VALUES ('person7', 'person7last', '1999-01-01', '2342342345', 'person7@abcd.com', (SELECT address_id FROM addresses WHERE zip_code = '77777')),
       ('person8', 'person8last', '1960-06-30', '2343455678', 'person8@abcd.com', (SELECT address_id FROM addresses WHERE zip_code = '77777')),
       ('person9', 'person9last', '1975-11-30', '1231231234', 'person9@abcd.com', (SELECT address_id FROM addresses WHERE zip_code = '77777')); 

INSERT INTO customers  (person_id)
VALUES (7),
       (8),
       (9);
       
SELECT cities.city_name 
FROM customers
	JOIN persons ON customers.person_id = persons.person_id
    JOIN addresses ON persons.address_id = addresses.address_id
    JOIN cities ON addresses.city_id = cities.city_id
GROUP BY cities.city_name HAVING COUNT(persons.address_id) > 2;
    
    
/*employees with age more than average age*/
SELECT persons.first_name, datediff(now(), persons.date_of_birth)/365 AS 'age'
FROM persons
	JOIN employees ON employees.person_id = persons.person_id
WHERE datediff(now(), persons.date_of_birth)/365 >= (SELECT AVG(datediff(now(), p.date_of_birth)/365)
												FROM employees e 
                                                JOIN persons p ON e.person_id = p.person_id);
											
/*days where transactions are high*/
INSERT INTO account_transactions (amount, transaction_date, account_number, transaction_type_id)
VALUES (-50, '2023-06-03', 20230001, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'withdrawal')),
       (+50,  '2023-06-03', 20230002, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'deposit')),
       (+50, '2023-06-03', 20230003, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'deposit')),
       (-1050, '2023-06-03', 20230005, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'transfer')),
	   (+1050,  '2023-06-03', 20230004, (SELECT transaction_type_id from transaction_types WHERE transaction_type LIKE 'transfer'));

SELECT DATE(account_transactions.transaction_date)
FROM account_transactions
GROUP BY DATE(account_transactions.transaction_date) 
HAVING COUNT(*) > 3;  

/*account_types with atleast 1 account linked to it*/
SELECT account_type
FROM account_types
	JOIN accounts ON accounts.account_type_id = account_types.account_type_id
GROUP BY account_type HAVING COUNT(accounts.account_number) > 0;


                                     
						

                                                



