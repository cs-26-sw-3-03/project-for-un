import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Filter {
    protected Object value;
    
    public abstract boolean TypeAllowed(Type type);
    
    public abstract String ToString();
    
    public abstract Filter Clone();
    
    public abstract <T extends Throwable> int AcceptInt(IFilterVisitorInt<T> visitor) throws T;
}
