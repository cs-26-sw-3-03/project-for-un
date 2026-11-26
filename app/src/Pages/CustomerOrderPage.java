import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class CustomerOrderPage extends JPanel {
  
  private static HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme();
  private static HashMap<Attribute, JComponent> attributeObjects = new HashMap<>();
  
  public CustomerOrderPage() {
    setBackground(CustomTheme.GetColor("primær_baggrund"));
  }
}