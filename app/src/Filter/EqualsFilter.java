public class EqualsFilter extends Filter {
    
    public EqualsFilter() {
        Deactivate();
    }
    
    public EqualsFilter(Object value) {
        SetValue(value);
    }
    
    public void SetValue(Object value) {Activate(); this.value = value;}
    public Object GetValue() {return this.value;}
    
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
    
    public String ToString() {return "= ?";}
    
    @Override
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
