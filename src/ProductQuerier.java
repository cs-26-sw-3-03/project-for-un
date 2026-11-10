import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class ProductQuerier {
    public static void main(String[] args) throws SQLException {
        DeleteProduct(2);
    }



    public static void SetAttributes(int id, AttributeValueMap attributeMap) throws SQLException {
        
        String sql = "UPDATE Product SET ";
        for(Attribute attribute : Attribute.values()) {
            if(!attributeMap.Contains(attribute))
                continue;
            sql += AttributeDescriber.DbNameOf(attribute) + "=?, ";
        }
        sql += "WHERE int_id = " + id + ";";
        
        Querier.GetInstance().Execute(sql, attributeMap);
    }
    
    
    public static Product MakeProduct(AttributeValueMap attributeMap) throws SQLException {
        if(!attributeMap.HasAllRequiredAttributes())
            throw new IllegalArgumentException("Tried to make a product, but not all required attributes are defined");
        
        String sql = "INSERT INTO Product (int_id";
        for(Attribute attribute : Attribute.values()) {
            if(!attributeMap.Contains(attribute))
                continue;
            
            sql += ", " + AttributeDescriber.DbNameOf(attribute);
        }
        
        sql += ") VALUES (nextval('int_id')";
        for(Attribute attribute : Attribute.values()) {
            if(!attributeMap.Contains(attribute))
                continue;
            sql += ", ?";
        }
        
        sql += ") RETURNING int_id;";
        
        int id;
        try (ResultSet res = Querier.GetInstance().ExecuteQuery(sql, attributeMap);) {
            res.next();
            id = res.getInt("int_id");
        }
        
        return new Product(id, attributeMap);
    }
    
    
    public static Product GetProduct(int id, Set<Attribute> attributeSet) throws SQLException {
        String sql = "FROM Product SELECT ";
        
        for(Attribute attribute : Attribute.values()) {
            if(!attributeSet.contains(attribute))
                continue;
            sql += AttributeDescriber.DbNameOf(attribute) + ", ";
        }
        
        sql += "WHERE int_id = " + id + ";";
        
        Product product = new Product(id);
        try (ResultSet res = Querier.GetInstance().ExecuteQuery(sql);) {
            res.next();
            
            for(Attribute attribute : Attribute.values()) {
                if(!attributeSet.contains(attribute))
                    continue;
                
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
        HashSet<Attribute> attributeSet = new HashSet<Attribute>();
        for(Attribute attribute : Attribute.values()) {
            attributeSet.add(attribute);
        }
        return GetProduct(id, attributeSet);
    }
    
    public static void DeleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM Product WHERE int_id = " + id + ";";
        Querier.GetInstance().Execute(sql);
    }
}
