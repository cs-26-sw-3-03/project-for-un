import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;

public class ButtonListener implements MouseListener {
  private JComponent parent;
  private String colorSet;
  private ClickFunction onClick;

  public interface ClickFunction {
    void run(MouseEvent e);
  }

  public ButtonListener(JComponent parent, ClickFunction onClick) {
    this(parent, "primær", onClick);
  }
  
  public ButtonListener(JComponent parent, String colorSet, ClickFunction onClick) {
    this.parent = parent;
    this.colorSet = colorSet;
    this.onClick = onClick;
    parent.setOpaque(true);
    parent.setBackground(CustomTheme.GetColor(colorSet + "_knap_standard"));
  }

  public void mouseEntered(MouseEvent e) {
    parent.setBackground(CustomTheme.GetColor(colorSet + "_knap_fremhævet"));
  }

  public void mouseExited(MouseEvent e) {
    parent.setBackground(CustomTheme.GetColor(colorSet + "_knap_standard"));
  }

  public void mousePressed(MouseEvent e) {
    parent.setBackground(CustomTheme.GetColor(colorSet + "_knap_tryk"));
  }

  public void mouseReleased(MouseEvent e) {
    parent.setBackground(CustomTheme.GetColor(colorSet + "_knap_standard"));
  }

  public void mouseClicked(MouseEvent e) {
    onClick.run(e);
  }
}
