

public class TypeFinder {
    static Type TypeOf(Attribute a) {
        switch(a) {
            case ID: case Attribute.NAME: case Attribute.FABRIC_ID: case Attribute.COMPOSITION: case Attribute.FINISH: case Attribute.COMMENTS: case Attribute.SIZE:
                return Type.STRING;
            case Attribute.COUNT:
                return Type.INT;
            case Attribute.SELL_PRICE: case Attribute.BUY_PRICE:
                return Type.DOUBLE;
            case Attribute.HIDDEN:
                return Type.BOOL;
            case Attribute.FABRIC:
                return Type.IMAGE;
            default:
                throw new IllegalArgumentException("Unsupported attribute enum was used");
        }
    }
}
