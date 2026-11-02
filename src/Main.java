import java.awt.*;
import javax.swing.*;
import java.util.Objects;

public class Main {
  public enum Pages {
    HOME_PAGE,
    PRODUCER_ORDERS,
    CUSTOMER_ORDERS,
    STATISTICS,
    PRODUCT_DETAILS,
    NEW_PRODUCT,
    SETTINGS
  }
  // private final Dimension initWindowSize = new Dimension(400, 300);
  private static JFrame window;
  public Pages currentPage = Pages.HOME_PAGE;

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
	    public void run() { showWindow(); }
    });
  }


  public static void showWindow() {
    window = new JFrame("Window Name");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setLocationRelativeTo(null);
    JPanel panel = new JPanel(new BorderLayout(0,0));
    
    panel.add(new PageButtons(Main::switchPage), BorderLayout.SOUTH);

    panel.add(new HomePage(), BorderLayout.CENTER);

    window.add(panel);
    window.pack();
    window.setVisible(true);
  }

  public static void switchPage(PageButtons.Pages page) { // Recieves PAGE_NAME
    // System.out.println(page);
    // Get mainPanel (the frame on screen)
    JPanel mainPanel = null;
    for (Component c : window.getContentPane().getComponents()) {
      if (c instanceof JPanel p) {mainPanel = p; break;}
    }
    if (mainPanel == null) {System.out.println("switchPage -> mainPanel == NULL"); return; }

    // Remove old page from screen
    BorderLayout layout = (BorderLayout)mainPanel.getLayout();
    mainPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));

    // Add new page to screen
    switch(page) {
      case HOME_PAGE:
        currentPage = Pages.HOME_PAGE;
        mainPanel.add(new HomePage(), BorderLayout.CENTER);
       break;
      case PRODUCER_ORDERS:
        currentPage = Pages.PRODUCER_ORDERS;
        mainPanel.add(new ProducerOrders(), BorderLayout.CENTER);
       break;
      case CUSTOMER_ORDERS:
        currentPage = Pages.CUSTOMER_ORDERS;
        mainPanel.add(new CustomerOrders(), BorderLayout.CENTER);
       break;
      case STATISTICS:
        currentPage = Pages.STATISTICS;
        mainPanel.add(new Statistics(), BorderLayout.CENTER);
       break;
      case PRODUCT_DETAILS:
        currentPage = Pages.PRODUCT_DETAILS;
        mainPanel.add(new ProductDetails(), BorderLayout.CENTER);
       break;
      case NEW_PRODUCT:
        currentPage = Pages.NEW_PRODUCT;
        mainPanel.add(new NewProduct(), BorderLayout.CENTER);
       break;
      case SETTINGS:
        currentPage = Pages.SETTINGS;
        mainPanel.add(new Settings(), BorderLayout.CENTER);
       break;

      default:
        System.out.println("ERROR: DEFAULTED PAGE");
        currentPage = Pages.HOME_PAGE;
        mainPanel.add(new HomePage(), BorderLayout.CENTER);
       break;
    }

    window.pack();
    // window.setVisible(true);
  }

}
