public class FilterToFilterInputVisitor implements IFilterVisitor<FilterInputPanel, ImpossibleException> {
    private Type type;
    private int width;
    private int height;
    
    public FilterToFilterInputVisitor(int preferedWidth, int preferedHeight, Type type) {
        this.type = type;
        this.width = preferedWidth;
        this.height = preferedHeight;
    }

    public ExistsFilterPanel CaseExists(ExistsFilter filter) throws ImpossibleException {
        if(filter.IsActive())
            return new ExistsFilterPanel(width, height, filter.GetValue());
        return new ExistsFilterPanel(width, height);
    }
    
    public RangeFilterPanel CaseRange(RangeFilter filter) throws ImpossibleException {
        if(filter.IsActive())
            return new RangeFilterPanel(width, height, filter.GetMin().toString(), filter.GetMax().toString(), type);
        return new RangeFilterPanel(width, height, type);
    }
    
    public FilterInputPanel CaseEquals(EqualsFilter filter) throws ImpossibleException {
        if(filter.IsActive()) {
            if(type == Type.BOOL)
                return new BooleanFilterPanel(width, height, (Boolean) filter.GetValue());
            return new EqualsFilterPanel(width, height, filter.GetValue().toString(), type);
        }
        if(type == Type.BOOL)
            return new BooleanFilterPanel(width, height);
        return new EqualsFilterPanel(width, height, type);
    }
    
    public GeqFilterPanel CaseGeq(GeqFilter filter) throws ImpossibleException {
        if(filter.IsActive())
            return new GeqFilterPanel(width, height, filter.GetValue().toString(), type);
        return new GeqFilterPanel(width, height, type);
    }
    
    public LeqFilterPanel CaseLeq(LeqFilter filter) throws ImpossibleException {
        if(filter.IsActive())
            return new LeqFilterPanel(width, height, filter.GetValue().toString(), type);
        return new LeqFilterPanel(width, height, type);
    }
    
    public ContainsFilterPanel CaseContains(ContainsFilter filter) throws ImpossibleException {
        if(filter.IsActive())
            return new ContainsFilterPanel(width, height, filter.GetValue().toString());
        return new ContainsFilterPanel(width, height);
    }
}
