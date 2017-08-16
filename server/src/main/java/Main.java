import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import wa2.entities.*;

import javax.swing.text.html.HTMLDocument;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mudrama1 on 15.03.2017.
 */
public class Main {
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

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(String[] args) {
        fillDbWithTestData();

        Session session = getSession();
        Transaction transaction = session.beginTransaction();


      /*  Credit p = new Credit();
        List<Credit> result = session
                .createQuery("from Credit p where p.id=3").list();
        transaction.commit();
        if(result.size()>0){
            p = result.get(0);
        }
        session.beginTransaction();
        session.delete(p);
        transaction.commit();*/
    }

    private static void fillDbWithTestData(){
        Session session = getSession();

        Client cl = new Client();
        cl.setNumber(998665332);
        cl.setLastname("Lojzicek");
        cl.setFirstname("Lojza");
        cl.setBirth("20.05.1982");
        Transaction transaction = session.beginTransaction();
        session.save(cl);
        transaction.commit();

        cl = new Client();
        cl.setNumber(112445778);
        cl.setLastname("Pepicek");
        cl.setFirstname("Pepa");
        cl.setBirth("23.01.1992");
        transaction = session.beginTransaction();
        session.save(cl);
        transaction.commit();

        cl = new Client();
        cl.setNumber(778445112);
        cl.setLastname("Tondicek");
        cl.setFirstname("Tonda");
        cl.setBirth("02.03.1990");
        transaction = session.beginTransaction();
        session.save(cl);
        transaction.commit();

        cl = new Client();
        cl.setNumber(1545646);
        cl.setLastname("Hozicek");
        cl.setFirstname("Honza");
        cl.setBirth("05.08.1972");
        transaction = session.beginTransaction();
        session.save(cl);
        transaction.commit();

        Address a = new Address();
        a.setCity("Praha");
        a.setPostal(43401);
        a.setStreet("Sezamova 12");
        session = getSession();
        transaction = session.beginTransaction();
        session.save(a);
        transaction.commit();

        a = new Address();
        a.setCity("Praha");
        a.setPostal(10000);
        a.setStreet("Centrumova 13");
        session = getSession();
        transaction = session.beginTransaction();
        session.save(a);
        transaction.commit();

        a = new Address();
        a.setCity("Praha");
        a.setPostal(15820);
        a.setStreet("Googlova 14");
        session = getSession();
        transaction = session.beginTransaction();
        session.save(a);
        transaction.commit();

        a = new Address();
        a.setCity("Praha");
        a.setPostal(77845);
        a.setStreet("Bingova 11");
        session = getSession();
        transaction = session.beginTransaction();
        session.save(a);
        transaction.commit();

        for (int i = 1; i < 5; i++) {
            transaction = session.beginTransaction();
            Person p = new Person();
            List<Person> result = session
                    .createQuery("from Person p where p.id="+ i).list();
            transaction.commit();
            if(result.size()>0){
                p = result.get(0);
            }

            transaction = session.beginTransaction();

            List<Address> result2 = session
                    .createQuery("from Address a where a.id=" + i).list();
            transaction.commit();
            if(result.size()>0){
                a = result2.get(0);
            }

            p.getAddresses().add(a);
            transaction = session.beginTransaction();
            session.save(p);
            transaction.commit();

            Account acc = new Account();
            acc.setBalance(i*190000);
            acc.setCurrency("CZK");
            acc.setPerson(p);
            acc.setNumber(i*561321);

            transaction = session.beginTransaction();
            session.save(acc);
            transaction.commit();

            Mortgage mort = new Mortgage();
            mort.setValue(i*15613);
            mort.setAccount(acc);
            mort.setYears(i*3);

            transaction = session.beginTransaction();
            session.save(mort);
            transaction.commit();

            Credit cr = new Credit();
            cr.setAccount(acc);
            cr.setPercentage(15);
            cr.setValue(i*11535);

            transaction = session.beginTransaction();
            session.save(cr);
            transaction.commit();
        }

        Employee e = new Employee();
        e.setSalary(45689);
        e.setLastname("Lenková");
        e.setFirstname("Lenka");
        e.setBirth("20.02.1988");
        transaction = session.beginTransaction();
        session.save(e);
        transaction.commit();

        e = new Employee();
        e.setSalary(41123);
        e.setLastname("Alenková");
        e.setFirstname("Alenka");
        e.setBirth("15.02.1982");
        transaction = session.beginTransaction();
        session.save(e);
        transaction.commit();

        e = new Employee();
        e.setSalary(84123);
        e.setLastname("Janicková");
        e.setFirstname("Jana");
        e.setBirth("18.12.1990");
        transaction = session.beginTransaction();
        session.save(e);
        transaction.commit();

        e = new Employee();
        e.setSalary(22568);
        e.setLastname("Evová");
        e.setFirstname("Eva");
        e.setBirth("10.02.1968");
        transaction = session.beginTransaction();
        session.save(e);
        transaction.commit();

        a = new Address();
        a.setCity("Praha");
        a.setPostal(47895);
        a.setStreet("Volvova 12");
        session = getSession();
        transaction = session.beginTransaction();
        session.save(a);
        transaction.commit();

        a = new Address();
        a.setCity("Praha");
        a.setPostal(12203);
        a.setStreet("Ferrarova 13");
        session = getSession();
        transaction = session.beginTransaction();
        session.save(a);
        transaction.commit();

        a = new Address();
        a.setCity("Praha");
        a.setPostal(15233);
        a.setStreet("Trabantova 14");
        session = getSession();
        transaction = session.beginTransaction();
        session.save(a);
        transaction.commit();

        a = new Address();
        a.setCity("Praha");
        a.setPostal(13325);
        a.setStreet("Citroenova 11");
        session = getSession();
        transaction = session.beginTransaction();
        session.save(a);
        transaction.commit();

        for (int i = 5; i < 9; i++) {
            transaction = session.beginTransaction();
            Person p = new Person();
            List<Person> result = session
                    .createQuery("from Person p where p.id="+ i).list();
            transaction.commit();
            if(result.size()>0){
                p = result.get(0);
            }

            transaction = session.beginTransaction();

            List<Address> result2 = session
                    .createQuery("from Address a where a.id=" + i).list();
            transaction.commit();
            if(result.size()>0){
                a = result2.get(0);
            }

            p.getAddresses().add(a);
            transaction = session.beginTransaction();
            session.save(p);
            transaction.commit();

            Account acc = new Account();
            acc.setBalance(i*25000);
            acc.setCurrency("CZK");
            acc.setPerson(p);
            acc.setNumber(i*12552);

            transaction = session.beginTransaction();
            session.save(acc);
            transaction.commit();

            Mortgage mort = new Mortgage();
            mort.setValue(i*12456);
            mort.setAccount(acc);
            mort.setYears(i*3);

            transaction = session.beginTransaction();
            session.save(mort);
            transaction.commit();

            Credit cr = new Credit();
            cr.setAccount(acc);
            cr.setPercentage(15);
            cr.setValue(i*10568);

            transaction = session.beginTransaction();
            session.save(cr);
            transaction.commit();
        }

        /*transaction = session.beginTransaction();
        List result3 = session
                .createQuery("from Person p where p.idperson=5").list();
        transaction.commit();*/

    }
}

