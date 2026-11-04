import java.awt.Image;

public interface IProduct {
    public enum Attribute{}
    
    
    public void setAtrribute(Attribute attribute, String value);
    public void setAtrribute(Attribute attribute, int value);
    public void setAtrribute(Attribute attribute, double value);
    public void setAtrribute(Attribute attribute, Image value);
    public void setAtrribute(Attribute attribute, boolean value);
    
    public Object getAttribute(Attribute attribute);
}
