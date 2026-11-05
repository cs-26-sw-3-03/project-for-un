import java.awt.*;
import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Main {
  public static JFrame window;
  public static JPanel mainPanel;
  public static JLayeredPane paneOfContent;
  public static Pages currentPage = Pages.HOME_PAGE;
  public static JPanel contextPanel;
  public static JPanel popupPanel;

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
	    public void run() { showWindow(); }
    });
  }

  private static void showWindow() {
    //Window Setup
    window = new JFrame("Window Name");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setLocationRelativeTo(null);
    EventQueue.invokeLater(() -> {window.setExtendedState(JFrame.MAXIMIZED_BOTH);});


    // Activate Shortcut Listener
    KeyboardShortcuts.Install();

    // Main Panel
    mainPanel = new JPanel(new BorderLayout(0,0));
    
    // JLayeredPane with the content with contextPanel and popupPanel
    paneOfContent = new JLayeredPane();
    
    // Initializing popup- & contextPanel, children of paneOfContent
    contextPanel  = new JPanel(new BorderLayout());
    popupPanel    = new JPanel(new BorderLayout());
    popupPanel.setBackground(new Color(0,0,0,25));

    // Add Default content to contextPanel
    HomePage homePage = new HomePage();
    contextPanel.add(homePage, BorderLayout.CENTER);

    // Popup- and contextPanel added to parent paneOfContent
    paneOfContent.add(contextPanel, JLayeredPane.DEFAULT_LAYER);
    paneOfContent.add(popupPanel  , JLayeredPane.POPUP_LAYER);

    // paneOfContent added to parent mainPanel
    mainPanel.add(paneOfContent, BorderLayout.CENTER);

    // Buttons for switching page
    PageButtons pageButtons = new PageButtons();
    mainPanel.add(pageButtons, BorderLayout.SOUTH);

    //Draw to window
    window.add(mainPanel);
    window.pack();

    // Resize main
    window.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
      contextPanel.setBounds(0, 0,
        mainPanel.getWidth(),
        mainPanel.getHeight() - pageButtons.getHeight()
        );
      popupPanel.setBounds(0, 0,
        mainPanel.getWidth(),
        mainPanel.getHeight() - pageButtons.getHeight()-1
        );
      }
    });
    EventQueue.invokeLater(() -> {
     contextPanel.setBounds(0, 0,
       mainPanel.getWidth(),
       mainPanel.getHeight() - pageButtons.getHeight()
       );
     popupPanel.setBounds(0, 0,
       mainPanel.getWidth(),
       mainPanel.getHeight() - pageButtons.getHeight()-1
       );
    });

    window.setVisible(true);
  }
}
