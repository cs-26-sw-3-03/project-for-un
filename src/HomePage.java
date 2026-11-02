import java.awt.*;
import javax.swing.*;

import java.awt.BorderLayout;

public class HomePage extends JPanel {
  public HomePage() {
    Dimension panelD = new Dimension(1000,1000);
    setPreferredSize(panelD);
    setBackground(Color.RED);
    
    int rowSize = 20;
    int columnSize = 7;

    JTable table = new JTable(rowSize,columnSize);

    for(int i = 0; i < rowSize; i++) {
        for(int j = 0; j < columnSize; j++) {
            table.setValueAt(j+columnSize*i,i,j);
        }
    }


    add(table);
  }
}
