import java.awt.*;
import javax.swing.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class PageButtons extends JPanel {
  public enum Pages {
    HOME_PAGE,
    PRODUCER_ORDERS,
    CUSTOMER_ORDERS,
    STATISTICS,
    PRODUCT_DETAILS,
    NEW_PRODUCT,
    SETTINGS
  }

  public PageButtons(Consumer<Pages> pageSwitcher) {
    setLayout(new GridLayout(1,7,0,0));

    JButton[] buttons = new JButton[7];

    for (Pages page : Pages.values()) { 
      int ordinal = page.ordinal();
      buttons[ordinal] = new JButton(page.toString().replace('_',' '));
      buttons[ordinal].setPreferredSize(new Dimension(100,50));
      buttons[ordinal].addActionListener(e -> pageSwitcher.accept(page));
      add(buttons[ordinal]);
    }
  }
}
