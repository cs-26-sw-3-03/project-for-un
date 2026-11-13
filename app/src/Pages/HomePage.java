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

    public HomePage() {
        setLayout(new BorderLayout(0,20));

            //----- dataTable part -----//
                
                //initialisation//
                    //variables//

        int columnCount = 7;
        int rowCount = 50; //not final. will be set to the current amount of products, 
                           //or some amount that updates automatically when the user scrolls down.

                           //panels//

//  tree visualisation of HomePage panels:
//                                    -> searchPanel
//                      -> headerPanel
//          -> pagePanel                                -> highlightPanel
//  HomePage           L-> tableScrollpane -> tablePanel 
//          -> filterPanel -> ...                       -> detailPanel
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

                    //buttons//      
                        //highlightPanel//    

        CreateHighlightButtons(highlightPanel, rowCount);

                        //detailPanel//

        CreateDetailButtons(detailPanel, rowCount);

                        //headerPanel//

        CreateHeaderPart(headerPanel, columnCount);

                        //searchPanel//

        CreateSearchPanel(searchPanel);

                    //meshing//

        pagePanel.add(tableScrollpane, BorderLayout.CENTER);
        pagePanel.add(headerPanel, BorderLayout.NORTH);

        headerPanel.add(searchPanel, BorderLayout.NORTH);
        headerPanel.add(tableHeaderPanel, BorderLayout.CENTER);

        tablePanel.add(table, BorderLayout.CENTER);
        tablePanel.add(highlightPanel, BorderLayout.WEST);
        tablePanel.add(detailPanel, BorderLayout.EAST);

            //----- building -----//

        add(pagePanel, BorderLayout.CENTER);

        FilterPanel filterPanel = new FilterPanel();
        add(filterPanel, BorderLayout.NORTH);

            //----- testing -----//

        //sets the values in the table using a nested for loop

        
        for(int i = 0; i < columnCount; i++) {
            for(int j = 0; j < rowCount; j++) {
                table.setValueAt("1234567890$$%&//££😊",j,i);
            }
        }      
              
        //table.setValueAt( "test",0,0);
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
        
        return table;
    }

    private void CreateHighlightButtons(JPanel highlightPanel, int rowCount){
        highlightPanel.setLayout(new BoxLayout(highlightPanel, BoxLayout.Y_AXIS));

        JButton[] highlightButtons = new JButton[rowCount];

        for(int i = 0; i < rowCount; i++){
        
            int row = i;
        
            highlightButtons[row] = new JButton("[  ]");

            highlightButtons[row].setPreferredSize(TABLE_BUTTONS_SIZE);
            highlightButtons[row].setMaximumSize(TABLE_BUTTONS_SIZE);
            highlightButtons[row].setSize(TABLE_BUTTONS_SIZE);

            highlightPanel.add(highlightButtons[row]);

            highlightButtons[row].addMouseListener(new MouseListener()  {
                public void mouseEntered(MouseEvent e) {
                    //highlightButtons[row].setOpaque(true);
                    //highlightButtons[row].setBackground(Color.PINK);
                }
                public void mouseExited(MouseEvent e) {
                    //highlightButtons[row].setOpaque(false);
                    //highlightButtons[row].setBackground(Color.WHITE);
                }
                public void mousePressed(MouseEvent e) {
                    //highlightButtons[row].setBackground(Color.RED);
                }
                public void mouseReleased(MouseEvent e) {
                    //highlightButtons[row].setBackground(Color.WHITE);
                }
                public void mouseClicked(MouseEvent e) {
                    //placeholder
                    System.out.println(row);

                    if(highlightButtons[row].getText() == "[  ]") {
                        highlightButtons[row].setText("[X]");
                    } else {
                        highlightButtons[row].setText("[  ]");
                    }

                }
            });
        }
    }

    private void CreateDetailButtons(JPanel detailPanel, int rowCount){
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));

        JButton[] detailButtons = new JButton[rowCount];

        for(int i = 0; i < rowCount; i++){
        
            int row = i;
        
            detailButtons[row] = new JButton("#");

            detailButtons[row].setPreferredSize(TABLE_BUTTONS_SIZE);
            detailButtons[row].setMaximumSize(TABLE_BUTTONS_SIZE);
            detailButtons[row].setSize(TABLE_BUTTONS_SIZE);

            detailPanel.add(detailButtons[row]);

            detailButtons[row].addMouseListener(new MouseListener()  {
                public void mouseEntered(MouseEvent e) {
                    //detailButtons[row].setOpaque(true);
                    //detailButtons[row].setBackground(Color.PINK);
                }
                public void mouseExited(MouseEvent e) {
                    //detailButtons[row].setOpaque(false);
                    //detailButtons[row].setBackground(Color.WHITE);
                }
                public void mousePressed(MouseEvent e) {
                    //detailButtons[row].setBackground(Color.RED);
                }
                public void mouseReleased(MouseEvent e) {
                    //detailButtons[row].setBackground(Color.WHITE);
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

        JButton[] headerButtons = new JButton[columnCount];

        for(int i = 0; i < columnCount; i++){
        
            int column = i;
            
            Dimension headerButtonsSize = new Dimension(VIEWPORT_WIDTH/columnCount, HEADER_BUTTONS_HEIGHT);

            headerButtons[column] = new JButton("#");

            headerButtons[column].setPreferredSize(headerButtonsSize);
            headerButtons[column].setMaximumSize(headerButtonsSize);
            //headerButtons[column].setMinimumSize(headerButtonsSize);
            //headerButtons[column].setSize(headerButtonsSize);

            tableHeaderPanel.add(headerButtons[column]);
        }

        tableHeaderPanel.add(new Box(BoxLayout.X_AXIS).createHorizontalStrut(SCROLLBAR_WIDTH));
    }

    private void CreateSearchPanel(JPanel searchPanel){
        searchPanel.add(new TextField("test",1), BorderLayout.NORTH);
    }

}


