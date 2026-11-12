import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Main {
  public static JFrame window;
  public static JPanel mainPanel;
  public static JLayeredPane paneOfContent;
  public static Pages currentPage = Pages.HOME_PAGE;
  public static JPanel contextPanel;
  public static JPanel popupPanel;

  public static void main(String[] args) {
    Settings.GetSettingsFromSettingsfile();
    CustomTheme.ReadThemesFromThemefile();
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
    popupPanel.setVisible(false);
    
    // TEMPORARY FIX TO NOT INTERACT THROUGH POPUPPANEL
    popupPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {System.out.println("popupPanel clicked");}
    });

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

    // Resize main on window resize
    window.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        contextPanel.setBounds(0, 0, paneOfContent.getWidth(), paneOfContent.getHeight());
        popupPanel.setBounds(0, 0, paneOfContent.getWidth(), paneOfContent.getHeight()-1);
      }
    });

    // Start by resizing
    EventQueue.invokeLater(() -> {
      contextPanel.setBounds(0, 0, paneOfContent.getWidth(), paneOfContent.getHeight());
      popupPanel.setBounds(0, 0, paneOfContent.getWidth(), paneOfContent.getHeight()-1);
    });

    window.setVisible(true);
  }
}
