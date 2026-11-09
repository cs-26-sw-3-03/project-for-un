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
        try {
            return new AttributeValue(attribute, map.get(attribute));
        }
        catch(IllegalArgumentException e) {
            System.out.println("Somehow attributeMap had illegal attributes");
            throw(e);
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
