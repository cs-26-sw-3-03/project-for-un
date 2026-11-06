import java.time.LocalDate;

public interface IOrderReturn {
    public LocalDate GetDate();
    public int GetId();
    public IOrder GetOrder();
    
    public void SetDate(LocalDate a);
    public void SetId(int a);
    
    public int GetReturnCount();
    public boolean HasReturn(IReturn a);
    
    public void AddReturn(IReturn a);
    public IReturn PopReturn(IReturn a);
    public void ClearReturns();
}
