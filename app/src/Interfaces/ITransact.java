public interface ITransact {
    public Product GetProduct();
    public int GetCount();
    public double GetPrice();
    
    public void SetProduct(Product a);
    public void SetCount(int a);
    public void SetPrice(double a);
}
