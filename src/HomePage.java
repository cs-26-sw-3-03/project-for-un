import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.table.TableColumn;


public class HomePage extends JPanel {
    static int FILTER_AMOUNT = 20;

    public HomePage() {
        setLayout(new BorderLayout(0,20));

            //----- Filter part -----// 
        
                //initialisation//
                    //panels//

        JPanel dropDownPanel = new JPanel(new BorderLayout());

        JPanel presetPanel = new JPanel(new BorderLayout());

        JPanel filterPanel = new JPanel(new BorderLayout());

        JPanel filterButtonsPanel = new JPanel(new BorderLayout());
        
        JPanel filterGridPanel = new JPanel(new BorderLayout());

                //initialisation//
                    //meshing//

        dropDownPanel.add(presetPanel, BorderLayout.NORTH);

        dropDownPanel.add(filterPanel, BorderLayout.CENTER);

        filterPanel.add(filterGridPanel, BorderLayout.CENTER);

        filterPanel.add(filterButtonsPanel, BorderLayout.EAST);

        filterGridPanel.setLayout(new GridLayout(FILTER_AMOUNT/5,5,5,5));

                //initialisation//
                    //buttons//

        presetPanel.add(new JButton("preset"), BorderLayout.WEST);

        filterButtonsPanel.add(new JButton("save filter"),BorderLayout.NORTH);
        filterButtonsPanel.add(new JButton("reset filter"),BorderLayout.SOUTH);
        
                    //filters//

        JLabel[] filterLabels = new JLabel[FILTER_AMOUNT];
        TextField[] filterParams = new TextField[FILTER_AMOUNT];

        for (int i = 0; i < FILTER_AMOUNT; i++){
            int number = i;

            filterLabels[number] = new JLabel("filter_"+number);
            filterParams[number] = new TextField("parameter_"+number, 15);

            filterLabels[number].setPreferredSize(new Dimension(40,25));
            filterParams[number].setColumns(15);

            filterLabels[number].addMouseListener(new MouseListener()  {
                public void mouseEntered(MouseEvent e) {
                    filterLabels[number].setOpaque(true);
                    filterLabels[number].setBackground(Color.PINK);
                }
                public void mouseExited(MouseEvent e) {
                    filterLabels[number].setOpaque(false);
                    filterLabels[number].setBackground(Color.WHITE);
                }
                public void mousePressed(MouseEvent e) {
                    filterLabels[number].setBackground(Color.RED);
                }
                public void mouseReleased(MouseEvent e) {
                    filterLabels[number].setBackground(Color.WHITE);
                }
                public void mouseClicked(MouseEvent e) {
                    //placeholder
                    System.out.println(number);
                }
            });

            filterGridPanel.add(filterLabels[number], BorderLayout.CENTER);
            filterGridPanel.add(filterParams[number], BorderLayout.EAST);
        }

            //----- dataTable part -----//
                
                //initialisation//
                    //panels//

        JPanel pagePanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());

        JPanel tableHeaderPanel = new JPanel(new BorderLayout());

        JPanel tablePanel = new JPanel(new BorderLayout());

        JScrollPane tableScrollpane = new JScrollPane(tablePanel);
        
        JPanel highlightPanel = new JPanel(new BorderLayout());

        JPanel detailPanel = new JPanel(new BorderLayout());

                //initialisation//
                    //variables//

        int rowCount = 50;
        int columnCount = 7;
        int rowHeight = 50;
        int headerButtonsHeight = 50;
        int tableButtonsWidth = 50;
        int scrollbarWidth = 10;
        Dimension tableButtonsSize = new Dimension(tableButtonsWidth, rowHeight);
        int viewportWidth = ((int)Math.round(Toolkit.getDefaultToolkit().getScreenSize().getWidth())
                            - tableButtonsWidth*2 //both detail and highlight button
                            - scrollbarWidth
                            );


                //initialisation//
                    //dataTable//

        DefaultTableModel dataModel = new DefaultTableModel() {
            public int getColumnCount() { return columnCount; }
            public int getRowCount() { return rowCount;}
        };
        JTable table = new JTable(dataModel);

        for(int i = 0; i < columnCount; i++){
            
            int column = i;

            table.getColumnModel().getColumn(column).setPreferredWidth(150);
        }

        table.setRowHeight(rowHeight);

                //initialisation//
                    //meshing//

        pagePanel.add(tableScrollpane, BorderLayout.CENTER);
        pagePanel.add(headerPanel, BorderLayout.NORTH);

        headerPanel.add(searchPanel, BorderLayout.NORTH);
        headerPanel.add(tableHeaderPanel, BorderLayout.CENTER);

        tablePanel.add(table, BorderLayout.CENTER);
        tablePanel.add(highlightPanel, BorderLayout.WEST);
        tablePanel.add(detailPanel, BorderLayout.EAST);

                //initialisation//
                    //buttons//
        
                        //highlightPanel//    

        highlightPanel.setLayout(new BoxLayout(highlightPanel, 1));

        JButton[] highlightButtons = new JButton[rowCount];

        for(int i = 0; i < rowCount; i++){
        
            int row = i;
        
            highlightButtons[row] = new JButton("[  ]");

            highlightButtons[row].setPreferredSize(tableButtonsSize);
            highlightButtons[row].setMaximumSize(tableButtonsSize);
            highlightButtons[row].setSize(tableButtonsSize);

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

                        //detailPanel//

        detailPanel.setLayout(new BoxLayout(detailPanel, 1));

        JButton[] detailButtons = new JButton[rowCount];

        for(int i = 0; i < rowCount; i++){
        
            int row = i;
        
            detailButtons[row] = new JButton("#");

            detailButtons[row].setPreferredSize(tableButtonsSize);
            detailButtons[row].setMaximumSize(tableButtonsSize);
            detailButtons[row].setSize(tableButtonsSize);

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

                        //headerPanel//

        tableHeaderPanel.setLayout(new BoxLayout(tableHeaderPanel, 0));

        tableHeaderPanel.add(new Box(0).createHorizontalStrut(tableButtonsWidth));
    
        //placeholder for invisioned implementation//
        //will be replaced with panels including: column name and a filter arrow//

        JButton[] headerButtons = new JButton[columnCount];

        for(int i = 0; i < columnCount; i++){
        
            int column = i;
            
            Dimension headerButtonsSize = new Dimension(viewportWidth/columnCount, headerButtonsHeight);

            headerButtons[column] = new JButton("#");

            headerButtons[column].setPreferredSize(headerButtonsSize);
            headerButtons[column].setMaximumSize(headerButtonsSize);
            //headerButtons[column].setMinimumSize(headerButtonsSize);
            //headerButtons[column].setSize(headerButtonsSize);

            tableHeaderPanel.add(headerButtons[column]);
        }

        tableHeaderPanel.add(new Box(0).createHorizontalStrut(scrollbarWidth));

        //placeholder for searchPanel//

        searchPanel.add(new TextField("test",1), BorderLayout.NORTH);

            //----- building -----//

        add(pagePanel, BorderLayout.CENTER);
        add(dropDownPanel, BorderLayout.NORTH);

            //----- testing -----//

        //sets the values in the table using a nested for loop

        
        for(int i = 0; i < columnCount; i++) {
            for(int j = 0; j < rowCount; j++) {
                table.setValueAt("1234567890$$%&//££😊",j,i);
            }
        }
      
        //table.setValueAt( "test",0,0);
  }
}


