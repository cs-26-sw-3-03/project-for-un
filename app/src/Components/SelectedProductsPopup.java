import java.awt.*;
import javax.swing.*;
import javax.swing.SpringLayout;

public class SelectedProductsPopup extends JPanel {
  
  final static int PRODUCTS_MARGIN = 10;
  final static int FIRST_PRODUCT_X_AXIS = 50;
  final static int FIRST_PRODUCT_Y_AXIS = 50;
  final static int PRODUCT_TEXTFIELD_WIDTH = 15;
  
  public SelectedProductsPopup() {
    setBackground(new Color(0,0,0,120));
    setLayout(new BorderLayout());

    // setOpaque(true);

    
    SpringLayout productsLayout = new SpringLayout();
    JPanel productsPanel = new JPanel(); 

    productsPanel.setLayout(productsLayout);
    productsPanel.setBackground(new Color(0,0,0,120));

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(0,0,0,120));

    CreateProductLabels(productsPanel, productsLayout);

    JButton cancelButton = new JButton("Cancel");
    JButton confirmButton = new JButton("Confirm");

    buttonPanel.add(cancelButton);
    buttonPanel.add(confirmButton);

    add(productsPanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

  }

  private void CreateProductLabels(JPanel productsPanel, SpringLayout productsLayout){

    int number = 0;

    if(HighlightManager.highlightsHash.size() < 1) {
      
      System.out.println("reeeeee");

      return;
    }

    JTextField[] productsArr = new JTextField[HighlightManager.highlightsHash.size()];
    String[] productIds = HighlightManager.GetHighlightProductsString();
    
    productsArr[number] = new JTextField(productIds[number], PRODUCT_TEXTFIELD_WIDTH);

    productsLayout.putConstraint(SpringLayout.WEST, productsArr[number], FIRST_PRODUCT_X_AXIS, SpringLayout.WEST, productsPanel);
    productsLayout.putConstraint(SpringLayout.NORTH, productsArr[number], FIRST_PRODUCT_Y_AXIS, SpringLayout.NORTH, productsPanel);

    productsPanel.add(productsArr[number]);

    for (number = 1; number < HighlightManager.highlightsHash.size(); number++) {
      
      productsArr[number] = new JTextField(productIds[number], PRODUCT_TEXTFIELD_WIDTH);

      productsLayout.putConstraint(SpringLayout.WEST, productsArr[number], FIRST_PRODUCT_X_AXIS, SpringLayout.WEST, productsPanel);
      productsLayout.putConstraint(SpringLayout.NORTH, productsArr[number], PRODUCTS_MARGIN, SpringLayout.SOUTH, productsArr[number-1]);
      
      productsPanel.add(productsArr[number]);
    }

    
  }

}
