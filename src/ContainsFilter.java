import java.sql.SQLException;

public class ContainsFilter extends Filter {
    private String value;
    
    public ContainsFilter(String value) {
        this.value = value;
    }
    
    public void SetValue(String value) {this.value = value;}
    public String GetValue() {return this.value;}
    
    public boolean TypeAllowed(Type type) {
        switch(type) {
            case Type.STRING:
                return true;
            case Type.INT: case Type.DOUBLE: case Type.BOOL: case Type.IMAGE:
                return false;
            case null:
                throw new NullPointerException();
        }
    }
    
    public String ToString() {return "LIKE ?";}
    
    @Override
    public ContainsFilter Clone() {
        return new ContainsFilter(value);
    }
    
    public int AcceptInt(IFilterVisitorIntThrowsSQL visitor) throws SQLException {
        return visitor.CaseContains(this);
    }
}
