//Abstract class for filters. Only inheriting filters like EqualsFilter can be instantiated.

public abstract class Filter {
    protected Object value;
    
    public abstract boolean TypeAllowed(Type type);
    
    public abstract String ToString();
    
    public abstract Filter Clone();
    
    public abstract <T, E extends Throwable> T Accept(IFilterVisitor<T, E> visitor) throws E;
}
