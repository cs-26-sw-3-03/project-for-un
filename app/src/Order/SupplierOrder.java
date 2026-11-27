import java.time.LocalDate;

public class SupplierOrder extends Order {

    public SupplierOrder() {
        super();
    }

    public SupplierOrder(Integer id, LocalDate date, boolean finished) {
        super(id, finished ? OrderStatus.COMPLETED : OrderStatus.ONGOING, date);
    }

    public SupplierOrder(LocalDate date) {
        super(date);
    }
}
