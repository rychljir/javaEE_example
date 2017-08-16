package wa2.repositories;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import wa2.entities.*;

import java.util.ArrayList;
import java.util.List;

public class DBRepository {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            configuration.addAnnotatedClass(Person.class);
            configuration.addAnnotatedClass(Employee.class);
            configuration.addAnnotatedClass(Client.class);
            configuration.addAnnotatedClass(Address.class);
            configuration.addAnnotatedClass(Account.class);
            configuration.addAnnotatedClass(Mortgage.class);
            configuration.addAnnotatedClass(Credit.class);
            configuration.addAnnotatedClass(Calculation.class);

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public Client getClient(String id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Client> result = new ArrayList<Client>();
        try {
            result = session.createQuery("from Client c where c.id=" + id).list();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public Person getPerson(String id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Person> result = new ArrayList<Person>();
        try {
            result = session.createQuery("from Person c where c.id=" + id).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public void saveClient(Client client) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(client);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public void deleteClient(String id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Client c = new Client();
            List<Client> result = session.createQuery("from Client c where c.id =" + id).list();
            transaction.commit();
            if (result.size() > 0) {
                c = result.get(0);
                session.beginTransaction();
                session.delete(c);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public void updateClient(String id, String firstname, String lastname, String birth, Integer number) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Client c = new Client();
            List<Client> result = session.createQuery("from Client c where c.id =" + id).list();
            transaction.commit();
            if (result.size() > 0) {
                c = result.get(0);
            }
            if (firstname != null) {
                c.setFirstname(firstname);
            }
            if (lastname != null) {
                c.setLastname(lastname);
            }
            if (birth != null) {
                c.setBirth(birth);
            }
            if (number != null) {
                c.setNumber(number);
            }
            session.beginTransaction();
            session.update(c);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public Account getAccount(String id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Account> result = new ArrayList<Account>();
        try {
            result = session.createQuery("from Account c where c.id=" + id).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public void deleteAccount(String id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Account c = new Account();
            List<Account> result = session.createQuery("from Account c where c.id =" + id).list();
            transaction.commit();
            if (result.size() > 0) {
                c = result.get(0);
                session.beginTransaction();
                session.delete(c);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public void updateAccount(Integer id, Integer number, Integer balance, String currency, String idPerson) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Account c = new Account();
            List<Account> result = session.createQuery("from Account c where c.id =" + id).list();
            transaction.commit();
            if (result.size() > 0) {
                c = result.get(0);
            }
            if (number != null) {
                c.setNumber(number);
            }
            if (balance != null) {
                c.setBalance(balance);
            }
            if (currency != null) {
                c.setCurrency(currency);
            }
            if (idPerson != null) {
                Person p = getPerson(idPerson);
                c.setPerson(p);
            }
            session.beginTransaction();
            session.update(c);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public void saveAccount(Account account, String idPerson) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Person p = getPerson(idPerson);
        if (p != null) {
            account.setPerson(p);
        }
        try {
            session.save(account);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public Address getAddress(String id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Address> result = new ArrayList<Address>();
        try {
            result = session.createQuery("from Address c where c.id=" + id).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public void saveAdress(Address address) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(address);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public void deleteAddress(String id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Address c = new Address();
            List<Address> result = session.createQuery("from Address c where c.id =" + id).list();
            transaction.commit();
            if (result.size() > 0) {
                c = result.get(0);
                session.beginTransaction();
                session.delete(c);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public void updateAddress(String id, String street, String city, Integer postal) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Address c = new Address();
            List<Address> result = session.createQuery("from Address c where c.id =" + id).list();
            transaction.commit();
            if (result.size() > 0) {
                c = result.get(0);
            }
            if (street != null) {
                c.setStreet(street);
            }
            if (city != null) {
                c.setCity(city);
            }
            if (postal != null) {
                c.setPostal(postal);
            }
            session.beginTransaction();
            session.update(c);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public void updateCredit(String id, Integer value, Integer percentage, String idaccount) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Credit c = new Credit();
            List<Credit> result = session.createQuery("from Credit c where c.id =" + id).list();
            transaction.commit();
            if (result.size() > 0) {
                c = result.get(0);
            }
            if (value != null) {
                c.setValue(value);
            }
            if (percentage != null) {
                c.setPercentage(percentage);
            }
            if (idaccount != null) {
                Account a = getAccount(idaccount);
                c.setAccount(a);
            }
            session.beginTransaction();
            session.update(c);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public void deleteCredit(String id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Credit c = new Credit();
            List<Credit> result = session.createQuery("from Credit c where c.id =" + id).list();
            transaction.commit();
            if (result.size() > 0) {
                c = result.get(0);
                session.beginTransaction();
                session.delete(c);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public void saveCredit(Credit credit, String idaccount) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Account a = getAccount(idaccount);
        if (a != null) {
            credit.setAccount(a);
        }
        try {
            session.save(credit);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public Credit getCredit(String id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Credit> result = new ArrayList<Credit>();
        try {
            result = session.createQuery("from Credit c where c.id=" + id).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public Employee getEmployee(String id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Employee> result = new ArrayList<Employee>();
        try {
            result = session.createQuery("from Employee c where c.id=" + id).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public void saveEmployee(Employee employee) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public void deleteEmployee(String id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Employee c = new Employee();
            List<Employee> result = session.createQuery("from Employee c where c.id =" + id).list();
            transaction.commit();
            if (result.size() > 0) {
                c = result.get(0);
                session.beginTransaction();
                session.delete(c);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public void updateEmployee(String id, String firstname, String lastname, String birth, Integer salary) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Employee c = new Employee();
            List<Employee> result = session.createQuery("from Employee c where c.id =" + id).list();
            transaction.commit();
            if (result.size() > 0) {
                c = result.get(0);
            }
            if (firstname != null) {
                c.setFirstname(firstname);
            }
            if (lastname != null) {
                c.setLastname(lastname);
            }
            if (birth != null) {
                c.setBirth(birth);
            }
            if (salary != null) {
                c.setSalary(salary);
            }
            session.beginTransaction();
            session.update(c);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public Mortgage getMortgage(String id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Mortgage> result = new ArrayList<Mortgage>();
        try {
            result = session.createQuery("from Mortgage c where c.id=" + id).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public void saveMortgage(Mortgage mortgage, String idaccount) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Account a = getAccount(idaccount);
        if (a != null) {
            mortgage.setAccount(a);
        }
        try {
            session.save(mortgage);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public void updateMortgage(String id, Integer value, Integer years, String idaccount) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Mortgage c = new Mortgage();
            List<Mortgage> result = session.createQuery("from Mortgage c where c.id =" + id).list();
            transaction.commit();
            if (result.size() > 0) {
                c = result.get(0);
            }
            if (value != null) {
                c.setValue(value);
            }
            if (years != null) {
                c.setYears(years);
            }
            if (idaccount != null) {
                Account a = getAccount(idaccount);
                c.setAccount(a);
            }
            session.beginTransaction();
            session.update(c);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public void deleteMortgage(String id) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Mortgage c = new Mortgage();
            List<Mortgage> result = session.createQuery("from Mortgage c where c.id =" + id).list();
            transaction.commit();
            if (result.size() > 0) {
                c = result.get(0);
                session.beginTransaction();
                session.delete(c);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public List<Address> getAllAddress() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Address> result = new ArrayList<Address>();
        try {
            result = session.createQuery("from Address a ORDER BY a.id asc").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    public List<Client> getAllClients() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Client> result = new ArrayList<Client>();
        try {
            result = session.createQuery("from Client c ORDER BY c.id asc").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    public List<Employee> getAllEmployees() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Employee> result = new ArrayList<Employee>();
        try {
            result = session.createQuery("from Employee e ORDER BY e.id asc").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    public List getAccountsForPerson(String personId) {
        Person p = getPerson(personId);
        if (p == null){
            return null;
        }
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List result = new ArrayList();
        try {
            result = session.createQuery("from Account a,Person p where a.person = p and p.id = "+personId).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    public List getMortgageForAccount(String accountId) {
        Account a = getAccount(accountId);
        if (a == null){
            return null;
        }
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List result = new ArrayList();
        try {
            result = session.createQuery("from Mortgage m,Account a where m.account = a and a.id = "+accountId).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    public List getCreditForAccount(String accountId) {
        Account a = getAccount(accountId);
        if (a == null){
            return null;
        }
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List result = new ArrayList();
        try {
            result = session.createQuery("from Credit c,Account a where c.account = a and a.id = "+accountId).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    public void saveCalculation(Calculation calculation) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(calculation);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public Calculation getCalculation(String uuid) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Calculation> result = new ArrayList<Calculation>();
        try {
            Query query = session.createQuery("from Calculation where uuid= :uuid ");
            query.setParameter("uuid", uuid);
            result = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public void deleteCalculation(String uuid) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Calculation c;
        try {
            Query query = session.createQuery("from Calculation where uuid= :uuid ");
            query.setParameter("uuid", uuid);
            List<Calculation> result = query.list();
            transaction.commit();
            if (result.size() > 0) {
                c = result.get(0);
                session.beginTransaction();
                session.delete(c);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
    }

    public List<Account> getAllAccounts() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Account> result = new ArrayList<Account>();
        try {
            result = session.createQuery("from Account a ORDER BY a.id asc").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    public List<Mortgage> getAllMortgages() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Mortgage> result = new ArrayList<Mortgage>();
        try {
            result = session.createQuery("from Mortgage m ORDER BY m.id asc").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    public List<Credit> getAllCredits() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Credit> result = new ArrayList<Credit>();
        try {
            result = session.createQuery("from Credit c ORDER BY c.id asc").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    public List<Person> getAllPersons() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Person> result = new ArrayList<Person>();
        try {
            result = session.createQuery("from Person p ORDER BY p.id asc").list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        if (result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

/*
    public List<Customer> getAllCustomers() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        List<Customer> result = session
                .createQuery("from Customer c ORDER BY c.Name asc").list();
        transaction.commit();

        return result;
    }

    public Customer getCustomer(int custId) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        List<Customer> result = session
                .createQuery("from Customer c where c.id="+custId).list();
        transaction.commit();
        if(result.size()>0){
            return result.get(0);
        }
        return null;
    }

    public List<Customer> getAllCustomers(int start, int pageSize) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        List<Customer> result = session
                .createQuery("from Customer c ORDER BY c.Name asc")
                .setFirstResult(start)
                .setMaxResults(pageSize).list();

        transaction.commit();

        return result;
    }

    public int getNumberOfCustomers(){
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Customer> list = session.createQuery("from Customer c").list();
        transaction.commit();
        return list.size();
    }

    public void deleteCustomer(Customer customer) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Object persistentInstance = session.load(Customer.class, customer.getId());
        if (persistentInstance != null) {
            session.delete(persistentInstance);
        };
        transaction.commit();
    }

    public List getAllCarsWithManufaturer(int start, int pageSize) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        List result = session
                .createQuery("from Car c,Manufacturer m where c.manufacturer = m")
                //.createQuery("from Car c ORDER BY c.carName asc")
                .setFirstResult(start)
                .setMaxResults(pageSize).list();

        transaction.commit();

        return result;
    }

    public int getNumberOfCars(){
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Customer> list = session.createQuery("from Car c").list();
        transaction.commit();
        return list.size();
    }

    public Car getCar(int carId) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        List<Car> result = session
                .createQuery("from Car c where c.id="+carId).list();
        transaction.commit();
        if(result.size()>0){
            return result.get(0);
        }
        return null;
    }

    public List getCarsFromManufacturer(int start, int pageSize, int manId) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        List result = session
                .createQuery("from Car c,Manufacturer m where c.manufacturer = m and m.id = "+manId)
                //.createQuery("from Car c ORDER BY c.carName asc")
                .setFirstResult(start)
                .setMaxResults(pageSize).list();

        transaction.commit();

        return result;
    }

    public void saveCar(Car car) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.save(car);
        transaction.commit();
    }

    public List<Manufacturer> getAllManufacturers() {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        List result = session
                .createQuery("from Manufacturer m ORDER BY m.name asc")
                .list();

        transaction.commit();

        return result;
    }

    public Manufacturer getManufacturer(int manId) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        List<Manufacturer> result = session
                .createQuery("from Manufacturer m where m.id="+manId).list();
        transaction.commit();
        if(result.size()>0){
            return result.get(0);
        }
        return null;
    }

    public void deleteManufacturer(Manufacturer man) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Object persistentInstance = session.load(Manufacturer.class, man.getId());
        if (persistentInstance != null) {
            session.delete(persistentInstance);
        };
        transaction.commit();
    }

    public List getAddressForCustomer(int customerId) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        List result = session
                .createQuery("from Customer c,Address a where a.customer = c and c.id = "+customerId)
                .list();

        transaction.commit();

        return result;
    }

    public void saveAddress(Address addr) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.save(addr);
        transaction.commit();
    }

    public List getCarsForCustomer(int customerId) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        List result = session
                .createQuery("from Customer c,Car car where car.customer = c and c.id = "+customerId)
                .list();

        transaction.commit();

        return result;
    }

*/

}
