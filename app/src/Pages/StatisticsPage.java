import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class StatisticsPage extends JPanel {
  
  private static HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme();
  private static HashMap<Attribute, JComponent> attributeObjects = new HashMap<>();
  
  public StatisticsPage() {
    setBackground(CustomTheme.GetColor("primær_baggrund"));
  }
}