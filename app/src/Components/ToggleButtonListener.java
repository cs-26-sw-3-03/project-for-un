import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;

public class ToggleButtonListener implements MouseListener {
  private boolean active;
  private JComponent parent;
  private String colorSet;
  private ClickFunction onClick;

  public interface ClickFunction {
    void run(MouseEvent e, boolean active);
  }

  public ToggleButtonListener(JComponent parent, ClickFunction onClick) {
    this(parent, "primær", false, onClick);
  }
  
  public ToggleButtonListener(JComponent parent, Boolean active, ClickFunction onClick) {
    this(parent, "primær", active, onClick);
  }
  
  public ToggleButtonListener(JComponent parent, String colorSet, ClickFunction onClick) {
    this(parent, colorSet, false, onClick);
  }
  
  public ToggleButtonListener(JComponent parent, String colorSet, Boolean active, ClickFunction onClick) {
    this.parent = parent;
    this.colorSet = colorSet;
    this.active = active;
    this.onClick = onClick;
    parent.setOpaque(true);
    parent.setBackground(CustomTheme.GetColor(colorSet + (active == true ? "_skifteknap_aktiv_standard" : "_skifteknap_inaktiv_standard")));
  }


  public void mouseEntered(MouseEvent e) {
    parent.setBackground(CustomTheme.GetColor(colorSet + (active == true ? "_skifteknap_aktiv_fremhævet" : "_skifteknap_inaktiv_fremhævet")));
  }

  public void mouseExited(MouseEvent e) {
    parent.setBackground(CustomTheme.GetColor(colorSet + (active == true ? "_skifteknap_aktiv_standard" : "_skifteknap_inaktiv_standard")));
  }

  public void mousePressed(MouseEvent e) {
    parent.setBackground(CustomTheme.GetColor(colorSet + (active == true ? "_skifteknap_aktiv_tryk" : "_skifteknap_inaktiv_tryk")));
  }

  public void mouseReleased(MouseEvent e) {
    active = !active;
    parent.setBackground(CustomTheme.GetColor(colorSet + (active == true ? "_skifteknap_aktiv_standard" : "_skifteknap_inaktiv_standard")));
  }

  public void mouseClicked(MouseEvent e) {
    onClick.run(e, active);
  }
}
