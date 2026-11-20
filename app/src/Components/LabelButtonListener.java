import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;

public class LabelButtonListener implements MouseListener {
  private static final HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme();
  private JLabel parent;

  public LabelButtonListener(JLabel parent) {
    this.parent = parent;
    parent.setOpaque(true);
    parent.setBackground(themeColors.get("normal_knap_standard"));
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    parent.setBackground(themeColors.get("normal_knap_fremhævet"));
  }
  @Override
  public void mouseExited(MouseEvent e) {
    parent.setBackground(themeColors.get("normal_knap_standard"));
  }
  @Override
  public void mousePressed(MouseEvent e) {
    parent.setBackground(themeColors.get("normal_knap_tryk"));
  }
  @Override
  public void mouseReleased(MouseEvent e) {
    parent.setBackground(themeColors.get("normal_knap_standard"));
  }
  @Override
  public void mouseClicked(MouseEvent e) {
  }
}
