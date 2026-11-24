import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class SupplierOrderPage extends JPanel {
    
  private static HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme();
  private static HashMap<Attribute, JComponent> attributeObjects = new HashMap<>();
  
  public SupplierOrderPage() {
    setLayout(new BorderLayout());
    setBackground(themeColors.get("primær_baggrund"));
}
}

