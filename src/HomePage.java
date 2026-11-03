import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;

public class HomePage extends JPanel {
    public HomePage() {

        setLayout(new BorderLayout(0,50));

        JPanel filterPanel = new JPanel(new BorderLayout());

        JPanel pagePanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());

        JPanel tablePanel = new JPanel(new BorderLayout());

        JPanel highlightPanel = new JPanel(new BorderLayout());

        JPanel detailPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());

        //TextField tfSearch = new TextField("search...",1);

        Dimension panelD = new Dimension(500,500);
        setPreferredSize(panelD);
        setBackground(Color.RED);

        int rowSize = 50;
        int columnSize = 7;

        /*
        TableModel myModel = createMyTableModel();
        TableRowSorter sorter = new TableRowSorter(myModel);

        JTable table = new JTable(rowSize,columnSize);
        JScrollPane scrollpane = new JScrollPane(table);
        */

        DefaultTableModel dataModel = new DefaultTableModel() {
            public int getColumnCount() { return columnSize; }
            public int getRowCount() { return rowSize;}
            //public Object getValueAt(int row, int col) { return (row + columnSize*col); }
        };
        JTable table = new JTable(dataModel);
        JScrollPane scrollpane = new JScrollPane(tablePanel);
        //table.setRowSorter(new TableRowSorter(dataModel);

        //table.setAutoCreateRowSorter(true);

        //sets the header values for the table
        table.getColumnModel().getColumn(2).setHeaderValue("test");


        //sets the values in the table using a nested for loop
        for(int i = 0; i < columnSize; i++) {
            for(int j = 0; j < rowSize; j++) {
                table.setValueAt(j+columnSize*i,j,i);
            }
        }
        

        filterPanel.add(new JButton("test"));
        highlightPanel.add(new JButton("test"));
        detailPanel.add(new JButton("test"));
        headerPanel.add(new JButton("test"), BorderLayout.CENTER);
        searchPanel.add(new TextField("test",1));

        headerPanel.add(searchPanel, BorderLayout.NORTH);

        pagePanel.add(scrollpane, BorderLayout.CENTER);
        pagePanel.add(headerPanel, BorderLayout.NORTH);

        tablePanel.add(table, BorderLayout.CENTER);
        tablePanel.add(highlightPanel, BorderLayout.EAST);
        tablePanel.add(detailPanel, BorderLayout.WEST);

        add(pagePanel, BorderLayout.CENTER);
        add(filterPanel, BorderLayout.NORTH);

        table.setValueAt( "test",0,0);

  }
}


