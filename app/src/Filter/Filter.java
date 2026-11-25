//Abstract class for filters. Only inheriting filters like EqualsFilter can be instantiated.

public abstract class Filter {
    //The order of the enums is used in the cycling of filters in the UI. See the FilterManager class
    public enum FType {
        CONTAINS,
        EQUALS,
        LEQ,
        GEQ,
        RANGE,
        EXISTS
    };

    protected boolean active;
    
    public boolean IsActive() {return active;}
    
    public void Activate() {active = true;}
    public abstract void Deactivate();
    public void SetActive(boolean b) {
        if(b)
            Activate();
        else
            Deactivate();
    }
    public void ToggleActive() {SetActive(!IsActive());}
    
    
    
    public abstract boolean AttributeAllowed(Attribute attribute);
    
    public abstract String ToSQL();
    
    public abstract Filter Clone();
    
    public abstract FType GetFType();
    
    public abstract <T, E extends Throwable> T Accept(IFilterVisitor<T, E> visitor) throws E;
}
