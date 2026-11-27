import java.time.LocalDate;

public class CustomerOrder extends Order {
    
    public CustomerOrder() {
        super();
    }

    public CustomerOrder(Integer id) {
        super(id, OrderStatus.COMPLETED);
    }

    public CustomerOrder(LocalDate date) {
        super(date);
    }

    public CustomerOrder(Integer id, LocalDate date) {
        super(id, OrderStatus.COMPLETED, date);
    }
}
