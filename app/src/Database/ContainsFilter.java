public class ContainsFilter extends Filter {
    private String value;
    
    public ContainsFilter() {
        Deactivate();
    }
    
    public ContainsFilter(String value) {
        SetValue(value);
    }
    
    public void SetValue(String value) {Activate(); this.value = value;}
    public String GetValue() {return this.value;}
    
    public boolean AttributeAllowed(Attribute attribute) {
        switch(AttributeDescriber.TypeOf(attribute)) {
            case Type.STRING:
                return true;
            case Type.INT: case Type.DOUBLE: case Type.DECIMAL: case Type.BOOL: case Type.IMAGE:
                return false;
            case null:
                throw new NullPointerException();
        }
    }
    
    public String ToString() {return "LIKE ?";}
    
    @Override
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
