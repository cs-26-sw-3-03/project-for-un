public interface IReturn{
    public ITransact GetTransact();
    public int count GetCount();

    public void SetTransact(ITransact transact);
    public void SetCount(int count);
}