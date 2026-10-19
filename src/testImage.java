import java.awt.*;
import javax.swing.*;

public class testImage extends JPanel {
  public testImage(Dimension d) {
    ImageIcon image = new ImageIcon("bleh.png");
    // setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // JPanel innerPanel = new JPanel(new GridLayout(2,2));
    // JPanel innerPanel = new JPanel();
    // innerPanel.setPreferredSize(d);

    JLabel lt = new JLabel(image);
    lt.setPreferredSize(d);

    add(lt);
    // innerPanel.add(lt);
    // innerPanel.add(lt);
    // innerPanel.add(lt);
    // innerPanel.add(new Button("1"));
    // innerPanel.add(new Button("2"));
    // innerPanel.add(new Button("3"));
    // innerPanel.add(new Button("4"));
    // innerPanel.add(new Button("5"));
    // innerPanel.add(new Button("6"));
    // add(innerPanel);
  }

  public testImage() {
    this(new Dimension(400,500));
  }
}
