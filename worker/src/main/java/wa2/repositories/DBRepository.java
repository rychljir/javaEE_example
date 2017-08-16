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

    public boolean updateCalculation(String uuid, Integer value) {
        boolean success;
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Calculation c = new Calculation();
            Query query = session.createQuery("from Calculation where uuid= :uuid ");
            query.setParameter("uuid", uuid);
            List <Calculation> result = query.list();
            transaction.commit();
            if (result.size() > 0) {
                c = result.get(0);
            }
            if (value != null) {
                c.setResult(value);
            }
            session.beginTransaction();
            session.update(c);
            transaction.commit();
            success = true;
        } catch (Exception e) {
            success = false;
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.flush();
                session.close();
            }
        }
        return success;
    }
}
