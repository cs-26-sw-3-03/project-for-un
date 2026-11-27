import java.math.BigDecimal;

public class Transaction {
    private int intId;
    private String stringId;
    private String productName;
    private int count;
    private BigDecimal price;

    public Transaction(int intId, String stringId, String productName, int count, BigDecimal price) {
        this.intId = intId;
        this.stringId = stringId;
        this.productName = productName;
        this.count = count;
        this.price = price;
    }

    public int GetIntId() {
        return intId;
    }

    public String GetStringId() {
        return stringId;
    }

    public String GetProductName() {
        return productName;
    }

    public int GetCount() {
        return count;
    }

    public BigDecimal GetPrice() {
        return price;
    }

    public void SetProduct(int intId, String stringId, String productName) {
        this.intId = intId;
        this.stringId = stringId;
        this.productName = productName;
    }

    public void SetCount(int count) {
        this.count = count;
    }

    public void SetPrice(BigDecimal price) {
        this.price = price;
    }
}
