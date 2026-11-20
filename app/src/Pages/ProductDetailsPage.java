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

    if (int_id == 0) {
      JLabel info = new JLabel("Tryk på '#' på 'Home' eller 'Selected Products' siden for at se dets detaljer", SwingConstants.CENTER);
      info.setFont(new Font("Times New Roman", Font.BOLD, 36));
      add(info);
      return;
    }

    HashMap<Attribute, Object> attributeMap = new HashMap<>();


    // Get Product from db
    Product product;
    try {
      product = ProductQuerier.GetProduct(int_id);
    } catch(java.sql.SQLException e) {
      System.out.println(e);
      return;
    }

    // Fill attributeMap with attribute values of the product
    for (Attribute attribute : Attribute.values()) {
      Object value = product.GetObject(attribute);
      attributeMap.put(attribute, value);
    }

    JPanel tempPanel = new JPanel(new GridLayout(10, 4, 6, 6));
    tempPanel.setBorder(new javax.swing.border.EmptyBorder(60,60,60,60));

    for (Attribute attribute : Attribute.values()) {
      JPanel subPanel = new JPanel(new GridLayout(1,2, 10, 10));
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
        case Type.STRING: case Type.INT: case Type.DECIMAL:
          content = attributeMap.get(attribute);

          if (attributeType == Type.STRING) {
            field.setText(content == null ? "" : content.toString());
            break;
          }
          field = (attributeType == Type.INT ? new IntTextField() : new FloatTextField());
          field.setText(content == null ? "0" : content.toString());

          break;

        case Type.BOOL:
          isText = false;
          Boolean boolContent = (Boolean)attributeMap.get(attribute);
          checkBox.setSelected(boolContent == null ? false : boolContent);
          break;

        case null:
          throw new NullPointerException("Attribute type is null");
        default:
          throw new IllegalArgumentException("Attribute type doesnt match string/int/decimal/bool");
      }
      field.setFont(new Font("Times New Roman", Font.PLAIN, 24));
      subPanel.add(aName);
      subPanel.add(isText ? field : checkBox);
      tempPanel.add(subPanel);

      attributeObjects.put(attribute, isText ? field : checkBox);
    }

    JLabel confirm = new JLabel("Save Changes");
<<<<<<< HEAD
    confirm.setFont(new Font("Times New Roman", Font.PLAIN, 24));


    // Handle updating db with new details
=======
>>>>>>> bef43b9 (linked HomePage and ProductDetails with the productDetailsButton)
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
