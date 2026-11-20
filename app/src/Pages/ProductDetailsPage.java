import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.*;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;

public class ProductDetailsPage extends JPanel {
  private static HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme();
  private static HashMap<Attribute, JComponent> attributeObjects = new HashMap<>();

  public ProductDetailsPage(int int_id) {
    setLayout(new BorderLayout());
    setBackground(themeColors.get("primær_baggrund"));
    
    HashMap<Attribute, Object> attributeMap = new HashMap<>();

    Product product;
    try {
      product = ProductQuerier.GetProduct(int_id);
    } catch(java.sql.SQLException e) {
      System.out.println(e);
      return;
    }

    System.out.println(product);

    for (Attribute attribute : Attribute.values()) {
      Object value = product.GetObject(attribute);
      attributeMap.put(attribute, value);
    }

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
          content = attributeMap.get(attribute);
          aVal = new JTextField(content == null ? "" : content.toString());
          break;
        case Type.INT:
          content = attributeMap.get(attribute);
          aVal = new IntTextField();
          aVal.setText(content == null ? "0" : content.toString());
          break;
        case Type.DECIMAL:
          content = attributeMap.get(attribute);
          aVal = new FloatTextField();
          aVal.setText(content == null ? "0" : content.toString());
          break;
        case Type.BOOL:
          isText = false;
          Boolean boolContent = (Boolean)attributeMap.get(attribute);
          aBool = new JCheckBox();
          aBool.setSelected(boolContent == null ? false : boolContent);
          break;
        case null:
          throw new NullPointerException("Attribute type is null");
        default:
          throw new IllegalArgumentException("Attribute type doesnt match string/int/decimal/bool");
      }
      subPanel.add(aName);
      attributeObjects.put(attribute, isText ? aVal : aBool);
      subPanel.add(isText ? aVal : aBool);
      tempPanel.add(subPanel);
    }

    JLabel confirm = new JLabel("Save Changes");
    confirm.addMouseListener(new LabelButtonListener(confirm) {
      @Override
      public void mouseClicked(MouseEvent e) {

        AttributeValueMap attributeValueMap = new AttributeValueMap();
        // Spoink
        for (Attribute attribute : Attribute.values()) {
          JComponent obj = attributeObjects.get(attribute);

          Object productValue = product.GetObject(attribute);

          String objText;

          switch(AttributeDescriber.TypeOf(attribute)) {
            case Type.STRING: 
              objText = ((JTextComponent)obj).getText();
              if (objText.equals(""))
                continue;

              if (productValue == null|| !productValue.equals(objText))
                attributeValueMap.Put(attribute, objText);
              break;

            case Type.INT:
              objText = ((JTextComponent)obj).getText();
              if (objText.equals(""))
                continue;

              Object value = Integer.parseInt(objText);
              if (productValue == null|| !productValue.equals(value))
                attributeValueMap.Put(attribute, value);
              break;

            case Type.DECIMAL:
              objText = ((JTextComponent)obj).getText();
              objText = objText.replace(",", ".");
              if (objText.equals(""))
                continue;

              value = new BigDecimal(objText);
              if (productValue == null|| !productValue.equals(value))
                attributeValueMap.Put(attribute, value);
              break;

            case Type.BOOL:
              value = ((AbstractButton)obj).isSelected();
              if (productValue == null|| !productValue.equals(value) || productValue == null)
                attributeValueMap.Put(attribute, value);
              break;
          }
        }
        System.out.println("Changed attributes: " + attributeValueMap.Values());
        try {
          ProductQuerier.SetAttributes(int_id, attributeValueMap);
        } catch (java.sql.SQLException sqlE) {
          System.out.println(sqlE);
          // Show popup to warn user
        }
      }
    });
    tempPanel.add(confirm);

    add(tempPanel);
  }
}
