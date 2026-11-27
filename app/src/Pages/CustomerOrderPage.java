public class CustomerOrderPage extends OrderPage {
  public CustomerOrderPage() {
    super(
      new String[] {"Produkt navn", "Antal", "K\u00f8bspris"},
      new String[] {"0", "1", "2", "3"}
    );
  }
    private static CustomerOrderPage Instance;

    public static CustomerOrderPage getInstance() {
      if (Instance == null) {
        Instance = new CustomerOrderPage();
      }
      return Instance;
    }
}
