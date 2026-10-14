import javax.swing.*;
import java.awt.*;

public class testLabel extends JPanel {
  public testLabel(Dimension d) {
    System.out.println("Hey, d: ");
    System.out.println(d);
    ImageIcon image = new ImageIcon("bleh.png");

    // JPanel innerPanel = new JPanel(SwingConstants.WEST);
    JPanel innerPanel = new JPanel(new GridLayout(3,2));
    // innerPanel.setPreferredSize(new Dimension(500,400));
    innerPanel.setPreferredSize(d);

    JLabel lt = new JLabel(image);//, SwingConstants.LEFT);
    JLabel lt2 = new JLabel(image);//, SwingConstants.RIGHT);
    // li.setPreferredSize(d);

    JLabel li = new JLabel("Hello, Goober!");//, SwingConstants.LEFT);
    // lt.setFont(new Font("Serif", Font.PLAIN, 50));
    // lt.setForeground(Color.RED);
    lt.setPreferredSize(new Dimension(100,250));
    // lt.setPreferredSize(d);

    innerPanel.add(lt);
    innerPanel.add(lt2);
    innerPanel.add(li);
    li = new JLabel("Goob, Hello!");//, SwingConstants.LEFT);
    innerPanel.add(li);
    // innerPanel.add(new Button("1"));
    // innerPanel.add(new Button("2"));
    // innerPanel.add(new Button("3"));
    // innerPanel.add(new Button("4"));
    innerPanel.add(new Button("5"));
    innerPanel.add(new Button("6"));
    add(innerPanel);
  }

  public testLabel() {
    // DEFAULT:
    System.out.println("Yo");
    this(new Dimension(400, 300));
  }
}
