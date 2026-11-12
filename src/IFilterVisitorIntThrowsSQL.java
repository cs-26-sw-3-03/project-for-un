import java.sql.SQLException;

public interface IFilterVisitorIntThrowsSQL {
    public int CaseExists(ExistsFilter filter) throws SQLException;
    public int CaseRange(RangeFilter filter) throws SQLException;
    public int CaseEquals(EqualsFilter filter) throws SQLException;
    public int CaseGeq(GeqFilter filter) throws SQLException;
    public int CaseLeq(LeqFilter filter) throws SQLException;
    public int CaseContains(ContainsFilter filter) throws SQLException;
}
