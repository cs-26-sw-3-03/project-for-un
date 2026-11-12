import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RangeFilter extends Filter {
    private Object min;
    private Object max;
    
    public RangeFilter(Object min, Object max) {
        this.min = min;
        this.max = max;
    }
    
    public void SetMin(Object min) {this.min = min;}
    public void SetMax(Object max) {this.max = max;}
    public void SetRange(Object min, Object max) {this.min = min; this.max = max;}
    public Object GetMin() {return this.min;}
    public Object GetMax() {return this.max;}
    
    public boolean TypeAllowed(Type type) {
        switch(type) {
            case Type.INT: case Type.DOUBLE:
                return true;
            case Type.STRING: case Type.BOOL: case Type.IMAGE:
                return false;
            case null:
                throw new NullPointerException();
        }
    }
    
    public String ToString() {return "BETWEEN ? AND ?";}
    
    @Override
    public RangeFilter Clone() {
        return new RangeFilter(value);
    }
    
    @Override
    public int AddToPreparedStatement(PreparedStatement stmt, int i, Type type) throws SQLException {
        switch(type) {
            case Type.INT:
                stmt.setInt(i, (Integer) min);
                stmt.setInt(i+1, (Integer) max);
                break;
            case Type.STRING:
                stmt.setString(i, (String) min);
                stmt.setString(i+1, (String) max);
                break;
            /*
            case Type.DOUBLE:
                stmt.setBigDecimal(i, );
                stmt.setBigDecimal(i+1, );
                break;
            */
            /*
            case Type.IMAGE:
                stmt.setImage(i, );
                stmt.setImage(i+1, );
                break;
            */
            case Type.BOOL:
                stmt.setBoolean(i, (Boolean) min);
                stmt.setBoolean(i+1, (Boolean) max);
                break;
            default:
                throw new IllegalArgumentException("Unsupported attribute type used");
        }
        
        return 2;
    }
}
