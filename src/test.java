import java.awt.*;
import javax.swing.*;

public class test {
  private final Dimension initWindowSize = new Dimension(400, 300);

  public test() {
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() { showWindow(); }
    });
  }

  

  public static void showWindow() {
    JFrame frame = new JFrame("Window Name");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // frame.setSize(initWindowSize); // Does nothing ?? JFrame doesnt seem to have a size, JPanel works
    JPanel panel = new JPanel(new BorderLayout(0,0));
    // panel.setBackground(Color.RED);
    // panel.setPreferredSize(new Dimension(500,500));

    // panel.add(new testLabel(new Dimension(500,500)));
    // panel.add(new testLabel(new Dimension(500,500)));
    panel.add(new testImage(new Dimension(200,100)), BorderLayout.CENTER);
    panel.add(new testLabel(), BorderLayout.SOUTH);
    // panel.add(new testImage(new Dimension(500,500)));
    // panel.add(new testImage(new Dimension(500,500)));
    frame.add(panel);
    // testLabel hello = new testLabel(new Dimension(500,500));
    // testImage hello2 = new testImage(new Dimension(500,500));
    // frame.add(hello); //same as frame.getContentPane().add(test);
    // frame.add(hello2);
    // test.testLabel();
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
