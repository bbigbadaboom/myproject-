import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    static {
        try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Producer.class);
            sessionFactory = configuration.configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void start(){
        try{
            Producer pr1 = new Producer();
            pr1.setName("Toyota");
            Producer pr2 = new Producer();
            pr2.setName("Kia");
            Producer pr3 = new Producer();
            pr3.setName("Renault");

            Model m1 = new Model();
            m1.setModel("Camry");
            m1.setProducer(pr1);

            Model m2 = new Model();
            m2.setModel("Soul");
            m2.setProducer(pr2);

            Model m3 = new Model();
            m3.setModel("Duster");
            m3.setProducer(pr3);

            Model m4 = new Model();
            m4.setModel("Megan");
            m4.setProducer(pr3);

            Session s = sessionFactory.openSession();
            s.beginTransaction();
            //s.persist(pr1);
            //s.persist(pr2);
            //s.persist(pr3);
            s.persist(m1);
            s.persist(m2);
            s.persist(m3);
            s.persist(m4);
            s.getTransaction().commit();
            s.close();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.err.println("Возможно, таблицы уже созданы");
        }
    }
}
