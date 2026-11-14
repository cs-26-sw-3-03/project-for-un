import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Set;
import java.util.HashSet;

//This class is used to query for products (wow).
//Function desctiptions:
//  SetAttributes takes an int id and an AttributeValueMap. It sets the attributes in the attributeMap for the product with the selected productId.
//  MakeProduct takes an AttributeValueMap as input, and if the map has all the required attributes, makes a product in the database, and returns a Product Object with the id and attributes of the created product.
//  GetProduct takes an id and an optional set of attributes. It returns a product object with that id.
//    If the set is specified, it only contains those attributes. Otherwise it has all attributes. Returns null if product does not exist
//  GetProducts takes a QueryConfig as input, and returns an array containing Product objects corresponding to matching products in the database. The array may be empty.
//  DeleteProduct takes a product id as input, and deletes the product with that id from the database if it exists. Otherwise nothing happens.

public class ProductQuerier {
    private final static String ID_NAME = "int_id";

    //Used as input in the AddAttributeNamesToSQL functions to make a string based on the inserted attribute
    private interface AttrToStrFunction {
        String run(Attribute attribute);
    }

    private final static AttrToStrFunction empty = (a) -> "";
    private static AttrToStrFunction AlwaysReturn(String str) {
        return ((a) -> {return str;});
    }
    
    
    //Adds the attribute database names to the string sequentially. If before or after are provided, they produce a string based on the attribute to be placed before or after the attribute name.
    
    private static String AddAttributeNamesToSQL(String sql, Set<Attribute> attributeSet)                           {return AddAttributeNamesToSQL(sql, empty, attributeSet, empty);}
    private static String AddAttributeNamesToSQL(String sql, AttrToStrFunction before, Set<Attribute> attributeSet) {return AddAttributeNamesToSQL(sql, before, attributeSet, empty);}
    private static String AddAttributeNamesToSQL(String sql, Set<Attribute> attributeSet, AttrToStrFunction after)  {return AddAttributeNamesToSQL(sql, empty, attributeSet, after);}
    
    private static String AddAttributeNamesToSQL(String sql, AttrToStrFunction before, Set<Attribute> attributeSet, AttrToStrFunction after) {
        for(Attribute attribute : Attribute.values()) {
            if(attributeSet.contains(attribute))
                sql += before.run(attribute) + AttributeDescriber.DbNameOf(attribute) + after.run(attribute);
        }
        return sql;
    }
    

    
    
    public static Product ProductFromResultSet(ResultSet res, Set<Attribute> attributeSet) throws SQLException {
        return ProductFromResultSet(res, res.getInt(ID_NAME), attributeSet);
    }
    
    public static Product ProductFromResultSet(ResultSet res, int id, Set<Attribute> attributeSet) throws SQLException {
        Product product = new Product(id);
        
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
                    value = res.getDouble(attributeName);
                    break;
                case Type.DECIMAL:
                    value = res.getBigDecimal(attributeName);
                    break;
                case Type.IMAGE:
                    value = res.getInt(attributeName);
                    break;
                case Type.BOOL:
                    value = res.getBoolean(attributeName);
                    break;
                case null:
                    throw new NullPointerException();
            }
            
            try {
                product.SetAttribute(attribute, value);
            }
            catch(IllegalArgumentException e) {
                throw(new SQLException("Types of values in database does not match expected types"));
            }
        }
        return product;
    }
    
    
    public static void SetAttributes(int id, AttributeValueMap attributeMap) throws SQLException {        
        if(attributeMap.Size() > 0) {
            String sql = "UPDATE Product SET ";
            sql = AddAttributeNamesToSQL(sql, attributeMap.GetAttributeSet(), AlwaysReturn("=?, "));
            
            //Remove the last comma
            sql = sql.substring(0, sql.length() - ", ".length());
            sql += " WHERE " + ID_NAME + " = " + id + ";";
        
            Querier.GetInstance().Execute(sql, attributeMap);
        }
    }
    
    
    public static Product MakeProduct(AttributeValueMap attributeMap) throws SQLException {
        if(!attributeMap.HasAllRequiredAttributes())
            throw new IllegalArgumentException("Tried to make a product, but not all required attributes are defined");
        
        String sql = "INSERT INTO Product (" + ID_NAME;
        sql = AddAttributeNamesToSQL(sql, AlwaysReturn(", "), attributeMap.GetAttributeSet());
        sql += ") VALUES (nextval('" + ID_NAME +"')";
        
        for(int i = 0; i < attributeMap.Size(); i++) {
            sql += ", ?";
        }
        
        sql += ") RETURNING " + ID_NAME + ";";
        
        int id;
        try (ResultSet res = Querier.GetInstance().ExecuteQuery(sql, attributeMap);) {
            res.next();
            id = res.getInt(ID_NAME);
        }
        
        return new Product(id, attributeMap);
    }
    
    
    public static Product GetProduct(int id, Set<Attribute> attributeSet) throws SQLException {
        String sql = "SELECT ";
        sql = AddAttributeNamesToSQL(sql, attributeSet, AlwaysReturn(", "));
        
        //Remove the last comma
        sql = sql.substring(0, sql.length() - ", ".length());
        sql += " FROM Product WHERE " + ID_NAME + " = " + id + ";";
        
        try (ResultSet res = Querier.GetInstance().ExecuteQuery(sql);) {
            if(res.next())
                return ProductFromResultSet(res, id, attributeSet);
            return null;
        }
    }
    
    public static Product GetProduct(int id) throws SQLException {
        HashSet<Attribute> attributeSet = new HashSet<Attribute>();
        for(Attribute attribute : Attribute.values()) {
            attributeSet.add(attribute);
        }
        return GetProduct(id, attributeSet);
    }
    
    
    public static Product[] GetProducts(QueryConfig config) throws SQLException {
        String sql = "SELECT " + ID_NAME;
        sql = AddAttributeNamesToSQL(sql, AlwaysReturn(", "), config.GetShowSet());
        sql += " FROM Product ";
        
        if(config.FilterCount() > 0) {
            sql += "WHERE ";
            sql = AddAttributeNamesToSQL(sql, config.GetAttributeFilterMap().GetAttributeSet(), ((a) -> {return " " + config.GetFilter(a).ToString() + " AND ";}));
            //Remove the last AND
            sql = sql.substring(0, sql.length() - "AND ".length());
        }
        
        if(config.SortCount() > 0) {
            sql += "ORDER BY ";
            sql = AddAttributeNamesToSQL(sql, config.GetSortMap().keySet(), ((a) -> {return config.IsAscending(a) ? " ASC, " : " DESC, ";}));   
            //Remove the last comma
            sql = sql.substring(0, sql.length() - ", ".length());     
        }
        
        sql += " LIMIT " + config.GetLimit() + " OFFSET " + config.GetOffset() + ";";
            
        Product[] out = new Product[config.GetLimit()];
        try (ResultSet res = Querier.GetInstance().ExecuteQuery(sql, config.GetAttributeFilterMap());) {
            for(int i = 0; res.next(); i++) {
                out[i] = ProductFromResultSet(res, config.GetShowSet());
            }
        }
        
        return out;
    }
    
    public static void DeleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM Product WHERE " + ID_NAME + " = " + id + ";";
        Querier.GetInstance().Execute(sql);
    }
}
