import duckdb.org.duckdb.*;
import java.sql.*;

//Querier is the link between the program and the database. It is a singleton, as it must maintain a connection to the database at all times.
//It should only be used by intermediate classes, as it needs raw sql strings.

public class Querier {
    private static Querier INSTANCE; 
    private Connection conn;
    
    //Private constructor for singleton
    private Querier() throws SQLException {
        conn = DriverManager.getConnection("jdbc:duckdb:testdb");
        System.out.println("Made database connection succesfully!");
    }

    public static Querier GetInstance() throws SQLException {
        if(INSTANCE == null)
            INSTANCE = new Querier();
        return INSTANCE;
    }
    
    //Takes some attributemap, and inserts the values for each attribute to the sql statement in attribute definition order
    private void InsertToPreparedStatement(PreparedStatement stmt, IAttributeMap map) throws SQLException {
        int nextIndex = 1;
        for(Attribute attribute : Attribute.values()) {
            if(map.Contains(attribute)) {
                nextIndex += map.AddToPreparedStatement(stmt, nextIndex, attribute);
            }
        }
    } 
    
    //Try to insert values of some attributes to a prepared statement and execute it
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
        //If there was an error, we still do this
        finally {
            conn.setAutoCommit(true);
        }
    }
    
    public void Execute(String sql) throws SQLException {
        Execute(sql, new AttributeValueMap());
    }
    
    //Same Execute, but we return a ResultSet
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
