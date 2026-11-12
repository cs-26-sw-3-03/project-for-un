import java.awt.*;
import javax.swing.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.awt.event.*;

public class PageButtons extends JPanel {
  private static java.util.HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme();

  public PageButtons() {
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
          labelButtons[ordinal].setBackground(themeColors.get("normal_knap_highlight"));
          // labelButtons[ordinal].setBackground(Color.PINK);
        }
        public void mouseExited(MouseEvent e) {
          labelButtons[ordinal].setOpaque(false);
          labelButtons[ordinal].setBackground(themeColors.get("normal_knap_standard"));
          // labelButtons[ordinal].setBackground(Color.WHITE);
        }
        public void mousePressed(MouseEvent e) {
          labelButtons[ordinal].setBackground(themeColors.get("normal_knap_tryk"));
          // labelButtons[ordinal].setBackground(Color.RED);
        }
        public void mouseReleased(MouseEvent e) {
          labelButtons[ordinal].setBackground(themeColors.get("normal_knap_standard"));
          // labelButtons[ordinal].setBackground(Color.WHITE);
        }
        public void mouseClicked(MouseEvent e) {
          PageSwitcher.switchPage(page);
        }
      });

      add(labelButtons[ordinal]);
    }
  }
}
