import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel; import java.awt.event.*;
import java.util.Arrays;
import javax.swing.table.TableColumn;
import javax.swing.BoxLayout;
import java.util.EnumSet;


public class HomePage extends JPanel {
  
  //Currently the dimenstions of the application are set as static values.
  //If there in the future was a want to make these dynamically changeable during use,
  //then this should be changed.

  //Width and Height are measurred in pixels on the screen.
  static final int ROW_HEIGHT = 50;
  static final int HEADER_BUTTONS_HEIGHT = 50;
  static final int TABLE_BUTTONS_WIDTH = 50;
  static final int SCROLLBAR_WIDTH = 10;
  static final Dimension TABLE_BUTTONS_SIZE = new Dimension(TABLE_BUTTONS_WIDTH, ROW_HEIGHT);
  static final int VIEWPORT_WIDTH = ((int)Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth())
                - TABLE_BUTTONS_WIDTH*2 //both detail and highlight button
                - SCROLLBAR_WIDTH
                );

  static final int ID_COLUMN = 0;
  
  private FilterPanel filterPanel;

  public void AddFilterPanel() {
    RemoveFilterPanel();
    filterPanel = new FilterPanel();
    add(filterPanel, BorderLayout.NORTH);
    Main.window.revalidate();
    Main.window.repaint();
  }
  
  public void RemoveFilterPanel() {
    if(filterPanel != null)
      remove(filterPanel);
  }

  private static int columnCount = 7;
  private static int rowCount = 20; //not final. will be set to the current amount of products, 

  public HomePage() {
    setLayout(new BorderLayout());

    //----- dataTable part -----//
        
      //initialisation//
        //variables//

            //or some amount that updates automatically when the user scrolls down.

            //panels//

//  tree visualisation of HomePage panels:
//                                -> searchPanel
//                  -> headerPanel
//      -> pagePanel                   -> highlightPanel
//  HomePage       L-> tableScrollpane -> tablePanel 
//      -> filterPanel -> ...          -> detailPanel
//  

    JPanel pagePanel = new JPanel(new BorderLayout());

    JPanel headerPanel = new JPanel(new BorderLayout());

    JPanel searchPanel = new JPanel(new BorderLayout());

    JPanel tableHeaderPanel = new JPanel(new BorderLayout());

    JPanel tablePanel = new JPanel(new BorderLayout());

    JScrollPane tableScrollpane = new JScrollPane(tablePanel);
    
    JPanel highlightPanel = new JPanel(new BorderLayout());

    JPanel detailPanel = new JPanel(new BorderLayout());

          //dataTable//

    JTable table = CreateDataTable();

          //content//    

    CreateHighlightButtons(highlightPanel, rowCount, table);

    CreateDetailButtons(detailPanel, rowCount, table);

    CreateHeaderPart(headerPanel, columnCount);

    CreateSearchPanel(searchPanel);

          //meshing//
          
    headerPanel.add(searchPanel, BorderLayout.NORTH);
    headerPanel.add(tableHeaderPanel, BorderLayout.CENTER);
    
    tablePanel.add(table, BorderLayout.CENTER);
    tablePanel.add(highlightPanel, BorderLayout.WEST);
    tablePanel.add(detailPanel, BorderLayout.EAST);

    pagePanel.add(tableScrollpane, BorderLayout.CENTER);
    pagePanel.add(headerPanel, BorderLayout.NORTH);

      //----- building -----//

    AddFilterPanel();

    add(pagePanel, BorderLayout.CENTER);
  }
  
  private JTable CreateDataTable() {
    try {
      int productCount = ProductQuerier.CountProducts();
      rowCount = productCount;
    } catch (java.sql.SQLException e) {
      System.out.println(e);
    }
    
    // Making table instance
    DefaultTableModel dataModel = new DefaultTableModel() {
      public int getColumnCount() { return columnCount; }
      public int getRowCount() { return rowCount;}
    };
    JTable table = new JTable(dataModel);
    table.setRowHeight(ROW_HEIGHT);

    // Getting products from db
    QueryConfig config = new QueryConfig(0, 20);
    EnumSet<Attribute> attributeSet = EnumSet.of(
      Attribute.ID,
      Attribute.NAME,
      Attribute.SIZE,
      Attribute.COUNT,
      Attribute.SELL_PRICE,
      Attribute.BUY_PRICE,
      Attribute.COMMENTS
    );
    config.ShowCollection(attributeSet);

    Product[] gottenProducts;
    try {
      gottenProducts = ProductQuerier.GetProducts(config);
    } catch (java.sql.SQLException e) {
      System.out.println(e);
      return null;
    }

    for (Product p : gottenProducts) {
      if (p == null) continue;

      IdLinker.idMap.put(p.GetObject(Attribute.ID).toString(), p.GetId());
    }

    int column = 0;
    int row = 0;
    for (Attribute attribute : attributeSet) {
      for (Product product : gottenProducts) {
        if (product == null) continue;
        Object val = product.GetObject(attribute);
        val = val == null ? "some value" : val;
        table.setValueAt(val.toString(), row, column);
        row++;
      }
      row = 0;
      column++;
    }
    

    return table;
  }


  private void CreateHighlightButtons(JPanel highlightPanel, int rowCount, JTable table){
    highlightPanel.setLayout(new BoxLayout(highlightPanel, BoxLayout.Y_AXIS));

    for(int i = 0; i < rowCount; i++){
    
      final int row = i; 
    
      JLabel highlightButton = new JLabel("", SwingConstants.CENTER);
      highlightButton.setText(HighlightManager.HighlightGetProduct(row) != null ? "[X]" : "[  ]");
      

      highlightButton.setPreferredSize(TABLE_BUTTONS_SIZE);
      highlightButton.setMaximumSize(TABLE_BUTTONS_SIZE);
      highlightButton.setSize(TABLE_BUTTONS_SIZE);

      highlightPanel.add(highlightButton);

      highlightButton.addMouseListener(new LabelButtonListener(highlightButton)  {
        @Override
        public void mouseClicked(MouseEvent e) {
          // Add to HighlightManager
          if (HighlightManager.HighlightGetProduct(row) != null)
            HighlightManager.HighlightRemoveProduct(row);
          else
            HighlightManager.HighlightAddProduct(row, table.getValueAt(row,ID_COLUMN));

          // Set Text
          highlightButton.setText(HighlightManager.HighlightGetProduct(row) != null ? "[X]" : "[  ]");

          // Debug?!?! ₍^ ⸝⸝> ·̫ <⸝⸝ ^₎  👅👅
          HighlightManager.HighlightProductsDebug();

        }
      });
    }
  }

  private void CreateDetailButtons(JPanel detailPanel, int rowCount, JTable table){
    detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));

    for(int i = 0; i < rowCount; i++){
    
      final int row = i;
    
<<<<<<< HEAD
      JLabel detailButton = new JLabel("#", SwingConstants.CENTER);
=======
      JLabel detailButton = new JLabel("#");
>>>>>>> bef43b9 (linked HomePage and ProductDetails with the productDetailsButton)

      detailButton.setPreferredSize(TABLE_BUTTONS_SIZE);
      detailButton.setMaximumSize(TABLE_BUTTONS_SIZE);
      detailButton.setSize(TABLE_BUTTONS_SIZE);

<<<<<<< HEAD
      detailButton.addMouseListener(new LabelButtonListener(detailButton) {
        @Override
        public void mouseClicked(MouseEvent e) {
          String stringId = table.getValueAt(row, 0).toString();
          int intId = IdLinker.idMap.get(stringId);

          PageSwitcher.switchPage(Pages.PRODUCT_DETAILS, intId);
=======
      detailPanel.add(detailButton);

      detailButton.addMouseListener(new LabelButtonListener(detailButton) {
        @Override
        public void mouseClicked(MouseEvent e) {
          System.out.println(row);

          PageSwitcher.switchPage(Pages.PRODUCT_DETAILS, row);
>>>>>>> bef43b9 (linked HomePage and ProductDetails with the productDetailsButton)
        }
      });

      detailPanel.add(detailButton);
    }
  }

  private void CreateHeaderPart(JPanel tableHeaderPanel, int columnCount){
    tableHeaderPanel.setLayout(new BoxLayout(tableHeaderPanel, BoxLayout.X_AXIS));

    tableHeaderPanel.add(new Box(BoxLayout.X_AXIS).createHorizontalStrut(TABLE_BUTTONS_WIDTH));
  
    //placeholder for invisioned implementation//
    //will be replaced with panels including: column name and a filter arrow//

    for(int i = 0; i < columnCount; i++){
    
      final int column = i; //for later use
      
      Dimension headerButtonsSize = new Dimension(VIEWPORT_WIDTH/columnCount, HEADER_BUTTONS_HEIGHT);

      JButton headerButton = new JButton("#");

      headerButton.setPreferredSize(headerButtonsSize);
      headerButton.setMaximumSize(headerButtonsSize);

      tableHeaderPanel.add(headerButton);
    }

    tableHeaderPanel.add(new Box(BoxLayout.X_AXIS).createHorizontalStrut(SCROLLBAR_WIDTH));
  }

  private void CreateSearchPanel(JPanel searchPanel){ //placeholder implementation
    searchPanel.add(new TextField("test",1), BorderLayout.NORTH);
  }
}
