import java.awt.*;
import javax.swing.*;
import java.util.Arrays;

public class PopupManager {
  private static boolean[] popupListActive = new boolean[Popups.values().length];

  public static boolean Show(Popups popup) {
    if (popupListActive[popup.ordinal()]) {
      System.out.println("FAILED ADDING POPUP: " + popup.toString());
      return false;
    }

    popupListActive[popup.ordinal()] = true;

    switch(popup)
    {
      case popup.SelectedProduct:
        // Add to mainPanel.contextpanel.popupPanel
        Main.popupPanel.add(new SelectedProductsPopup());
        break;
      default:
        System.out.println("Unknown Popup: " + popup.toString());
        return false;
    }
    System.out.println("Added Popup: " + popup.toString());

    Main.popupPanel.setVisible(true);
    Main.popupPanel.requestFocusInWindow();

    Main.window.revalidate();
    Main.window.repaint();

    return true;
  }


  public static boolean Hide(Popups popup) {
    if (!popupListActive[popup.ordinal()]) {
      System.out.println("POPUP ALREADY HIDDEN: " + popup.toString());
      return true;
    }

    // Remove from mainPanel.contextpanel.popupPanel
    // System.out.println(Main.popupPanel);
    
    Main.popupPanel.removeAll();

    popupListActive[popup.ordinal()] = false;
    System.out.println("Removed Popup: " + popup.toString());

    Main.popupPanel.setVisible(false);
    Main.contextPanel.requestFocusInWindow();

    Main.window.revalidate();
    Main.window.repaint();
    return true;
  }

  public static boolean Visible(Popups popup) {
    return popupListActive[popup.ordinal()];
  }

  public static boolean Toggle(Popups popup) {
    return popupListActive[popup.ordinal()] ? Hide(popup) : Show(popup);
  }

  public static void RemoveAll() {
    Arrays.fill(popupListActive, false);
    Main.popupPanel.removeAll();

    Main.popupPanel.setVisible(false);
    Main.contextPanel.requestFocusInWindow();
  }
}
