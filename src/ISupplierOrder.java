
public interface ISupplierOrder {
    IOrderStatus getStatus();
    LocalDate getDeliverDate();
    
    void setStatus(IOrderStatus status);
    void setDeliveryDate(LocalDate DeliveryDate);
}

