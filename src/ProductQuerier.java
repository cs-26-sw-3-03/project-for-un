import java.sql.SQLException;
import java.sql.ResultSet;

public class ProductQuerier {

    public void main(String[] Args) throws SQLException {
        AttributeValueMap map = new AttributeValueMap();
        map.Put(new AttributeValue(Attribute.ID, "Test id"));
        map.Put(new AttributeValue(Attribute.NAME, "Test name"));
        map.Put(new AttributeValue(Attribute.FABRIC_ID, "Test fabric id"));
        map.Put(new AttributeValue(Attribute.SIZE, "Test size"));
        map.Put(new AttributeValue(Attribute.COUNT, 42));
        map.Put(new AttributeValue(Attribute.HIDDEN, false));
        
        MakeProduct(map);
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
        
        try (ResultSet res = Querier.GetInstance().ExecuteQuery(sql, arr);) {
            res.next();
            int id = res.getInt("int_id");
        }
        
        return new Product(id, attributes);
    }
}
