import java.sql.SQLException;

//This class can visit a filter, and make it call one method depending on the type of filter, returning an int and throwing some user defined exception T

public interface IFilterVisitorInt<T extends Throwable> {
    public int CaseExists(ExistsFilter filter) throws T;
    public int CaseRange(RangeFilter filter) throws T;
    public int CaseEquals(EqualsFilter filter) throws T;
    public int CaseGeq(GeqFilter filter) throws T;
    public int CaseLeq(LeqFilter filter) throws T;
    public int CaseContains(ContainsFilter filter) throws T;
}
