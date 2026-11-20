import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;

public class FilterLabelButtonListener implements MouseListener {
  private static HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme();
  private Boolean active = false;
  private JLabel parent;

  public FilterLabelButtonListener(JLabel parent, Boolean active) {
    this.parent = parent;
    parent.setOpaque(true);
    this.active = active;
    parent.setBackground(themeColors.get(active == true ? "filter_knap_aktiv_standard" : "filter_knap_inaktiv_standard"));
  }

  public FilterLabelButtonListener(JLabel parent) {
    this(parent, false);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    parent.setBackground(themeColors.get(active == true ? "filter_knap_aktiv_fremhævet" : "filter_knap_inaktiv_fremhævet"));
  }
  @Override
  public void mouseExited(MouseEvent e) {
    parent.setBackground(themeColors.get(active == true ? "filter_knap_aktiv_standard" : "filter_knap_inaktiv_standard"));
  }
  @Override
  public void mousePressed(MouseEvent e) {
    parent.setBackground(themeColors.get(active == true ? "filter_knap_aktiv_tryk" : "filter_knap_inaktiv_tryk"));
  }
  @Override
  public void mouseReleased(MouseEvent e) {
    active = !active;
    parent.setBackground(themeColors.get(active == true ? "filter_knap_aktiv_standard" : "filter_knap_inaktiv_standard"));
  }
  @Override
  public void mouseClicked(MouseEvent e) {
  }
}
