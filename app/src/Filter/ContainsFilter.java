public class ContainsFilter extends SingleValueFilter<String> {
    public ContainsFilter() {super();}
    public ContainsFilter(String value) {super(value);}
    
    public boolean AttributeAllowed(Attribute attribute) {
        switch(AttributeDescriber.TypeOf(attribute)) {
            case Type.STRING:
                return true;
            case Type.INT: case Type.DECIMAL: case Type.BOOL: case Type.IMAGE:
                return false;
            case null:
                throw new NullPointerException();
        }
    }
    
    public String ToSQL() {return "LIKE CONCAT('%', ?, '%')";}
    
    public ContainsFilter Clone() {
        return new ContainsFilter(value);
    }
    
    public FType GetFType() {
        return FType.CONTAINS;
    }
    
    public <T, E extends Throwable> T Accept(IFilterVisitor<T, E> visitor) throws E {
        return visitor.CaseContains(this);
    }
}
