import java.util.HashMap;
import java.util.Set;
import java.awt.Image;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.math.BigDecimal;

//Is a collection of mappings between Attributes and their values. Treat it like a hashmap with put, remove, size and contains.
//The getter functions and setter function will throw an exception if the expected type of the attribute does not match the inputted/returned object type.

public class AttributeValueMap implements IAttributeMap{
    private HashMap<Attribute, Object> map;
    
    public AttributeValueMap() {
        map = new HashMap<Attribute, Object>();
    }
    
    public void Put(Attribute attribute, Object value) {
        if(value == null) {
            map.put(attribute, value);
            return;
        }
    
        try {
            switch(AttributeDescriber.TypeOf(attribute)) {
                case Type.INT:
                    map.put(attribute, (Integer) value);
                    return;
                case Type.STRING:
                    map.put(attribute, (String) value);
                    return;
                case Type.DOUBLE:
                    map.put(attribute, (Double) value);
                    return;
                case Type.DECIMAL:
                    map.put(attribute, (BigDecimal) value);
                    return;
                case Type.IMAGE:
                    map.put(attribute, (Image) value);
                    return;
                case Type.BOOL:
                    map.put(attribute, (Boolean) value);
                    return;
            }
        }
        catch (ClassCastException e) {
            throw(new IllegalArgumentException("Attribute type not matching value type"));
        }
    }
    
    
    public boolean Contains(Attribute attribute) {
        return map.containsKey(attribute);
    }
    
    
    public Object GetObject(Attribute attribute) {
        return map.get(attribute);
    }
    
    public String GetString(Attribute attribute) {
        if(AttributeDescriber.TypeOf(attribute) == Type.STRING)
            return (String) map.get(attribute);
        throw new IllegalArgumentException("Asked for string value of non-string attribute");
    }
    
    public Integer GetInt(Attribute attribute) {
        if(AttributeDescriber.TypeOf(attribute) == Type.INT)
            return (Integer) map.get(attribute);
        throw new IllegalArgumentException("Asked for integer value of non-integer attribute");
    }
    
    public Double GetDouble(Attribute attribute) {
        if(AttributeDescriber.TypeOf(attribute) == Type.DOUBLE)
            return (Double) map.get(attribute);
        throw new IllegalArgumentException("Asked for double value of non-double attribute");
    }
    
    public BigDecimal GetDecimal(Attribute attribute) {
        if(AttributeDescriber.TypeOf(attribute) == Type.DECIMAL)
            return (BigDecimal) map.get(attribute);
        throw new IllegalArgumentException("Asked for decimal value of non-decimal attribute");
    }
    
    public Image GetImage(Attribute attribute) {
        if(AttributeDescriber.TypeOf(attribute) == Type.IMAGE)
            return (Image) map.get(attribute);
        throw new IllegalArgumentException("Asked for image value of non-image attribute");
    }
    
    public Boolean GetBool(Attribute attribute) {
        if(AttributeDescriber.TypeOf(attribute) == Type.BOOL)
            return (Boolean) map.get(attribute);
        throw new IllegalArgumentException("Asked for boolean value of non-boolean attribute");
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
    
    public boolean HasAllRequiredAttributes() {
        for(Attribute attribute : Attribute.values()) {
            if(AttributeDescriber.Required(attribute) && !Contains(attribute))
                return false;
        }
        return true;
    }
    
    public int AddToPreparedStatement(PreparedStatement stmt, int i, Attribute attribute) throws SQLException {
        switch(AttributeDescriber.TypeOf(attribute)) {
            case Type.INT:
                stmt.setInt(i, GetInt(attribute));
                break;
            case Type.STRING:
                stmt.setString(i, GetString(attribute));
                break;
            case Type.DOUBLE:
                stmt.setDouble(i, GetDouble(attribute));
                break;
            case Type.DECIMAL:
                stmt.setBigDecimal(i, GetDecimal(attribute));
                break;
            case Type.IMAGE:
                //stmt.setImage(i, );
                break;
            case Type.BOOL:
                stmt.setBoolean(i, GetBool(attribute));
                break;
            case null:
                throw new NullPointerException();
        }
        return 1;
    }
}
