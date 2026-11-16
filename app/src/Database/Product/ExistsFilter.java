public class ExistsFilter extends Filter {
    private boolean exists;
    
    public ExistsFilter(boolean exists) {
        this.exists = exists;
    }
    
    public void SetExistance(boolean exists) {this.exists = exists;}
    public boolean GetExistance() {return this.exists;}
    
    public boolean TypeAllowed(Type type) {
        switch(type) {
            case Type.INT: case Type.DOUBLE: case Type.DECIMAL: case Type.STRING: case Type.BOOL: case Type.IMAGE:
                return true;
            case null:
                throw new NullPointerException();
        }
    }
    
    public String ToString() {return exists ? "IS NOT NULL" : "IS NULL";}
    
    @Override
    public ExistsFilter Clone() {
        return new ExistsFilter(exists);
    }
    
    @Override
    public <T extends Throwable> int AcceptInt(IFilterVisitorInt<T> visitor) throws T {
        return visitor.CaseExists(this);
    }
}
