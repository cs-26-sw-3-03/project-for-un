import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.*;

public class NewProductPage extends JPanel {
  private static HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme();

  public NewProductPage() {
    setLayout(new BorderLayout());
    setBackground(themeColors.get("primær_baggrund"));
    
    JPanel tempPanel = new JPanel(new GridLayout(10, 4));

    for (Attribute attribute : Attribute.values()) {
      JPanel subPanel = new JPanel(new GridLayout(1,2));
      JLabel aName = new JLabel(AttributeDescriber.DkNameOf(attribute));
      aName.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.GRAY));
      aName.setBackground(themeColors.get("accent_farve"));
      aName.setOpaque(true);

      JTextField aVal = new JTextField();
      JCheckBox aBool = new JCheckBox();
      aVal.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.GRAY));
      aVal.setOpaque(true);
      aBool.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.GRAY));
      aBool.setOpaque(true);

      Boolean isText = true;

      Object content;
      switch(AttributeDescriber.TypeOf(attribute)) {
        case Type.STRING:
          aVal = new JTextField();
          break;
        case Type.INT:
          aVal = new JTextField();
          ((AbstractDocument)aVal.getDocument()).setDocumentFilter(new IntDocumentFilter());
          break;
        case Type.DECIMAL:
          aVal = new JTextField();
          ((AbstractDocument)aVal.getDocument()).setDocumentFilter(new FloatDocumentFilter());
          break;
        case Type.BOOL:
          isText = false;
          aBool = new JCheckBox();
          aBool.setSelected(false);
          break;
        default:
          aVal = new JTextField();
          break;
      }


      subPanel.add(aName);
      subPanel.add(isText ? aVal : aBool);
      tempPanel.add(subPanel);
    }

    add(tempPanel);
  }
}
