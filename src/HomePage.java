import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;

public class HomePage extends JPanel {
    public HomePage() {

        //setLayout(new BorderLayout(50,50));

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
        JScrollPane scrollpane = new JScrollPane(table);
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
        



        add(scrollpane);

        
        table.setValueAt("test",0,0);

  }
}


