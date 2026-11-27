import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Order {
    protected Integer id;
    protected ArrayList<Transaction> transactions;
    protected OrderStatus status;
    protected LocalDate date;

    public Order() {
        this.id = null;
        this.transactions = new ArrayList<>();
        this.status = OrderStatus.DRAFT;
        this.date = null;
    }

    public Order(Integer id, OrderStatus status) {
        this.id = id;
        this.transactions = new ArrayList<>();
        this.status = status;
        this.date = null;
    }

    public Order(LocalDate date) {
        this.id = null;
        this.transactions = new ArrayList<>();
        this.status = OrderStatus.DRAFT;
        this.date = date;
    }

    public Order(Integer id, OrderStatus status, LocalDate date) {
        this.id = id;
        this.transactions = new ArrayList<>();
        this.status = status;
        this.date = date;
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

    public LocalDate GetDate() {
        return date;
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
