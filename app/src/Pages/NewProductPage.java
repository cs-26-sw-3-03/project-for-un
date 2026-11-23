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
    
    JPanel tempPanel = new JPanel(new GridLayout(10, 4, 6, 6));
    tempPanel.setBorder(new javax.swing.border.EmptyBorder(60,60,60,60));

    for (Attribute attribute : Attribute.values()) {
      JPanel subPanel = new JPanel(new GridLayout(1, 2, 10, 10));
      JLabel aName = new JLabel(AttributeDescriber.DkNameOf(attribute));
      aName.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.GRAY));
      aName.setBackground(themeColors.get("accent_farve"));
      aName.setOpaque(true);
      aName.setFont(new Font("Times New Roman", Font.PLAIN, 24));

      JTextField field = new JTextField();
      JCheckBox checkBox = new JCheckBox();
      field.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.GRAY));
      field.setOpaque(true);
      checkBox.setBorder(BorderFactory.createMatteBorder(2,2,2,2,Color.GRAY));
      checkBox.setOpaque(true);

      Boolean isText = true;

      Object content;
      Type attributeType = AttributeDescriber.TypeOf(attribute);
      switch(attributeType) {
        case Type.STRING:
          field = new JTextField();
          break;
        case Type.INT: case Type.DECIMAL:
          field = (attributeType == Type.INT ? new IntTextField() : new FloatTextField());
          field.setText("0");
          break;
        case Type.BOOL:
          isText = false;
          checkBox = new JCheckBox();
          checkBox.setSelected(false);
          break;
        case null:
          throw new NullPointerException("Attribute type is null");
        default:
          throw new IllegalArgumentException("Attribute type doesnt match string/int/decimal/bool");
      }
      field.setFont(new Font("Times New Roman", Font.PLAIN, 24));

      subPanel.add(aName);
      attributeObjects.put(attribute, isText ? field : checkBox);
      subPanel.add(isText ? field : checkBox);
      tempPanel.add(subPanel);
    }

    JLabel confirm = new JLabel("Make Product");
    confirm.setFont(new Font("Times New Roman", Font.PLAIN, 24));

    confirm.addMouseListener(new LabelButtonListener(confirm) {
      @Override
      public void mouseClicked(MouseEvent e) {
        AttributeValueMap attributeValueMap = new AttributeValueMap();
        // Spoink
        for (Attribute attribute : Attribute.values()) {
          JComponent obj = attributeObjects.get(attribute);

          String objText;
          switch(AttributeDescriber.TypeOf(attribute)) {
            case Type.STRING: 
              objText = ((JTextComponent)obj).getText();
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
