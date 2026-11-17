//Abstract class for filters. Only inheriting filters like EqualsFilter can be instantiated.

public abstract class IFilter {
    protected Object value;
    
    public abstract boolean TypeAllowed(Type type);
    
    public abstract String ToString();
    
    public abstract IFilter Clone();
    
    public abstract <T, E extends Throwable> T Accept(IFilterVisitor<T, E> visitor) throws E;
}
