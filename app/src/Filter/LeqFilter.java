public class LeqFilter extends SingleValueFilter<Object> {
    public LeqFilter() {super();}
    public LeqFilter(Object value) {super(value);}
    
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
    
    public String ToSQL() {return "<= ?";}
    
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
