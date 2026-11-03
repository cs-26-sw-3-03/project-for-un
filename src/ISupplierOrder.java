import java.time.LocalDate;

public interface ISupplierOrder extends IOrder{
    public OrderStatus getStatus();
    public LocalDate getDeliverDate();
    
    void setStatus(OrderStatus status);
    void setDeliveryDate(LocalDate DeliveryDate);
}

