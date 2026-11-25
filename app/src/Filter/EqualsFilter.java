public class EqualsFilter extends SingleValueFilter<Object> {
    public EqualsFilter() {super();}
    public EqualsFilter(Object value) {super(value);}
    
    public boolean AttributeAllowed(Attribute attribute) {
        switch(AttributeDescriber.TypeOf(attribute)) {
            case Type.INT: case Type.DECIMAL: case Type.STRING: case Type.BOOL:
                return true;
            case Type.IMAGE:
                return false;
            case null:
                throw new NullPointerException();
        }
    }
    
    public String ToSQL() {return "= ?";}
    
    public EqualsFilter Clone() {
        return new EqualsFilter(value);
    }
    
    public FType GetFType() {
        return FType.EQUALS;
    }
    
    public <T, E extends Throwable> T Accept(IFilterVisitor<T, E> visitor) throws E {
        return visitor.CaseEquals(this);
    }
}
