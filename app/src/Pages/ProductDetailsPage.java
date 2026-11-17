import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

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
          aVal = new JTextField(content == null ? "0" : content.toString());
          break;
        case Type.INT:
          content = attributeMap.get(attribute);
          aVal = new JTextField(content == null ? "0" : content.toString());
          ((AbstractDocument)aVal.getDocument()).setDocumentFilter(new IntDocumentFilter());
          break;
        case Type.DECIMAL:
          content = attributeMap.get(attribute);
          aVal = new JTextField(content == null ? "0" : content.toString());
          ((AbstractDocument)aVal.getDocument()).setDocumentFilter(new FloatDocumentFilter());
          break;
        case Type.BOOL:
          isText = false;
          Boolean boolContent = (Boolean)attributeMap.get(attribute);
          aBool = new JCheckBox();
          aBool.setSelected(boolContent == null ? false : boolContent);
          break;
        default:
          aVal = new JTextField();
          break;
      }
      subPanel.add(aName);
      attributeObjects.put(attribute, isText ? aVal : aBool);
      subPanel.add(isText ? aVal : aBool);
      tempPanel.add(subPanel);
    }

    System.out.println(attributeObjects);

    JLabel confirm = new JLabel("Save Changes");
    confirm.addMouseListener(new LabelButtonListener() {
      @Override
      public void mouseClicked(MouseEvent e) {

        AttributeValueMap attributeValueMap = new AttributeValueMap();
        // Spoink
        for (Attribute attribute : Attribute.values()) {
          JComponent obj = attributeObjects.get(attribute);

          Object productValue = product.GetObject(attribute);

          switch(AttributeDescriber.TypeOf(attribute)) {
            case Type.STRING: 
              String objText = ((JTextComponent)obj).getText()
              if (productValue == null) {
                productValue = (String)"";
                break;
              }

              Object value = (Object)objText;
              if (!productValue.equals(value))
                attributeValueMap.Put(attribute, value);
              break;
            case Type.INT:
              String objText = ((JTextComponent)obj).getText()
              if (productValue == null) {
                productValue = 0;
                break;
              }

              value = Integer.parseInt(objText);
              if (!productValue.equals(value))
                attributeValueMap.Put(attribute, value);
              break;
            case Type.DECIMAL:
              if (productValue == null)
                break;

              value = Double.parseDouble(((JTextComponent)obj).getText());
              if (!productValue.equals(value))
                attributeValueMap.Put(attribute, value);
              break;
            case Type.BOOL:
                productValue = product.GetObject(attribute);
                if (productValue == null)
                  break;
                value = (Object)((AbstractButton)obj).isSelected();
                if (!product.GetObject(attribute).equals(value))
                  attributeValueMap.Put(attribute, value);
              break;
          }
        }
      }
    });
    tempPanel.add(confirm);

    add(tempPanel);
  }
}
