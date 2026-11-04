import java.awt.*;
import javax.swing.*;

public class PageSwitcher {
  public static void switchPage (Pages page) { // Recieves PAGE_NAME
    // System.out.println(page);

    // Get mainPanel (the frame on screen)
    JPanel mainPanel = null;
    for (Component c : Main.window.getContentPane().getComponents()) {
      if (c instanceof JPanel p) {mainPanel = p; break;}
    }
    if (mainPanel == null) {System.out.println("switchPage -> mainPanel == NULL"); return; }

    // Remove old page from screen
    BorderLayout layout = (BorderLayout)mainPanel.getLayout();
    mainPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));

    // Add new page to screen
    JPanel showedPage = null;
    switch(page) {
      case HOME_PAGE:
        showedPage = new HomePage();
        Main.currentPage = Pages.HOME_PAGE;
       break;
      case SUPPLIER_ORDERS:
        showedPage = new SupplierOrderPage();
        Main.currentPage = Pages.SUPPLIER_ORDERS;
       break;
      case CUSTOMER_ORDERS:
        showedPage = new CustomerOrderPage();
        Main.currentPage = Pages.CUSTOMER_ORDERS;
       break;
      case STATISTICS:
        showedPage = new StatisticsPage();
        Main.currentPage = Pages.STATISTICS;
       break;
      case PRODUCT_DETAILS:
        showedPage = new ProductDetailsPage();
        Main.currentPage = Pages.PRODUCT_DETAILS;
       break;
      case NEW_PRODUCT:
        showedPage = new NewProductPage();
        Main.currentPage = Pages.NEW_PRODUCT;
       break;
      case SETTINGS:
        showedPage = new SettingsPage();
        Main.currentPage = Pages.SETTINGS;
       break;

      default:
        System.out.println("ERROR: DEFAULTED PAGE");
        showedPage = new HomePage();
        Main.currentPage = Pages.HOME_PAGE;
       break;
    }
    mainPanel.add(showedPage, BorderLayout.CENTER);

    Main.window.pack();
  }
}
