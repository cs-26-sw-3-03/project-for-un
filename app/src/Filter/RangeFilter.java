public class RangeFilter extends Filter {
    private Object min;
    private Object max;
    
    public RangeFilter() {
        Deactivate();
    }
    
    public RangeFilter(Object min, Object max) {
        SetMin(min);
        SetMax(max);
    }
    
    public void SetMin(Object min) {Activate(); this.min = min;}
    public void SetMax(Object max) {Activate(); this.max = max;}
    public void SetRange(Object min, Object max) {Activate(); this.min = min; this.max = max;}
    public Object GetMin() {return this.min;}
    public Object GetMax() {return this.max;}
    
    @Override
    public void Deactivate() {min = null; max = null; active = false;}
    
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
    
    public String ToString() {return "BETWEEN ? AND ?";}
    
    @Override
    public RangeFilter Clone() {
        return new RangeFilter(min, max);
    }
    
    public FType GetFType() {
        return FType.RANGE;
    }
    
    public <T, E extends Throwable> T Accept(IFilterVisitor<T, E> visitor) throws E {
        return visitor.CaseRange(this);
    }
}
