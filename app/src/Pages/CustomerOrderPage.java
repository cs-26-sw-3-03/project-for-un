import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class CustomerOrderPage extends JPanel {
  
  private static HashMap<Attribute, JComponent> attributeObjects = new HashMap<>();
  
  public CustomerOrderPage() {
    setBackground(CustomTheme.GetColor("primær_baggrund"));
  }
}