import org.duckdb.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;



public class Test {
    private static void makeStatement(Connection conn, String statement) {
        try (Statement stmt = conn.createStatement()) {
            int a = stmt.executeUpdate(statement);
        }
        catch (Exception e) {
            System.out.println("ERROR 2!!!");
            System.out.println(e);
        }

    }

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:duckdb:");
            System.out.println("Sucess");
        }
        catch (Exception e) {
            System.out.println("ERROR!!!");
            System.out.println(e);
        }
        makeStatement(conn, "CREATE TABLE test (attribute1 INTEGER PRIMARY KEY)");
        makeStatement(conn, "INSERT INTO test (attribute1) VALUES (76)");
        makeStatement(conn, "INSERT INTO test (attribute1) VALUES (77)");
        
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT attribute1 FROM test")) {
            rs.next();
            System.out.println(rs.getInt(1));
            rs.next();
            System.out.println(rs.getInt(1));
        }
        catch (Exception e) {
            System.out.println("ERROR 2!!!");
            System.out.println(e);
        }

    }    
}
