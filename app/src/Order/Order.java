import java.util.ArrayList;
import java.time.LocalDate;

public abstract class Order {
    protected Integer id;
    protected ArrayList<Transaction> transactions;
    protected OrderStatus status;

    public Order() {
        this.id = null;
        this.transactions = new ArrayList<>();
        this.status = OrderStatus.DRAFT;
    }

    public Order(Integer id, OrderStatus status) {
        this.id = id;
        this.transactions = new ArrayList<>();
        this.status = status;
    }

    public Integer GetId() {
        return id;
    }
    
    public ArrayList<Transaction> GetTransactions() {
        return transactions;
    }

    public OrderStatus GetStatus() {
        return status;
    }
    public void SetId(Integer id) {
        this.id = id;
    }

    public void SetStatus(OrderStatus status) {
        this.status = status;
    }

    public void AddTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void RemoveTransaction(Transaction transaction) {
        transactions.remove(transaction);
    }
}
