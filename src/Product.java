import java.awt.Image;

public class Product implements IProduct {
    private int id;
    private AttributeValueMap attributeValues;
    
    public Product(int id) {
        this.id = id;
        for(Attribute attribute : Attribute.values()) {
            attributeValues.Put(new AttributeValue(attribute));
        }
    }

    public void SetAttribute(Attribute attribute, String value) {
        attributeValues.Put(new AttributeValue(attribute, value));
    }
    public void SetAttribute(Attribute attribute, int value) {
        attributeValues.Put(new AttributeValue(attribute, value));
    }
    public void SetAttribute(Attribute attribute, double value) {
        attributeValues.Put(new AttributeValue(attribute, value));
    }
    public void SetAttribute(Attribute attribute, Image value) {
        attributeValues.Put(new AttributeValue(attribute, value));
    }
    public void SetAttribute(Attribute attribute, boolean value) {
        attributeValues.Put(new AttributeValue(attribute, value));
    }
    
    public int GetId() {return id;}
    
    public Object GetAttribute(Attribute attribute) {
        return attributeValues.Get(attribute).GetAttribute();
    }
}
