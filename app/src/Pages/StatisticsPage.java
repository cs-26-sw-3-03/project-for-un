import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class StatisticsPage extends JPanel {
  
  private static HashMap<Attribute, JComponent> attributeObjects = new HashMap<>();
  
  public StatisticsPage() {
    setBackground(CustomTheme.GetColor("primær_baggrund"));
  }
}