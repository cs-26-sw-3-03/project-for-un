import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public interface IAttributeMap {
    
    public boolean Contains(Attribute attribute);
    public void Remove(Attribute attribute);
    public int Size();
    public Set<Attribute> GetAttributeSet(); 
    
    public int AddToPreparedStatement(PreparedStatement stmt, int i, Attribute attribute) throws SQLException;
}
