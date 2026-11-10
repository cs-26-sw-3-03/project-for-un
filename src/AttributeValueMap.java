import java.util.HashMap;  
import java.awt.Image;

public class AttributeValueMap {
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
    
    public boolean HasAllRequiredAttributes() {
        for(Attribute attribute : Attribute.values()) {
            if(AttributeDescriber.Required(attribute) && !Contains(attribute))
                return false;
        }
        return true;
    }
}
