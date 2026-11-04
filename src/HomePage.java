import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Arrays;



public class HomePage extends JPanel {
    static int FILTER_AMOUNT = 20;

    public HomePage() {

        setLayout(new BorderLayout(0,20));

        /*
        Dimension panelD = new Dimension(500,500);
        setPreferredSize(panelD);
        setBackground(Color.RED);
        */

            //----- Filter part -----// 
        
                //initialisation//
                    //panels//

        JPanel dropDownPanel = new JPanel(new BorderLayout());

        JPanel presetPanel = new JPanel(new BorderLayout());

        JPanel filterPanel = new JPanel(new BorderLayout());

        JPanel filterButtonsPanel = new JPanel(new BorderLayout());
        
        JPanel filterGridPanel = new JPanel(new BorderLayout());

        //JScrollPane filterScrollPane = new JScrollPane(filterGridPanel);
            
            //filterScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            //filterScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);


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

        JPanel tablePanel = new JPanel(new BorderLayout());

        JPanel highlightPanel = new JPanel(new BorderLayout());

        JPanel detailPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());

        JScrollPane tableScrollpane = new JScrollPane(tablePanel);

                //initialisation//
                    //integers//

        int rowSize = 50;
        int columnSize = 7;

                //initialisation//
                    //dataTable//

        DefaultTableModel dataModel = new DefaultTableModel() {
            public int getColumnCount() { return columnSize; }
            public int getRowCount() { return rowSize;}
            //public Object getValueAt(int row, int col) { return (row + columnSize*col); }
        };
        JTable table = new JTable(dataModel);

                //initialisation//
                    //meshing//

        headerPanel.add(searchPanel, BorderLayout.NORTH);

        pagePanel.add(tableScrollpane, BorderLayout.CENTER);
        pagePanel.add(headerPanel, BorderLayout.NORTH);

        tablePanel.add(table, BorderLayout.CENTER);
        tablePanel.add(highlightPanel, BorderLayout.EAST);
        tablePanel.add(detailPanel, BorderLayout.WEST);

                //initialisation//
                    //buttons//
        
        highlightPanel.add(new JButton("test"));
        detailPanel.add(new JButton("test"));
        headerPanel.add(new JButton("test"), BorderLayout.CENTER);
        searchPanel.add(new TextField("test",1));

                //initialisation//
                    //building//

        add(pagePanel, BorderLayout.CENTER);
        add(dropDownPanel, BorderLayout.NORTH);

            //----- testing -----//

        //sets the values in the table using a nested for loop
        for(int i = 0; i < columnSize; i++) {
            for(int j = 0; j < rowSize; j++) {
                table.setValueAt(j+columnSize*i,j,i);
            }
        }

        table.setValueAt( "test",0,0);

  }
}


