import java.time.LocalDate;

public class SupplierOrder extends Order {
    private LocalDate date;

    public SupplierOrder() {
        super();
    }

    public SupplierOrder(Integer id, LocalDate date, boolean finished) {
        super(id, finished ? OrderStatus.COMPLETED : OrderStatus.ONGOING);
        this.date = date;
    }

    public SupplierOrder(LocalDate date) {
        super();
        this.date = date;
    }
/*
    public SupplierOrder(int id, LocalDate date, boolean finished) {
        super(id);
        this.date = date;

        if (finished) {
            this.status = OrderStatus.COMPLETED;
        } else {
            this.status = OrderStatus.ONGOING;
        }
    }
*/

    public LocalDate GetDate() {
        return date;
    }
}
