import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class ProductQuerier {
    public static void main(String[] args) throws SQLException {
        DeleteProduct(2);
    }



    public static void SetAttributes(int id, AttributeValueMap attributes) throws SQLException {
        AttributeValue[] arr = attributes.ToArr();
        
        String sql = "UPDATE Product SET ";
        for(AttributeValue attributeValue : arr) {
            sql += AttributeDescriber.DbNameOf(attributeValue.GetAttribute()) + "=?, ";
        }
        sql += "WHERE int_id = " + id + ";";
        
        Querier.GetInstance().Execute(sql, arr);
    }
    
    
    public static Product MakeProduct(AttributeValueMap attributes) throws SQLException {
        if(!attributes.HasAllRequiredAttributes())
            throw new IllegalArgumentException("Tried to make a product, but not all required attributes are defined");
            
        AttributeValue[] arr = attributes.ToArr();
        
        String sql = "INSERT INTO Product (int_id";
        for(AttributeValue attributeValue : arr) {
            sql += ", " + AttributeDescriber.DbNameOf(attributeValue.GetAttribute());
        }
        
        sql += ") VALUES (nextval('int_id')";
        for(AttributeValue attributeValue : arr) {
            sql += ", ?";
        }
        
        sql += ") RETURNING int_id;";
        
        int id;
        try (ResultSet res = Querier.GetInstance().ExecuteQuery(sql, arr);) {
            res.next();
            id = res.getInt("int_id");
        }
        
        return new Product(id, attributes);
    }
    
    
    public static Product GetProduct(int id, Set<Attribute> attributes) throws SQLException {
        Attribute[] arr = attributes.toArray(new Attribute[0]);
        String sql = "FROM Product SELECT ";
        
        for(Attribute attribute : arr) {
            sql += AttributeDescriber.DbNameOf(attribute) + ", ";
        }
        
        sql += "WHERE int_id = " + id + ";";
        
        Product product = new Product(id);
        try (ResultSet res = Querier.GetInstance().ExecuteQuery(sql, new AttributeValue[0]);) {
            res.next();
            
            for(Attribute attribute : arr) {
                String attributeName = AttributeDescriber.DbNameOf(attribute);
                Object value;
                
                switch(AttributeDescriber.TypeOf(attribute)) {
                    case Type.INT:
                        value = res.getInt(attributeName);
                        break;
                    case Type.STRING:
                        value = res.getString(attributeName);
                        break;
                    case Type.DOUBLE:
                        value = res.getInt(attributeName);
                        break;
                    case Type.IMAGE:
                        value = res.getInt(attributeName);
                        break;
                    case Type.BOOL:
                        value = res.getBoolean(attributeName);
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported attribute type");
                }
                
                try {
                    product.SetAttribute(attribute, value);
                }
                catch(IllegalArgumentException e) {
                    throw(new SQLException("Types of values in database does not match expected types"));
                }
            }
            
        }
        return product;
    }
    
    public static Product GetProduct(int id) throws SQLException {
        HashSet<Attribute> attributes = new HashSet<Attribute>();
        for(Attribute attribute : Attribute.values()) {
            attributes.add(attribute);
        }
        return GetProduct(id, attributes);
    }
    
    public static void DeleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM Product WHERE int_id = " + id + ";";
        Querier.GetInstance().Execute(sql);
    }
}
