import java.awt.*;
import javax.swing.*;
import java.util.Objects;

public class Main {
  // private final Dimension initWindowSize = new Dimension(400, 300);
  private static JFrame window;

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

    panel.add(new testPage(), BorderLayout.CENTER);

    window.add(panel);
    window.pack();
    window.setVisible(true);
  }

  public static void switchPage(PageButtons.Pages page) { // Recieves PAGE_NAME
    System.out.println(page);
    // System.out.println(window.getContentPane().getComponents());
    JPanel mainPanel = null;
    for (Component c : window.getContentPane().getComponents()) {
      if (c instanceof JPanel p) {mainPanel = p; break;}
    }
    if (mainPanel == null) {System.out.println("switchPage -> mainPanel == NULL"); return; }

    BorderLayout layout = (BorderLayout)mainPanel.getLayout();
    mainPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
    mainPanel.add(new JLabel(page.toString()), BorderLayout.CENTER);

    switch(page) {
      case HOME_PAGE:
        mainPanel.add(new HomePage(), BorderLayout.CENTER);
       break;
      case PRODUCER_ORDERS:
        mainPanel.add(new ProducerOrders(), BorderLayout.CENTER);
       break;
      case CUSTOMER_ORDERS:
        mainPanel.add(new CustomerOrders(), BorderLayout.CENTER);
       break;
      case STATISTICS:
        mainPanel.add(new Statistics(), BorderLayout.CENTER);
       break;
      case PRODUCT_DETAILS:
        mainPanel.add(new ProductDetails(), BorderLayout.CENTER);
       break;
      case NEW_PRODUCT:
        mainPanel.add(new NewProduct(), BorderLayout.CENTER);
       break;
      case SETTINGS:
        mainPanel.add(new Settings(), BorderLayout.CENTER);
       break;

      default:
       System.out.println("ERROR: DEFAULTED PAGE");
       break;
    }

    window.pack();
    window.setVisible(true);
  }

}
