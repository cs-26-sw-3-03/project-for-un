public class RangeFilter extends Filter {
    private Object min;
    private Object max;
    
    public RangeFilter(Object min, Object max) {
        this.min = min;
        this.max = max;
    }
    
    public void SetMin(Object min) {this.min = min;}
    public void SetMax(Object max) {this.max = max;}
    public void SetRange(Object min, Object max) {this.min = min; this.max = max;}
    public Object GetMin() {return this.min;}
    public Object GetMax() {return this.max;}
    
    public boolean TypeAllowed(Type type) {
        switch(type) {
            case Type.INT: case Type.DOUBLE: case Type.DECIMAL:
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
    
    public <T extends Throwable> int AcceptInt(IFilterVisitorInt<T> visitor) throws T {
        return visitor.CaseRange(this);
    }
}
