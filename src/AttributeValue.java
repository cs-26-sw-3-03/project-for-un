import java.awt.Image;

public class AttributeValue {
    private Attribute attribute;
    private Object value;
    
    public AttributeValue(Attribute attribute) {
        this.attribute = attribute;
        this.value = null;
    }
    
    public AttributeValue(Attribute attribute, String value) {
        this.attribute = attribute;
        if(GetType() != Type.STRING)
            throw new IllegalArgumentException("Attribute type not matching value type");
        this.value = value;
    }
    
    public AttributeValue(Attribute attribute, Integer value) {
        this.attribute = attribute;
        if(GetType() != Type.INT)
            throw new IllegalArgumentException("Attribute type not matching value type");
        this.value = value;
    }
    
    public AttributeValue(Attribute attribute, Double value) {
        this.attribute = attribute;
        if(GetType() != Type.DOUBLE)
            throw new IllegalArgumentException("Attribute type not matching value type");
        this.value = value;
    }
    
    public AttributeValue(Attribute attribute, Boolean value) {
        this.attribute = attribute;
        if(GetType() != Type.BOOL)
            throw new IllegalArgumentException("Attribute type not matching value type");
        this.value = value;
    }
    
    public AttributeValue(Attribute attribute, Image value) {
        this.attribute = attribute;
        if(GetType() != Type.IMAGE)
            throw new IllegalArgumentException("Attribute type not matching value type");
        this.value = value;
    }
    
    public void SetValue(String value) {
        if(GetType() != Type.STRING)
            throw new IllegalArgumentException("Attribute type not matching value type");
        this.value = value;
    }
    
    public void SetValue(Integer value) {
        if(GetType() != Type.INT)
            throw new IllegalArgumentException("Attribute type not matching value type");
        this.value = value;
    }
    
    public void SetValue(Double value) {
        if(GetType() != Type.DOUBLE)
            throw new IllegalArgumentException("Attribute type not matching value type");
        this.value = value;
    }
    
    public void SetValue(Boolean value) {
        if(GetType() != Type.BOOL)
            throw new IllegalArgumentException("Attribute type not matching value type");
        this.value = value;
    }
    
    public void SetValue(Image value) {
        if(GetType() != Type.IMAGE)
            throw new IllegalArgumentException("Attribute type not matching value type");
        this.value = value;
    }
    
    public boolean HasValue() {
        return value != null;
    }
    
    public String GetString() {
        if(GetType() != Type.STRING)
            throw new IllegalStateException("Requested value type does not match attribute type");
        return (String) value;
    }
    
    public Integer GetInt() {
        if(GetType() != Type.INT)
            throw new IllegalStateException("Requested value type does not match attribute type");
        return (Integer) value;
    }
    
    public Double GetDouble() {
        if(GetType() != Type.DOUBLE)
            throw new IllegalStateException("Requested value type does not match attribute type");
        return (Double) value;
    }
    
    public Boolean GetBool() {
        if(GetType() != Type.BOOL)
            throw new IllegalStateException("Requested value type does not match attribute type");
        return (Boolean) value;
    }
    
    public Image GetImage() {
        if(GetType() != Type.IMAGE)
            throw new IllegalStateException("Requested value type does not match attribute type");
        return (Image) value;
    }
    
    public Object GetValue() {
        return value;
    }
    
    public Attribute GetAttribute() {
        return attribute;
    }
    
    public Type GetType() {
        return AttributeDescriber.TypeOf(attribute);
    }
}
