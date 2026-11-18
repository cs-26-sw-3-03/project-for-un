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

    add(productsPanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

  }

  private void CreateProductLabels(JPanel productsPanel, SpringLayout productsLayout){

    if(HighlightManager.highlightsHash.size() < 1) return;

    JTextField[] productsArr = new JTextField[HighlightManager.highlightsHash.size()];

    productsArr[0] = new JTextField(PRODUCT_TEXTFIELD_WIDTH);

    productsLayout.putConstraint(SpringLayout.WEST, productsArr[0], FIRST_PRODUCT_X_AXIS, SpringLayout.WEST, productsPanel);
    productsLayout.putConstraint(SpringLayout.NORTH, productsArr[0], FIRST_PRODUCT_Y_AXIS, SpringLayout.NORTH, productsPanel);

    for (int i = 1; i > HighlightManager.highlightsHash.size(); i++) {
      
      productsArr[i] = new JTextField(PRODUCT_TEXTFIELD_WIDTH);

      productsLayout.putConstraint(SpringLayout.NORTH, productsArr[i], PRODUCTS_MARGIN, SpringLayout.SOUTH, productsArr[i-1]);
      
    }

    
  }

}
