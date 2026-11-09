import java.awt.Image;

public class Product implements IProduct {
    private int id;
    private AttributeValueMap attributeValues;
    
    public Product(int id, AttributeValueMap map) {
        this.id = id;
        this.attributeValues = new AttributeValueMap();
        
        //Put all possible attributes in attributeValues. If they are not in map, their value becomes null
        for(Attribute attribute : Attribute.values()) {
            if(map.Contains(attribute))
                attributeValues.Put(map.Get(attribute));
            else
                attributeValues.Put(new AttributeValue(attribute));
        }
    }
    
    public Product(int id) {
        this.id = id;
        this.attributeValues = new AttributeValueMap();
        
        //Put all possible attributes in attributeValues, and set their values to null
        for(Attribute attribute : Attribute.values()) {
            attributeValues.Put(new AttributeValue(attribute));
        }
    }

    public void SetAttribute(Attribute attribute, Object value) throws IllegalArgumentException{
        attributeValues.Put(new AttributeValue(attribute, value));
    }
    
    public int GetId() {return id;}
    
    public Object GetAttribute(Attribute attribute) {
        return attributeValues.Get(attribute).GetValue();
    }
}
