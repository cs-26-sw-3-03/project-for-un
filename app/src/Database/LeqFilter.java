public class LeqFilter extends Filter {
    private Object value;
    
    public LeqFilter(Object value) {
        this.value = value;
    }
    
    public void SetValue(Object value) {this.value = value;}
    public Object GetValue() {return this.value;}
    
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
    
    public String ToString() {return "<= ?";}
    
    @Override
    public LeqFilter Clone() {
        return new LeqFilter(value);
    }
    
    public <T extends Throwable> int AcceptInt(IFilterVisitorInt<T> visitor) throws T {
        return visitor.CaseLeq(this);
    }
}
