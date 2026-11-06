import java.time.LocalDate;

public interface IOrder {
    public enum OrderStatus {
        DRAFT,
        ONGOING,
        FINISHED
    }

    public LocalDate GetDate();
    public int GetId();
    public ITransact GetTransact(int a);
    
    public void SetDate(LocalDate a);
    public void SetId(int a);
    
    public int GetTransactCount();
    public boolean HasTransact(ITransact a);
    
    public void AddTransact(ITransact a);
    public ITransact PopTransact(ITransact a);
    public void ClearTransacts();
}
