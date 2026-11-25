public class LeqFilter extends Filter {
    private Object value;
    
    public LeqFilter() {
        Deactivate();
    }
    
    public LeqFilter(Object value) {
        SetValue(value);
    }
    
    public void SetValue(Object value) {Activate(); this.value = value;}
    public Object GetValue() {return this.value;}
    
    public boolean AttributeAllowed(Attribute attribute) {
        switch(AttributeDescriber.TypeOf(attribute)) {
            case Type.INT: case Type.DECIMAL:
                return true;
            case Type.STRING: case Type.BOOL: case Type.IMAGE:
                return false;
            case null:
                throw new NullPointerException();
        }
    }
    
    public String ToString() {return "<= ?";}
    
    @Override
    public LeqFilter Clone() {
        return new LeqFilter(value);
    }
    
    public FType GetFType() {
        return FType.LEQ;
    }
    
    public <T, E extends Throwable> T Accept(IFilterVisitor<T, E> visitor) throws E {
        return visitor.CaseLeq(this);
    }
}
