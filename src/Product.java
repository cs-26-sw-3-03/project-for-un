import java.awt.Image;
import java.math.BigDecimal;

//Product has an id and some attributes. The id cannot be changed.
//All attributes are not neccesarily present, as this only represents a view of what is in the database.


public class Product implements IProduct {
    private int id;
    private AttributeValueMap attributeValues;
    
    public Product(int id, AttributeValueMap map) {
        this.id = id;
        this.attributeValues = new AttributeValueMap();
        
        //Put all possible attributes in attributeValues. If they are not in map, their value becomes null
        for(Attribute attribute : Attribute.values()) {
            attributeValues.Put(attribute, map.GetObject(attribute));
        }
    }
    
    //Make the product with all null attributes
    public Product(int id) {
        this(id, new AttributeValueMap());
    }

    public void SetAttribute(Attribute attribute, Object value) throws IllegalArgumentException{
        attributeValues.Put(attribute, value);
    }
    
    public int GetId() {return id;}
    
    public Object     GetAttribute(Attribute attribute) {return attributeValues.GetObject(attribute);}
    public Integer    GetInt(Attribute attribute)       {return attributeValues.GetInt(attribute);}
    public String     GetString(Attribute attribute)    {return attributeValues.GetString(attribute);}
    public Double     GetDouble(Attribute attribute)    {return attributeValues.GetDouble(attribute);}
    public BigDecimal GetDecimal(Attribute attribute)   {return attributeValues.GetDecimal(attribute);}
    public Image      GetImage(Attribute attribute)     {return attributeValues.GetImage(attribute);}
    public Boolean    GetBool(Attribute attribute)      {return attributeValues.GetBool(attribute);}
}
