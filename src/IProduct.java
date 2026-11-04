import java.awt.Image;

public interface IProduct {    
    public void SetAtrribute(Attribute attribute, String value);
    public void SetAtrribute(Attribute attribute, int value);
    public void SetAtrribute(Attribute attribute, double value);
    public void SetAtrribute(Attribute attribute, Image value);
    public void SetAtrribute(Attribute attribute, boolean value);
    
    public Object GetAttribute(Attribute attribute);
}
