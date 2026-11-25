public abstract class SingleValueFilter<T> extends Filter {
    protected T value;
    
    protected SingleValueFilter() {
        Deactivate();
    }
    
    protected SingleValueFilter(T value) {
        SetValue(value);
    }
    
    public void SetValue(T value) {SetActive(value != null); this.value = value;}
    public T GetValue() {return this.value;}
    
    public void Deactivate() {value = null; active = false;}
}
