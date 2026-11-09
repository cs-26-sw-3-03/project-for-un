import java.awt.Image;

public class AttributeValue {
    private Attribute attribute;
    private Object value;
    
    public AttributeValue(Attribute attribute) {
        this.attribute = attribute;
        this.value = null;
    }
    
    public AttributeValue(Attribute attribute, Object value) throws IllegalArgumentException {
        this.attribute = attribute;
        SetValue(value);
    }
    
    public void SetValue(Object value) throws IllegalArgumentException {
        if(value == null) {
            this.value = value;
            return;
        }
    
        try {
            switch(GetType()) {
                case Type.INT:
                    this.value = (Integer) value;
                    break;
                case Type.STRING:
                    this.value = (String) value;
                    break;
                case Type.DOUBLE:
                    this.value = (Double) value;
                    break;
                case Type.IMAGE:
                    this.value = (Image) value;
                    break;
                case Type.BOOL:
                    this.value = (Boolean) value;
                    break;
            }
        }
        catch (ClassCastException e) {
            throw(new IllegalArgumentException("Attribute type not matching value type"));
        }
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
