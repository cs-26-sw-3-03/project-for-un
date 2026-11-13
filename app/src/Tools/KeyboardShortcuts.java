import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class KeyboardShortcuts {

  private static Set<Integer> pressedKeys = new HashSet<>();
  
  public static void Install() {
    System.out.println("KeyboardShortcuts Installed!");
    
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
      int keyCode = e.getKeyCode();
      // System.out.println("KeyTyped: " + KeyEvent.KEY_TYPED + " AND getID: " + e.getID());

      if (e.getID() == KeyEvent.KEY_PRESSED) 
        if(pressedKeys.add(keyCode))
          KeyPressed(keyCode, e.getModifiersEx());
      
      if (e.getID() == KeyEvent.KEY_RELEASED) {
        pressedKeys.remove(keyCode);
        KeyRelease(keyCode, e.getModifiersEx());
      }
      
      return false;
    });
  }

  private static boolean KeyPressed(int keyCode, int modifiers) {
    // Ignore text input fields
    // if (isInputComponent(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()))
    //   return false;

    // Handle Keys
    switch (keyCode) {
      case KeyEvent.VK_SPACE:
        if (modifiers != InputEvent.CTRL_DOWN_MASK)
          return false;

        // Toggle:
        if (Settings.GetSetting("SELECTED_PRODUCTS_TOGGLE_MODE") == "toggle")
          return PopupManager.Toggle(Popups.SelectedProduct);
        else 
          return PopupManager.Show(Popups.SelectedProduct);
    }
    return false;
  }

  private static boolean KeyRelease(int keyCode, int modifiers) {
    // Ignore text input fields
    // if (isInputComponent(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()))
    //   return false;

    // Handle Keys
    switch (keyCode) {
      case KeyEvent.VK_SPACE:
        // System.out.println("SPACE PRESSED");
        if (modifiers != InputEvent.CTRL_DOWN_MASK)
          return false;

        //Holding, Release:
        if (Settings.GetSetting("SELECTED_PRODUCTS_TOGGLE_MODE") != "toggle") 
          return PopupManager.Hide(Popups.SelectedProduct);
        return true;
    }
    return false;
  }
      
  private static boolean IsInputComponent(Component c) {
    return c instanceof javax.swing.text.JTextComponent
        || c instanceof JComboBox
        || c instanceof JSpinner;
  }
}
