import java.awt.*;
import javax.swing.*;
import javax.swing.SpringLayout;
import java.awt.Font;
import java.net.URL;
import java.net.URI;

public class SelectedProductsPopup extends JPanel {
  

  final static int FIRST_PRODUCT_X_AXIS = 50;
  final static int FIRST_PRODUCT_Y_AXIS = 60;
  final static int PRODUCT_TEXTFIELD_COLUMNS = 15;
  final static int PRODUCT_AMOUNT_TEXTFIELD_COLUMNS = 2;
  final static int PRODUCT_TO_AMOUNT_DISTANCE = 5;
  final static int FONT_SIZE = 30;
  final static String PRODUCT_STARTING_AMOUNT = "1";
  final static ImageIcon INCREASE = new ImageIcon("Images/Increase.png");
  final static ImageIcon DECREASE = new ImageIcon("Images/Decrease.png");
  final static Font FONT = new Font("times new roman", Font.PLAIN, FONT_SIZE);
  final static int PRODUCTS_MARGIN = INCREASE.getIconHeight() + DECREASE.getIconHeight() + 10;

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

    CreateProductLabels(productsPanel, productsLayout, HighlightManager.highlightsHash.size());

    JButton cancelButton = new JButton("Cancel");
    JButton confirmButton = new JButton("Confirm");

    buttonPanel.add(cancelButton);
    buttonPanel.add(confirmButton);

    add(productsPanel, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

  }

  private void CreateProductLabels(JPanel productsPanel, SpringLayout productsLayout, Integer amount){

    int number = 0;

    if(amount < 1) {
      
      System.out.println("reeeeee");

      return;
    }

    JTextField[] productsArr = new JTextField[amount];
    String[] productIds = HighlightManager.GetHighlightProductsString();
    
    productsArr[number] = new JTextField(productIds[number], PRODUCT_TEXTFIELD_COLUMNS);

    productsArr[number].setEditable(false);
    productsArr[number].setFont(FONT);

    productsLayout.putConstraint(SpringLayout.WEST, productsArr[number], FIRST_PRODUCT_X_AXIS, SpringLayout.WEST, productsPanel);
    productsLayout.putConstraint(SpringLayout.NORTH, productsArr[number], FIRST_PRODUCT_Y_AXIS, SpringLayout.NORTH, productsPanel);

    productsPanel.add(productsArr[number]);

    for (number = 1; number < amount; number++) {
      
      productsArr[number] = new JTextField(productIds[number], PRODUCT_TEXTFIELD_COLUMNS);
      
      productsArr[number].setEditable(false);
      productsArr[number].setFont(FONT);

      productsLayout.putConstraint(SpringLayout.WEST, productsArr[number], FIRST_PRODUCT_X_AXIS, SpringLayout.WEST, productsPanel);
      productsLayout.putConstraint(SpringLayout.NORTH, productsArr[number], PRODUCTS_MARGIN, SpringLayout.SOUTH, productsArr[number-1]);
      
      productsPanel.add(productsArr[number]);
    }

    CreateProductAmounts(productsPanel, productsLayout, productsArr, amount);
    
  }

  private void CreateProductAmounts(JPanel productsPanel, SpringLayout productsLayout, JTextField[] productsArr, Integer amount){

    JLabel[] productsAmountIncreaseArr = new JLabel[amount];    //  A
    IntTextField[] productsAmountArr = new IntTextField[amount];// [ ] //visualisation
    JLabel[] productsAmountDecreaseArr = new JLabel[amount];    //  V
        
    for(int number = 0; number < amount; number++){

      productsAmountArr[number] = new IntTextField(); 

      productsAmountArr[number].setColumns(PRODUCT_AMOUNT_TEXTFIELD_COLUMNS);
      productsAmountArr[number].setText(PRODUCT_STARTING_AMOUNT);
      productsAmountArr[number].setFont(FONT);

      productsLayout.putConstraint(SpringLayout.WEST, productsAmountArr[number], PRODUCT_TO_AMOUNT_DISTANCE, SpringLayout.EAST, productsArr[number]);
      productsLayout.putConstraint(SpringLayout.NORTH, productsAmountArr[number], 0, SpringLayout.NORTH, productsArr[number]); // the 0 is used to keep the textfield for the amount,
                                                                                                                               // paralel with the textfield for the id.
      productsAmountIncreaseArr[number] = new JLabel(INCREASE); 

      productsLayout.putConstraint(SpringLayout.WEST, productsAmountIncreaseArr[number], PRODUCT_TO_AMOUNT_DISTANCE, SpringLayout.EAST, productsArr[number]);
      productsLayout.putConstraint(SpringLayout.SOUTH, productsAmountIncreaseArr[number], 2, SpringLayout.NORTH, productsAmountArr[number]); 
                                                                                                                               
      productsAmountDecreaseArr[number] = new JLabel(DECREASE); 

      productsLayout.putConstraint(SpringLayout.WEST, productsAmountDecreaseArr[number], PRODUCT_TO_AMOUNT_DISTANCE, SpringLayout.EAST, productsArr[number]);
      productsLayout.putConstraint(SpringLayout.NORTH, productsAmountDecreaseArr[number], 2, SpringLayout.SOUTH, productsAmountArr[number]); 
      
      productsPanel.add(productsAmountArr[number]);
      productsPanel.add(productsAmountIncreaseArr[number]);
      productsPanel.add(productsAmountDecreaseArr[number]);

    }

    
    
  }
}
