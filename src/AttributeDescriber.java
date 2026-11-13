

public class AttributeDescriber {
    static Type TypeOf(Attribute a) {
        switch(a) {
            case ID: case Attribute.NAME: case Attribute.FABRIC_ID: case Attribute.COMPOSITION: case Attribute.FINISH: case Attribute.COMMENTS: case Attribute.SIZE:
                return Type.STRING;
            case Attribute.COUNT:
                return Type.INT;
            case Attribute.SELL_PRICE: case Attribute.BUY_PRICE:
                return Type.DECIMAL;
            case Attribute.HIDDEN:
                return Type.BOOL;
            case Attribute.FABRIC:
                return Type.IMAGE;
            case null:
                throw new NullPointerException("Attribute is null");
        }
    }
    
    static String DkNameOf(Attribute a) {
        switch(a) {
            case ID:
                return "ID";
            case Attribute.NAME:
                return "Navn";
            case Attribute.FABRIC_ID:
                return "Stof-ID";
            case Attribute.COMPOSITION:
                return "Komposition";
            case Attribute.FINISH:
                return "Færdighed?";
            case Attribute.COMMENTS:
                return "Kommentarer";
            case Attribute.SIZE:
                return "Størrelse";
            case Attribute.COUNT:
                return "Antal";
            case Attribute.SELL_PRICE:
                return "Salgspris";
            case Attribute.BUY_PRICE:
                return "Købspris";
            case Attribute.HIDDEN:
                return "Skjult";
            case Attribute.FABRIC:
                return "Stof";
            case null:
                throw new NullPointerException("Attribute is null");
        }
    }
    
    static String EnNameOf(Attribute a) {
        switch(a) {
            case ID:
                return "ID";
            case Attribute.NAME:
                return "Name";
            case Attribute.FABRIC_ID:
                return "Fabric ID";
            case Attribute.COMPOSITION:
                return "Composition";
            case Attribute.FINISH:
                return "Finish";
            case Attribute.COMMENTS:
                return "Comments";
            case Attribute.SIZE:
                return "Size";
            case Attribute.COUNT:
                return "Count";
            case Attribute.SELL_PRICE:
                return "Sell price";
            case Attribute.BUY_PRICE:
                return "Purchace price";
            case Attribute.HIDDEN:
                return "Hidden";
            case Attribute.FABRIC:
                return "Fabric";
            case null:
                throw new NullPointerException("Attribute is null");
        }
    }
    
    static String DbNameOf(Attribute a) {
        switch(a) {
            case ID:
                return "string_id";
            case Attribute.NAME:
                return "name";
            case Attribute.FABRIC_ID:
                return "fabric_id";
            case Attribute.COMPOSITION:
                return "fabric_composition";
            case Attribute.FINISH:
                return "finish";
            case Attribute.COMMENTS:
                return "comments";
            case Attribute.SIZE:
                return "size";
            case Attribute.COUNT:
                return "count";
            case Attribute.SELL_PRICE:
                return "sell_price";
            case Attribute.BUY_PRICE:
                return "buy_price";
            case Attribute.HIDDEN:
                return "hidden";
            case Attribute.FABRIC:
                return "fabric";
            case null:
                throw new NullPointerException("Attribute is null");
        }
    }
    
    static boolean Required(Attribute a) {
        switch(a) {
            case ID: case Attribute.NAME: case Attribute.FABRIC_ID: case Attribute.SIZE: case Attribute.COUNT: case Attribute.HIDDEN:
                return true;
            case Attribute.COMPOSITION: case Attribute.FINISH: case Attribute.COMMENTS: case Attribute.FABRIC: case Attribute.SELL_PRICE: case Attribute.BUY_PRICE:
                return false;
            case null:
                throw new NullPointerException("Attribute is null");
        }
    }
}
