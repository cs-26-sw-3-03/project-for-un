import java.awt.*;
import javax.swing.*;

public class testPage extends JPanel {
  public testPage(Dimension d) {
    setBackground(Color.RED);

    add(new JLabel(new ImageIcon("bleh.png")));
  }

  public testPage() {
  this(new Dimension(400,500));
  }
}
