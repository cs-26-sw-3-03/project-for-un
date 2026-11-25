import java.sql.SQLException;

//This class can visit a filter, and make it call one method depending on the type of filter, returning a type T and throwing some user defined exception E

public interface IFilterVisitor<T, E extends Throwable> {
    public T CaseExists(ExistsFilter filter) throws E;
    public T CaseRange(RangeFilter filter) throws E;
    public T CaseEquals(EqualsFilter filter) throws E;
    public T CaseGeq(GeqFilter filter) throws E;
    public T CaseLeq(LeqFilter filter) throws E;
    public T CaseContains(ContainsFilter filter) throws E;
}
