public class GeqFilter extends SingleValueFilter<Object> {
    public GeqFilter() {super();}
    public GeqFilter(Object value) {super(value);}
        
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
    
    public String ToSQL() {return ">= ?";}
    
    public GeqFilter Clone() {
        return new GeqFilter(value);
    }
    
    public FType GetFType() {
        return FType.GEQ;
    }
    
    public <T, E extends Throwable> T Accept(IFilterVisitor<T, E> visitor) throws E {
        return visitor.CaseGeq(this);
    }
}
