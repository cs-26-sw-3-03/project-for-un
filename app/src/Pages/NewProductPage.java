import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.*;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;

public class NewProductPage extends JPanel {
  private static HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme();
  private static HashMap<Attribute, JComponent> attributeObjects = new HashMap<>();

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
      }


      subPanel.add(aName);
      attributeObjects.put(attribute, isText ? aVal : aBool);
      subPanel.add(isText ? aVal : aBool);
      tempPanel.add(subPanel);
    }

    JLabel confirm = new JLabel("Make Product");
    confirm.addMouseListener(new LabelButtonListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        AttributeValueMap attributeValueMap = new AttributeValueMap();
        // Spoink
        for (Attribute attribute : Attribute.values()) {
          JComponent obj = attributeObjects.get(attribute);

          switch(AttributeDescriber.TypeOf(attribute)) {
            case Type.STRING: 
              String objText = ((JTextComponent)obj).getText();
              if (objText.equals(""))
                  continue;

              attributeValueMap.Put(attribute, objText);
              break;
            case Type.INT:
              objText = ((JTextComponent)obj).getText();
              if (objText.equals(""))
                  continue;

              Object value = Integer.parseInt(objText);
              attributeValueMap.Put(attribute, value);
              break;
            case Type.DECIMAL:
              objText = ((JTextComponent)obj).getText();
              if (objText.equals(""))
                  continue;
              objText = objText.replace(",", ".");

              value = new BigDecimal(objText);
              attributeValueMap.Put(attribute, value);
              break;
            case Type.BOOL:
              value = (Boolean)((AbstractButton)obj).isSelected();
              attributeValueMap.Put(attribute, value);
              break;
          }
        }
        Product product;
        System.out.println("Changed attributes: " + attributeValueMap.Values());
        try {
          product = ProductQuerier.MakeProduct(attributeValueMap);
        } catch (IllegalArgumentException argE) {
          System.out.println(argE);
          return;
          // Show popup to warn user
        } catch (java.sql.SQLException sqlE) {
          sqlE.printStackTrace(System.out);
          return;
          // Show popup to warn user
        }
        System.out.println(product);
      }
    });
    tempPanel.add(confirm);

    add(tempPanel);
  }
}
