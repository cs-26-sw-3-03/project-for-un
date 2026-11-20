import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel; import java.awt.event.*;
import java.util.Arrays;
import javax.swing.table.TableColumn;
import javax.swing.BoxLayout;
import java.util.EnumSet;


public class HomePage extends JPanel {
  static HomePage INSTANCE;
  
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
  
  
  private JPanel headerPanel;
  private JPanel mainPanel;
  
  private JPanel searchPanel;
  private JPanel tableHeaderPanel; //Top part of table with attribute names
  private JPanel tablePanel;
  private JScrollPane tableScrollPane;
  private JPanel highlightPanel;
  private JPanel detailPanel;
  

  public void OpenFilterPanel() {
    if(FilterPanel.GetInstance() != null)
      headerPanel.remove(FilterPanel.GetInstance());
    headerPanel.add(FilterPanel.MakeOrGetInstance(), BorderLayout.NORTH);
    Main.window.revalidate();
    Main.window.repaint();
  }
  
  public void ReloadFilterPanel() {
    if(FilterPanel.GetInstance() != null)
      headerPanel.remove(FilterPanel.GetInstance());
    headerPanel.add(FilterPanel.MakeInstance(), BorderLayout.NORTH);
    Main.window.revalidate();
    Main.window.repaint();
  }
  
  public void RemoveFilterPanel() {
    if(FilterPanel.GetInstance() != null) {
      headerPanel.remove(FilterPanel.GetInstance());
      FilterPanel.DeleteInstance();
      Main.window.revalidate();
      Main.window.repaint();
    }
  }
  
  public void ToggleFilterPanel() {
    if(FilterPanel.GetInstance() != null)
      RemoveFilterPanel();
    else
      ReloadFilterPanel();
  }
  
  
  public static HomePage MakeInstance() {
    return INSTANCE = new HomePage();
  }
    
  public static void DeleteInstance() {
    INSTANCE = null;
  }
    
  public static HomePage GetInstance() {
    return INSTANCE;
  }

  private HomePage() {
    setLayout(new BorderLayout());

    //----- dataTable part -----//
        
      //initialisation//
        //variables//

            //or some amount that updates automatically when the user scrolls down.

            //panels//

//  tree visualisation of HomePage panels:
//                        -> searchPanel
//         -> headerPanel -> filterPanel
// HomePage                                              -> highlightPanel
//        L-> mainPanel -> tableScrollPane -> tablePanel -> table
//                     |                                 -> detailPanel
//                     L-> tableHeaderPanel

    headerPanel = new JPanel(new BorderLayout());

    searchPanel = new JPanel(new BorderLayout());
    tableHeaderPanel = new JPanel(new BorderLayout());


    mainPanel = new JPanel(new BorderLayout());    

    tablePanel = new JPanel(new BorderLayout());
    highlightPanel = new JPanel(new BorderLayout());
    detailPanel = new JPanel(new BorderLayout());

    JScrollPane tableScrollpane = new JScrollPane(tablePanel);
    
    JPanel highlightPanel = new JPanel(new BorderLayout());

    JPanel detailPanel = new JPanel(new BorderLayout());

          //dataTable//

    JTable table = CreateDataTable();
    CreateHighlightButtons(highlightPanel, rowCount, table);
    CreateDetailButtons(detailPanel, rowCount, table);
    
    tablePanel.add(highlightPanel, BorderLayout.WEST);
    tablePanel.add(table, BorderLayout.CENTER);
    tablePanel.add(detailPanel, BorderLayout.EAST);
    tableScrollPane = new JScrollPane(tablePanel);
    tableScrollPane.getVerticalScrollBar().setUnitIncrement(12); 
    
    tableHeaderPanel = CreateTableHeader(columnCount);
    
    mainPanel.add(tableHeaderPanel, BorderLayout.NORTH);
    mainPanel.add(tableScrollPane, BorderLayout.CENTER);
    
    
          //Header panel//
    CreateSearchPanel(searchPanel);
          
    headerPanel.add(searchPanel, BorderLayout.CENTER);
    OpenFilterPanel();
    
    add(mainPanel, BorderLayout.CENTER);
    add(headerPanel, BorderLayout.NORTH);
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
    QueryConfig config = FilterManager.GetInstance().GetCurrentPreset();
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
        val = val == null ? "" : val;
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
    
      JLabel detailButton = new JLabel("#", SwingConstants.CENTER);

      detailButton.setPreferredSize(TABLE_BUTTONS_SIZE);
      detailButton.setMaximumSize(TABLE_BUTTONS_SIZE);
      detailButton.setSize(TABLE_BUTTONS_SIZE);

      detailButton.addMouseListener(new LabelButtonListener(detailButton) {
        @Override
        public void mouseClicked(MouseEvent e) {
          String stringId = table.getValueAt(row, 0).toString();
          int intId = IdLinker.idMap.get(stringId);

          PageSwitcher.switchPage(Pages.PRODUCT_DETAILS, intId);
        }
      });

      detailPanel.add(detailButton);
    }
  }

  private JPanel CreateTableHeader(int columnCount) {
    JPanel tableHeaderPanel = new JPanel(new BorderLayout()) ;
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
  return tableHeaderPanel;
  }

  private void CreateSearchPanel(JPanel searchPanel){ //placeholder implementation
    searchPanel.add(new TextField("test",1), BorderLayout.NORTH);
  }
}
