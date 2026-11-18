import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;

public class LabelButtonListener implements MouseListener {
  private static HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme();

  @Override
  public void mouseEntered(MouseEvent e) {
    JLabel label = (JLabel)e.getSource();
    label.setOpaque(true);
    label.setBackground(themeColors.get("normal_knap_fremhævet"));
  }
  @Override
  public void mouseExited(MouseEvent e) {
    JLabel label = (JLabel)e.getSource();
    label.setOpaque(false);
    label.setBackground(themeColors.get("normal_knap_standard"));
  }
  @Override
  public void mousePressed(MouseEvent e) {
    JLabel label = (JLabel)e.getSource();
    label.setBackground(themeColors.get("normal_knap_tryk"));
  }
  @Override
  public void mouseReleased(MouseEvent e) {
    JLabel label = (JLabel)e.getSource();
    label.setBackground(themeColors.get("normal_knap_standard"));
  }
  @Override
  public void mouseClicked(MouseEvent e) {
  }
}
