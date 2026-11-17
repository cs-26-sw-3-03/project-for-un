import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.table.TableColumn;
import javax.swing.BoxLayout;


public class HomePage extends JPanel {

  //Currently the dimenstions of the application are set as static values.
  //If there in the future was a want to make these dynamically changeable during use,
  //then this should be changed.

  //Width and Height are measurred in pixels on the screen.
  static int ROW_HEIGHT = 50;
  static int HEADER_BUTTONS_HEIGHT = 50;
  static int TABLE_BUTTONS_WIDTH = 50;
  static int SCROLLBAR_WIDTH = 10;
  static Dimension TABLE_BUTTONS_SIZE = new Dimension(TABLE_BUTTONS_WIDTH, ROW_HEIGHT);
  static int VIEWPORT_WIDTH = ((int)Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth())
                - TABLE_BUTTONS_WIDTH*2 //both detail and highlight button
                - SCROLLBAR_WIDTH
                );

  static int ID_COLUMN = 0;

  public HomePage() {
  setLayout(new BorderLayout(0,0));

    //----- dataTable part -----//
        
      //initialisation//
        //variables//

  int columnCount = 7;
  int rowCount = 50; //not final. will be set to the current amount of products, 
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

    JTable table = CreateDataTable(columnCount, rowCount);

          //content//    

    CreateHighlightButtons(highlightPanel, rowCount, table);

    CreateDetailButtons(detailPanel, rowCount);

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

    FilterPanel filterPanel = new FilterPanel();
    add(filterPanel, BorderLayout.NORTH);

    add(pagePanel, BorderLayout.CENTER);

  }
  
  private JTable CreateDataTable(int columnCount, int rowCount) {
    
    DefaultTableModel dataModel = new DefaultTableModel() {
      public int getColumnCount() { return columnCount; }
      public int getRowCount() { return rowCount;}
    };
    JTable table = new JTable(dataModel);
    /*
    for(int i = 0; i < columnCount; i++){
      
      int column = i;

      //table.getColumnModel().getColumn(column).setPreferredWidth(150); //test value, do ignore
    }*/

    table.setRowHeight(ROW_HEIGHT);  
    
    for(int i = 0; i < columnCount; i++) {
      for(int j = 0; j < rowCount; j++) {
        table.setValueAt(j+j*i,j,i);
      }
    }    

    return table;
  }

  private void CreateHighlightButtons(JPanel highlightPanel, int rowCount, JTable table){
    highlightPanel.setLayout(new BoxLayout(highlightPanel, BoxLayout.Y_AXIS));

    for(int i = 0; i < rowCount; i++){
    
      final int row = i;
    
      JButton highlightButton = new JButton("[  ]");
      
      if (HighlightManager.HighlightGetProduct(row) != null) {
        highlightButton.setText("[X]");
      }

      highlightButton.setPreferredSize(TABLE_BUTTONS_SIZE);
      highlightButton.setMaximumSize(TABLE_BUTTONS_SIZE);
      highlightButton.setSize(TABLE_BUTTONS_SIZE);

      highlightPanel.add(highlightButton);

      highlightButton.addMouseListener(new MouseListener()  {
        public void mouseEntered(MouseEvent e) {
          //highlightButton.setOpaque(true);
          //highlightButton.setBackground(Color.PINK);
        }
        public void mouseExited(MouseEvent e) {
          //highlightButton.setOpaque(false);
          //highlightButton.setBackground(Color.WHITE);
        }
        public void mousePressed(MouseEvent e) {
          //highlightButton.setBackground(Color.RED);
        }
        public void mouseReleased(MouseEvent e) {
          //highlightButton.setBackground(Color.WHITE);
        }
        public void mouseClicked(MouseEvent e) {
          //placeholder
          //System.out.println(row);

          if(highlightButton.getText() == "[  ]") {
            highlightButton.setText("[X]");

            HighlightManager.HighlightAddProduct(row, table.getValueAt(row,ID_COLUMN));

            HighlightManager.HighlightProductsDebug();

          } else {
            highlightButton.setText("[  ]");

            HighlightManager.HighlightRemoveProduct(row);

            HighlightManager.HighlightProductsDebug();

          }

        }
      });
    }
  }

  private void CreateDetailButtons(JPanel detailPanel, int rowCount){
    detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));

    for(int i = 0; i < rowCount; i++){
    
      final int row = i;
    
      JButton detailButton = new JButton("#");

      detailButton.setPreferredSize(TABLE_BUTTONS_SIZE);
      detailButton.setMaximumSize(TABLE_BUTTONS_SIZE);
      detailButton.setSize(TABLE_BUTTONS_SIZE);

      detailPanel.add(detailButton);

      detailButton.addMouseListener(new MouseListener()  {
        public void mouseEntered(MouseEvent e) {
          //detailButton.setOpaque(true);
          //detailButton.setBackground(Color.PINK);
        }
        public void mouseExited(MouseEvent e) {
          //detailButton.setOpaque(false);
          //detailButton.setBackground(Color.WHITE);
        }
        public void mousePressed(MouseEvent e) {
          //detailButton.setBackground(Color.RED);
        }
        public void mouseReleased(MouseEvent e) {
          //detailButton.setBackground(Color.WHITE);
        }
        public void mouseClicked(MouseEvent e) {
          //placeholder
          System.out.println(row);

        }
      });
    }
  }

  private void CreateHeaderPart(JPanel tableHeaderPanel, int columnCount){
    tableHeaderPanel.setLayout(new BoxLayout(tableHeaderPanel, BoxLayout.X_AXIS));

    tableHeaderPanel.add(new Box(BoxLayout.X_AXIS).createHorizontalStrut(TABLE_BUTTONS_WIDTH));
  
    //placeholder for invisioned implementation//
    //will be replaced with panels including: column name and a filter arrow//

    for(int i = 0; i < columnCount; i++){
    
      final int column = i;
      
      Dimension headerButtonsSize = new Dimension(VIEWPORT_WIDTH/columnCount, HEADER_BUTTONS_HEIGHT);

      JButton headerButton = new JButton("#");

      headerButton.setPreferredSize(headerButtonsSize);
      headerButton.setMaximumSize(headerButtonsSize);
      //headerButton.setMinimumSize(headerButtonsSize);
      //headerButton.setSize(headerButtonsSize);

      tableHeaderPanel.add(headerButton);
    }

    tableHeaderPanel.add(new Box(BoxLayout.X_AXIS).createHorizontalStrut(SCROLLBAR_WIDTH));
  }

  private void CreateSearchPanel(JPanel searchPanel){
    searchPanel.add(new TextField("test",1), BorderLayout.NORTH);
  }

}


