public class ExistsFilter extends Filter {
    private Boolean exists;
    
    public ExistsFilter() {
        Deactivate();
    }
    
    public ExistsFilter(boolean exists) {
        SetExistance(exists);
    }
    
    
    public void SetExistance(boolean exists) {Activate(); this.exists = exists;}
    public boolean GetExistance() {return this.exists;}
    
    @Override
    public void Deactivate() {exists = null; active = false;}
    
    public boolean AttributeAllowed(Attribute attribute) {
        return !AttributeDescriber.Required(attribute);
    }
    
    public String ToString() {return exists ? "IS NOT NULL" : "IS NULL";}
    
    @Override
    public ExistsFilter Clone() {
        return new ExistsFilter(exists);
    }
    
    public FType GetFType() {
        return FType.EXISTS;
    }
    
    @Override
    public <T, E extends Throwable> T Accept(IFilterVisitor<T, E> visitor) throws E {
        return visitor.CaseExists(this);
    }
}
