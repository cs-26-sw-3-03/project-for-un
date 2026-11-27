public class SupplierOrderPage extends OrderPage {
  public SupplierOrderPage() {
    super(
      new String[] {"Vare-id", "Produkt navn", "K\u00f8bspris", "Antal"},
      new String[] {"Status", "Ordre nr.", "Ordre dato", "Leverings dato"}
    );
  }

  private static SupplierOrderPage Instance;

  public static SupplierOrderPage getInstance() {
    if (Instance == null) {
      Instance = new SupplierOrderPage();
    }
    return Instance;
  }
}
