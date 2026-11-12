import duckdb.org.duckdb.*;
import java.sql.*;


public class Querier {
    private static Querier INSTANCE; 
    private Connection conn;

    private Querier() throws SQLException {
        conn = DriverManager.getConnection("jdbc:duckdb:testdb");
        System.out.println("Made database connection succesfully!");
    }

    public static Querier GetInstance() throws SQLException {
        if(INSTANCE == null)
            INSTANCE = new Querier();
        return INSTANCE;
    }
    
    private void InsertToPreparedStatement(PreparedStatement stmt, IAttributeMap map) throws SQLException {
        int nextIndex = 1;
        for(Attribute attribute : Attribute.values()) {
            if(map.Contains(attribute)) {
                nextIndex += map.AddToPreparedStatement(stmt, nextIndex, attribute);
            }
        }
    } 
    
    public void Execute(String sql, IAttributeMap attributes) throws SQLException {
        conn.setAutoCommit(false);
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            InsertToPreparedStatement(stmt, attributes);
            stmt.execute();
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
    
    public void Execute(String sql) throws SQLException {
        Execute(sql, new AttributeValueMap());
    }
    
    public ResultSet ExecuteQuery(String sql, IAttributeMap attributes) throws SQLException {
        conn.setAutoCommit(false);
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            InsertToPreparedStatement(stmt, attributes);
            return stmt.executeQuery();
        }
        catch(SQLException e) {
            conn.rollback();
            throw(e);
        }
        finally {
            conn.setAutoCommit(true);
        }
    }
    
    public ResultSet ExecuteQuery(String sql) throws SQLException {
        return ExecuteQuery(sql, new AttributeValueMap());
    }
}
