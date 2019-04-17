import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
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
            DBRequest.Start(conn);
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public void add(){
        try{
            System.out.println("Выберите машину для добавления");
            int i = 0;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(DBRequest.SQL_CARS_REQUEST);
            List<Integer> ids = new LinkedList<>();
            while(rs.next()){
                ids.add(rs.getInt("id"));
                System.out.println(String.format("%d. %s %s", i++, rs.getString("producer"),
                        rs.getString("model")));
            }
            System.out.println("Любое другое число/знак чтобы вернутся назад");
            i = scan.nextInt();
            if (i >= 0 && i < ids.size())
                rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
                if (rs.next())
                    stmt.executeUpdate(String.format("INSERT INTO garage(car_id) VALUES (%d)", ids.get(i)));
            else
                System.out.println("Машина не выбрана");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public void remove()
    {
        try{
            System.out.println("Выберите машину для удаления");
            int i = 0;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(DBRequest.SQL_GARAGE_REQUEST);
            List<Integer> ids = new LinkedList<>();
            while(rs.next()){
                ids.add(rs.getInt("id"));
                System.out.println(String.format("%d. %s %s", i++, rs.getString("producer"),
                        rs.getString("model")));
            }
            System.out.println("Любое другое число/знак чтобы вернутся назад");
            i = scan.nextInt();
            if (i >= 0 && i < ids.size())
                stmt.executeUpdate("DELETE FROM garage WHERE id = " + ids.get(i).toString());
            else
                System.out.println("Машина не выбрана");
        }
            catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public void show(){
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(DBRequest.SQL_GARAGE_REQUEST);
            int i = 0;
            System.out.println("ГАРАЖ");
            while(rs.next()){
                System.out.println(String.format("%d. %s %s", rs.getInt("id"), rs.getString("producer"),
                        rs.getString("model")));
                i++;
            }
            if (i == 0)
                System.out.println("пусто");
            System.out.println("Количество машин: " + i);
            System.out.println("Нажмите enter, чтобы продолжить");
            scan.nextLine();
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
