import java.awt.*;
import javax.swing.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.awt.event.*;

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

    JLabel[] labelButtons = new JLabel[Pages.values().length];
    for (Pages page : Pages.values()) { 
      int ordinal = page.ordinal();
      labelButtons[ordinal] = new JLabel(page.toString().replace('_',' '), SwingConstants.CENTER);
      if (ordinal > 0)
        labelButtons[ordinal].setBorder(BorderFactory.createMatteBorder(0,1,0,0,Color.GRAY));
      labelButtons[ordinal].setPreferredSize(new Dimension(100,50));

      labelButtons[ordinal].addMouseListener(new MouseListener() {
        public void mouseEntered(MouseEvent e) {
          labelButtons[ordinal].setOpaque(true);
          // labelButtons[ordinal].setBackground((Color)customTheme.buttonHovered());
          labelButtons[ordinal].setBackground(Color.PINK);
        }
        public void mouseExited(MouseEvent e) {
          labelButtons[ordinal].setOpaque(false);
          // labelButtons[ordinal].setBackground((Color)customTheme.buttonDefaultState());
          labelButtons[ordinal].setBackground(Color.WHITE);
        }
        public void mousePressed(MouseEvent e) {
          // labelButtons[ordinal].setBackground((Color)customTheme.buttonPressed());
          labelButtons[ordinal].setBackground(Color.RED);
        }
        public void mouseReleased(MouseEvent e) {
          // labelButtons[ordinal].setBackground((Color)customTheme.buttonDefaultState());
          labelButtons[ordinal].setBackground(Color.WHITE);
        }
        public void mouseClicked(MouseEvent e) {
          pageSwitcher.accept(page);
        }
      });

      add(labelButtons[ordinal]);
    }
  }
}
