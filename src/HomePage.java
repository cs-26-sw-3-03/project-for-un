import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;



public class HomePage extends JPanel {
    static int FILTER_AMOUNT = 10;

    public HomePage() {

        setLayout(new BorderLayout(0,50));

        /*
        Dimension panelD = new Dimension(500,500);
        setPreferredSize(panelD);
        setBackground(Color.RED);
        */

        JPanel filterPanel = new JPanel(new BorderLayout());

        JPanel presetPanel = new JPanel(new BorderLayout());

        JPanel filterShowPanel = new JPanel(new BorderLayout());

        filterPanel.add(presetPanel, BorderLayout.NORTH);

        filterPanel.add(filterShowPanel, BorderLayout.CENTER);

        filterShowPanel.setLayout(new GridLayout(2,FILTER_AMOUNT,5,5));

        JPanel pagePanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());

        JPanel tablePanel = new JPanel(new BorderLayout());

        JPanel highlightPanel = new JPanel(new BorderLayout());

        JPanel detailPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());

        int rowSize = 50;
        int columnSize = 7;

        DefaultTableModel dataModel = new DefaultTableModel() {
            public int getColumnCount() { return columnSize; }
            public int getRowCount() { return rowSize;}
            //public Object getValueAt(int row, int col) { return (row + columnSize*col); }
        };
        JTable table = new JTable(dataModel);
        JScrollPane scrollpane = new JScrollPane(tablePanel);

        //sets the values in the table using a nested for loop
        for(int i = 0; i < columnSize; i++) {
            for(int j = 0; j < rowSize; j++) {
                table.setValueAt(j+columnSize*i,j,i);
            }
        }
        

        presetPanel.add(new JButton("test"));
        }
        for(int i = 0; i < FILTER_AMOUNT; i++){
            filterShowPanel.add(new JButton("name"));
            filterShowPanel.add(new TextField("parameter"));
        }

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


