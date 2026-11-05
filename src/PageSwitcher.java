import java.awt.*;
import javax.swing.*;

public class PageSwitcher {
  public static void switchPage (Pages page) { // Recieves PAGE_NAME
    // System.out.println(page);

    // Remove old page from screen
    Component context = ((BorderLayout)Main.contextPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
    if (context != null) Main.contextPanel.remove(context);

    // Component popup = ((BorderLayout)Main.popupPanel.getLayout()).getLayoutComponent(BorderLayout.CENTER);
    // if (popup != null) Main.popupPanel.remove(popup);
    Main.popupPanel.removeAll();
    PopupManager.RemoveAll();

    // BorderLayout popupLayout = (BorderLayout)Main.popupPanel.getLayout();
    // Main.popupPanel.remove(popupLayout.getLayoutComponent(BorderLayout.CENTER));
    //
    // Main.contextPanel.remove(Main.contextPanel.getLayout().getLayoutComponent(BorderLayout.CENTER));
    // Main.popupPanel.remove(Main.contextPanel.getLayout().getLayoutComponent(BorderLayout.CENTER));
    //
    // System.out.println(Main.mainPanel.getLayout().getLayoutComponent(BorderLayout.SOUTH));

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
    Main.contextPanel.add(showedPage);

    Main.window.revalidate();
    Main.window.repaint();
  }
}
