import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBRequest {

    static String SQL_TABLE1 = "CREATE TABLE producers (id INTEGER not NULL AUTO_INCREMENT, name VARCHAR(255), PRIMARY KEY ( id ))";
    static String SQL_TABLE2 = "CREATE TABLE models (id INTEGER not NULL AUTO_INCREMENT, name VARCHAR(255), " +
            "producer_id INT REFERENCES producers(id), PRIMARY KEY ( id ))";
    static String SQL_TABLE3 = "CREATE TABLE garage (id INTEGER not NULL IDENTITY(1,1), car_id INT REFERENCES models(id), PRIMARY KEY ( id ))";

    public static String SQL_CARS_REQUEST = "SELECT models.id as id, producers.name as producer, models.name as model " +
            "FROM models INNER JOIN producers ON models.producer_id = producers.id";
    public static String SQL_GARAGE_REQUEST = "SELECT garage.id as id, producers.name as producer, models.name as model " +
            "FROM garage, producers, models WHERE models.id = garage.car_id AND models.producer_id = producers.id";

    public static void Start(Connection h2){
        try{
            System.out.println("Создаем и наполняем таблицы");
            Statement stmt = h2.createStatement();
            stmt.executeUpdate("DROP ALL OBJECTS DELETE FILES");
            stmt.executeUpdate(SQL_TABLE1);
            stmt.executeUpdate(SQL_TABLE2);
            stmt.executeUpdate(SQL_TABLE3);
            stmt.executeUpdate("INSERT INTO producers VALUES (1, 'Toyota')");
            stmt.executeUpdate("INSERT INTO producers VALUES (2, 'Kia')");
            stmt.executeUpdate("INSERT INTO producers VALUES (3, 'Renault')");
            stmt.executeUpdate("INSERT INTO models VALUES (1, 'Camry', 1)");
            stmt.executeUpdate("INSERT INTO models VALUES (2, 'Soul', 2)");
            stmt.executeUpdate("INSERT INTO models VALUES (3, 'Duster', 3)");
//            stmt.executeUpdate("INSERT INTO models VALUES (4, 'Megan', 3)");
        }
        catch(SQLException e){
            System.err.println(e.getErrorCode());
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.err.println("Возможно, таблицы уже созданы");
        }
    }
}
