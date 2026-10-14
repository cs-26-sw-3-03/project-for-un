import java.awt.*;
import javax.swing.*;

public class test {
  private static final Dimension initWindowSize = new Dimension(400, 300);

  public test() {
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        showWindow();
      }
    });
  }

  

  public static void showWindow() {
    JFrame frame = new JFrame("Window Name");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // frame.setSize(initWindowSize); // Does nothing ?? JFrame doesnt seem to have a size, JPanel works
    testLabel hello = new testLabel(new Dimension(500,500));
    frame.add(hello); //same as frame.getContentPane().add(test);
    // test.testLabel();
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
