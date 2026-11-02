import javax.swing.*;
import java.awt.Color;

public class HomePage extends JPanel {
  public HomePage() {
    // setPreferredSize(500,500);
    setBackground(Color.RED);
    
    JTable table = new JTable(10,7);

    add(table);
  }
}
