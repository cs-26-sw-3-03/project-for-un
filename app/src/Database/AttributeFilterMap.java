import java.util.HashMap;
import java.util.Set;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//This class maps an attribute to a filter, but only if the filter is allowed on the attribute, i.e. no >= on strings

public class AttributeIFilterMap implements IAttributeMap{
    private HashMap<Attribute, IFilter> map;

    public AttributeIFilterMap() {
        map = new HashMap<Attribute, IFilter>();
    }
    
    public void Put(Attribute attribute, IFilter filter) {
        if(filter == null) {
            Remove(attribute);
            return;
        }
        
        if(!filter.TypeAllowed(AttributeDescriber.TypeOf(attribute)))
            throw new IllegalArgumentException("Filter type not allowed for given attribute");
    
        map.put(attribute, filter);
    }
    
    public boolean Contains(Attribute attribute) {
        return map.containsKey(attribute);
    }
    
    public IFilter Get(Attribute attribute) {
        return map.get(attribute);
    }
    
    public void Remove(Attribute attribute) {
        map.remove(attribute);
    }

    public int Size() {
        return map.size();
    }
    
    public Set<Attribute> GetAttributeSet() {
        return map.keySet();
    }
    
    public AttributeIFilterMap Clone() {
        AttributeIFilterMap out = new AttributeIFilterMap();
        for(Attribute attribute : Attribute.values()) {
            if(Contains(attribute))
                out.Put(attribute, Get(attribute).Clone());
        }
        return out;
    }
    
    
    public int AddToPreparedStatement(PreparedStatement stmt, int i, Attribute attribute) throws SQLException {
        //Runs the AddToPreparedStatementVisitor on the filter. It returns the amount of values added to the statement
        IIFilterVisitorInt<SQLException> visitor = new AddToPreparedStatementVisitor(stmt, i, AttributeDescriber.TypeOf(attribute));
        return Get(attribute).AcceptInt(visitor);
    }
    
}
