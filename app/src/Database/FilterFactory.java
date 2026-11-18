public class FilterFactory {
    public static Filter MakeFilter(Filter.FType type) {
        switch(type) {
            case CONTAINS:
                return new ContainsFilter();
            case EQUALS:
                return new EqualsFilter();
            case LEQ:
                return new LeqFilter();
            case GEQ:
                return new GeqFilter();
            case RANGE:
                return new RangeFilter();
            case EXISTS:
                return new ExistsFilter();
            case null:
                throw new NullPointerException();
        }
    }
}
