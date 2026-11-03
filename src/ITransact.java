public interface ITransact {
    public IProduct GetProduct();
    public int GetCount();
    public double GetPrice();
    
    public void SetProduct(IProduct a);
    public void SetCount(int a);
    public void SetPrice(double a);
}
