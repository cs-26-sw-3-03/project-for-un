import java.awt.*;
import javax.swing.*;
import java.util.Objects;

public class Main {
  public static JFrame window;
  public static Pages currentPage = Pages.HOME_PAGE;

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
	    public void run() { showWindow(); }
    });
  }


  public static void showWindow() {
    window = new JFrame("Window Name");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setLocationRelativeTo(null);
    window.setExtendedState(JFrame.MAXIMIZED_BOTH);
    JPanel panel = new JPanel(new BorderLayout(0,0));
    
    panel.add(new PageButtons(), BorderLayout.SOUTH);

    panel.add(new HomePage(), BorderLayout.CENTER);

    window.add(panel);
    window.pack();
    window.setVisible(true);
  }


}
