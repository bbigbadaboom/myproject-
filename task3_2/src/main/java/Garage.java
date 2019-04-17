import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Garage {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    Connection conn;

    Scanner scan = new Scanner(System.in);

    Garage(){
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            //DBRequest.Start(conn);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public void add(){
        try{
            Session s = HibernateUtil.getSessionFactory().openSession();
            List<Model> models = s.createQuery("from Model").list();
            System.out.println("Выберите машину для добавления");
            int i = 0;
            for (Model m: models) {
                Producer pr = m.getProducer();
                System.out.println(String.format("%d. %s %s", i++, pr.getName(), m.getModel()));
            }
            System.out.println("Любое другое число/знак чтобы вернутся назад");
            i = scan.nextInt();
            if (i >= 0 && i < models.size()){
                s.beginTransaction();
                s.persist(new GaragePlace(models.get(i)));
                s.getTransaction().commit();
            }
            else
                System.out.println("Машина не выбрана");
            s.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public void remove()
    {
        try{
            System.out.println("Выберите машину для удаления");
            int i = 0;
            Session s = HibernateUtil.getSessionFactory().openSession();
            List<GaragePlace> places = s.createQuery("from GaragePlace").list();
            for (GaragePlace p: places) {
                System.out.println(String.format("%d. %s %s", i++, p.getModel().getProducer().getName(),
                        p.getModel().getModel()));
            }
            System.out.println("Любое другое число/знак чтобы вернутся назад");
            i = scan.nextInt();
            if (i >= 0 && i < places.size()){
                //GaragePlace ent = s.load(GaragePlace.class, places.get(i).getId());
                //s.createQuery()
                s.beginTransaction();
                s.delete(places.get(i));
                s.getTransaction().commit();
                //s.delete(places.get(i));
            }
            else
                System.out.println("Машина не выбрана");
            s.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public void show(){
        try{
            Session s = HibernateUtil.getSessionFactory().openSession();
            List<GaragePlace> places = s.createQuery("from GaragePlace").list();
            int i = 0;
            System.out.println("ГАРАЖ");
            for (GaragePlace p: places) {
                System.out.println(String.format("%d. %s %s", i++, p.getModel().getProducer().getName(),
                        p.getModel().getModel()));
            }
            if (i == 0)
                System.out.println("пусто");
            System.out.println("Количество машин: " + i);
            System.out.println("Нажмите enter, чтобы продолжить");
            scan.nextLine();
            scan.nextLine();
            s.close();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
