import java.awt.Image;

public interface IProduct {    
    public void SetAttribute(Attribute attribute, String value);
    public void SetAttribute(Attribute attribute, int value);
    public void SetAttribute(Attribute attribute, double value);
    public void SetAttribute(Attribute attribute, Image value);
    public void SetAttribute(Attribute attribute, boolean value);
    
    public Object GetAttribute(Attribute attribute);
}
