import java.util.HashMap;  
import java.awt.Image;

public class AttributeValueMap {
    private HashMap<Attribute, Object> map;
    public AttributeValueMap() {
        map = new HashMap<Attribute, Object>();
    }
    
    public void Put(AttributeValue attributeValue) {
        map.put(attributeValue.GetAttribute(), attributeValue.GetValue());
    }
    
    public boolean Contains(Attribute attribute) {
        return map.containsKey(attribute);
    }
    
    public AttributeValue Get(Attribute attribute) {
        switch(AttributeDescriber.TypeOf(attribute)) {
            case Type.STRING:
                return new AttributeValue(attribute, (String) map.get(attribute));
            case Type.INT:
                return new AttributeValue(attribute, (Integer) map.get(attribute));
            case Type.DOUBLE:
                return new AttributeValue(attribute, (Double) map.get(attribute));
            case Type.BOOL:
                return new AttributeValue(attribute, (Boolean) map.get(attribute));
            case Type.IMAGE:
                return new AttributeValue(attribute, (Image) map.get(attribute));
            default:
                throw new IllegalArgumentException("Unsupported attribute enum was used");
        }
    }
    
    public void Remove(Attribute attribute) {
        map.remove(attribute);
    }
    
    public AttributeValue[] ToArr() {
        AttributeValue[] arr = new AttributeValue[map.size()];
        for(int i = 0; i < map.size(); i++) {
            arr[i] = Get(map.keySet().toArray(new Attribute[0])[i]);
        }
        return arr;
    }
    
    public boolean HasAllRequiredAttributes() {
        for(Attribute attribute : Attribute.values()) {
            if(AttributeDescriber.Required(attribute) && !Contains(attribute))
                return false;
        }
        return true;
    }
}
