import java.time.LocalDate;

public class OrderListe {
    public final String productName;
    public final int amount;
    public float price;
    private LocalDate returnDate;

    public OrderListe(String productName, int amount, float price) {
        this.productName = productName;
        this.amount = amount;
        this.price = price;
    }

    public boolean isReturned()                 {return returnDate != null;}
    public LocalDate getReturnedDate()          {return returnDate;}
    public void markReturned()                  {markReturned(LocalDate.now());}
    public void markReturned(LocalDate date)    {returnDate = date;}
}