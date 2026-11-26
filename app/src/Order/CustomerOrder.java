import java.time.LocalDate;

public class CustomerOrder extends Order {
    private LocalDate date;
    
    public CustomerOrder() {
        super();
    }

    public CustomerOrder(Integer id) {
        super(id, OrderStatus.COMPLETED);
    }

    public CustomerOrder(LocalDate date) {
        super();
        this.date = date;
    }

    public LocalDate GetDate() {
        return date;
    }
}
