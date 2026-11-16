import javax.swing.JPanel;
import java.awt.Color;

public class ProductDetailsPage extends JPanel {
  private static HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme();

  public ProductDetailsPage() {
    setBackground(themeColors.get("primær_baggrund"));

    for (Attribute attribute : Attribute.values()) {
    }
  }
}
