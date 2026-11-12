import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IAttributeMap {
    
    public boolean Contains(Attribute attribute);
    public void Remove(Attribute attribute);
    public int Size();
    
    public int AddToPreparedStatement(PreparedStatement stmt, int i, Attribute attribute) throws SQLException;
}
