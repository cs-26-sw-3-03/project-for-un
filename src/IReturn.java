public interface IReturn{
    public ITransact GetTransact();
    public int GetCount();

    public void SetTransact(ITransact transact);
    public void SetCount(int count);
}
