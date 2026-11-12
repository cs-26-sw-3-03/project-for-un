import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Filter {
    protected Object value;
    
    public abstract boolean TypeAllowed(Type type);
    
    public abstract String ToString();
    
    public abstract Filter Clone();
    
    public abstract int AcceptInt(IFilterVisitorIntThrowsSQL visitor) throws SQLException;
    
    public int AddToPreparedStatement(PreparedStatement stmt, int i, Type type) throws SQLException {
        switch(type) {
            case Type.INT:
                stmt.setInt(i, (Integer) value);
                break;
            case Type.STRING:
                stmt.setString(i, (String) value);
                break;
            /*
            case Type.DOUBLE:
                stmt.setBigDecimal(i, );
                break;
            */
            /*
            case Type.IMAGE:
                stmt.setImage(i, );
                break;
            */
            case Type.BOOL:
                stmt.setBoolean(i, (Boolean) value);
                break;
            default:
                throw new IllegalArgumentException("Unsupported attribute type used");
        }
        
        return 1;
    }
}
