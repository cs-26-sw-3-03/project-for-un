import java.sql.SQLException;

public interface IFilterVisitorInt<T extends Throwable> {
    public int CaseExists(ExistsFilter filter) throws T;
    public int CaseRange(RangeFilter filter) throws T;
    public int CaseEquals(EqualsFilter filter) throws T;
    public int CaseGeq(GeqFilter filter) throws T;
    public int CaseLeq(LeqFilter filter) throws T;
    public int CaseContains(ContainsFilter filter) throws T;
}
