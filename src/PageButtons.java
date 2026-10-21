import java.awt.*;
import javax.swing.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class PageButtons extends JPanel {
  public PageButtons(Consumer<String> pageSwitcher) {
    setLayout(new GridLayout(1,7,0,0));
    setBackground(Color.RED);

    JButton[] buttons = {
      new JButton("HOME PAGE"),
      new JButton("PRODUCER ORDERS"),
      new JButton("CUSTOMER ORDERS"),
      new JButton("STATISTICS"),
      new JButton("PRODUCT DETAILS"),
      new JButton("NEW PRODUCT"),
      new JButton("SETTINGS")
    };

    for (JButton b : buttons) {
      b.setPreferredSize(new Dimension(100,50));

      String name = b.getText().replace(' ', '_');
      
      b.addActionListener(e -> pageSwitcher.accept(name));

      add(b);
    }
  }
}
