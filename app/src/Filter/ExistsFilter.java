public class ExistsFilter extends SingleValueFilter<Boolean> {
    public ExistsFilter() {super();}    
    public ExistsFilter(boolean exists) {super(exists);}
    
    public boolean AttributeAllowed(Attribute attribute) {
        return !AttributeDescriber.Required(attribute);
    }
    
    public String ToSQL() {return value ? "IS NOT NULL" : "IS NULL";}
    
    public ExistsFilter Clone() {
        return new ExistsFilter(value);
    }
    
    public FType GetFType() {
        return FType.EXISTS;
    }
    
    public <T, E extends Throwable> T Accept(IFilterVisitor<T, E> visitor) throws E {
        return visitor.CaseExists(this);
    }
}
