import duckdb.org.duckdb.*;
import java.sql.*;


public class Querier {
    private static Querier INSTANCE; 
    private Connection conn;

    private Querier() throws SQLException {
        conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:duckdb:");
            System.out.println("Made database connection succesfully!");
        }
        catch (Exception e) {
            System.out.println(e);
            throw(e);
        }
    }

    public Querier GetInstance() throws SQLException {
        if(INSTANCE == null)
            INSTANCE = new Querier();
        return INSTANCE;
    }
    
    public void ExecuteUpdate(String sql, AttributeValue[] values) throws SQLException {
        conn.setAutoCommit(false);
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {
            for(int i = 0; i < values.length; i++) {
                switch(values[i].GetType()) {
                    case Type.STRING:
                        stmt.setString(i, values[i].GetString());
                        break;
                    case Type.INT:
                        stmt.setInt(i, values[i].GetInt());
                        break;
                    /*
                    case Type.DOUBLE:
                        stmt.setBigDecimal(i, values[i].GetDouble());
                        break;
                    */
                    case Type.BOOL:
                        stmt.setBoolean(i, values[i].GetBool());
                        break;
                        /*
                    case Type.IMAGE:
                        stmt.setString(i, values[i].GetImage());
                        break;
                        */
                }
            }
            stmt.executeUpdate();
            conn.commit();
        }
        catch(SQLException e) {
            conn.rollback();
            throw(e);
        }
        finally {
            conn.setAutoCommit(true);
        }
    }
}
