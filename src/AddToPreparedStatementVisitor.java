import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;


//This class is a visitor pattern for filters, that adds their respective values to a prepared statement at an index, and returns the amount of values added

public class AddToPreparedStatementVisitor implements IFilterVisitorInt<SQLException> {
    private PreparedStatement stmt;
    private int i;
    private Type type;

    public AddToPreparedStatementVisitor(PreparedStatement stmt, int i, Type type) {
        this.stmt = stmt;
        this.i = i;
        this.type = type;
    }
    
    
    private int AddOneValue(Object value) throws SQLException {
        switch(type) {
            case Type.INT:
                stmt.setInt(i, (Integer) value);
                break;
            case Type.STRING:
                stmt.setString(i, (String) value);
                break;
            case Type.DOUBLE:
                stmt.setDouble(i, (Double) value);
                break;
            case Type.DECIMAL:
                stmt.setBigDecimal(i, (BigDecimal) value);
                break;
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
    
    public int CaseExists(ExistsFilter filter) throws SQLException {return 0;}
    public int CaseRange(RangeFilter filter) throws SQLException {
        switch(type) {
            case Type.INT:
                stmt.setInt(i, (Integer) filter.GetMin());
                stmt.setInt(i+1, (Integer) filter.GetMax());
                break;
            case Type.STRING:
                stmt.setString(i, (String) filter.GetMin());
                stmt.setString(i+1, (String) filter.GetMax());
                break;
            case Type.DOUBLE:
                stmt.setDouble(i, (Double) filter.GetMin());
                stmt.setDouble(i+1, (Double) filter.GetMax());
                break;
            case Type.DECIMAL:
                stmt.setBigDecimal(i, (BigDecimal) filter.GetMin());
                stmt.setBigDecimal(i+1, (BigDecimal) filter.GetMax());
                break;
            /*
            case Type.IMAGE:
                stmt.setImage(i, );
                stmt.setImage(i+1, );
                break;
            */
            case Type.BOOL:
                stmt.setBoolean(i, (Boolean) filter.GetMin());
                stmt.setBoolean(i+1, (Boolean) filter.GetMax());
                break;
            default:
                throw new IllegalArgumentException("Unsupported attribute type used");
        }
        
        return 2;
    }
    public int CaseEquals(EqualsFilter filter) throws SQLException {return AddOneValue(filter.GetValue());}
    public int CaseGeq(GeqFilter filter) throws SQLException {return AddOneValue(filter.GetValue());}
    public int CaseLeq(LeqFilter filter) throws SQLException {return AddOneValue(filter.GetValue());}
    public int CaseContains(ContainsFilter filter) throws SQLException {return AddOneValue(filter.GetValue());}
}
