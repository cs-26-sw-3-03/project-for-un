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
    
    private void InsertToPreparedStatement(PreparedStatement stmt, AttributeValue[] values) throws SQLException {
        for(int i = 0; i < values.length; i++) {
            switch(values[i].GetType()) {
                case Type.STRING:
                    stmt.setString(i + 1, values[i].GetString());
                    break;
                case Type.INT:
                    stmt.setInt(i + 1, values[i].GetInt());
                    break;
                /*
                case Type.DOUBLE:
                    stmt.setBigDecimal(i + 1, values[i].GetDouble());
                    break;
                */
                case Type.BOOL:
                    stmt.setBoolean(i + 1, values[i].GetBool());
                    break;
                    /*
                case Type.IMAGE:
                    stmt.setString(i + 1, values[i].GetImage());
                    break;
                    */
            }
        }
    } 
    
    public void Execute(String sql, AttributeValue[] values) throws SQLException {
        conn.setAutoCommit(false);
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            InsertToPreparedStatement(stmt, values);
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
        Execute(sql, new AttributeValue[0]);
    }
    
    public void Execute(String sql, AttributeValue attributeValue) throws SQLException {
        AttributeValue[] arr = new AttributeValue[1];
        arr[0] = attributeValue;
        Execute(sql, arr);
    }
    
    
    public ResultSet ExecuteQuery(String sql, AttributeValue[] values) throws SQLException {
        conn.setAutoCommit(false);
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            InsertToPreparedStatement(stmt, values);
            ResultSet res = stmt.executeQuery();
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
        return ExecuteQuery(sql, new AttributeValue[0]);
    }
    
    public ResultSet ExecuteQuery(String sql, AttributeValue attributeValue) throws SQLException {
        AttributeValue[] arr = new AttributeValue[1];
        arr[0] = attributeValue;
        return ExecuteQuery(sql, arr);
    }
}
