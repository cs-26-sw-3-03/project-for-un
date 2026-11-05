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
          keyPressed(keyCode);
      
      if (e.getID() == KeyEvent.KEY_RELEASED) {
        pressedKeys.remove(keyCode);
        keyRelease(keyCode);
      }
      
      return false;
    });
  }

  private static boolean keyPressed(int keyCode) {
    // Ignore text input fields
    if (isInputComponent(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()))
      return false;

    // Handle Keys
    switch (keyCode) {
      case KeyEvent.VK_SPACE:
        // System.out.println("SPACE PRESSED");

        // Toggle:
        // if (Settings.SelectedProductsMenuToggleState == true) {
        if (true == true) 
          return PopupManager.Toggle(Popups.SelectedProduct);
        else 
          return PopupManager.Show(Popups.SelectedProduct);
    }
    return false;
  }

  private static boolean keyRelease(int keyCode) {
    // Ignore text input fields
    if (isInputComponent(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()))
      return false;

    // Handle Keys
    switch (keyCode) {
      case KeyEvent.VK_SPACE:
        // System.out.println("SPACE PRESSED");

        //Holding, Release:
        // if (Settings.SelectedProductsMenuToggleState == true) {
        if (true == false)
          return PopupManager.Hide(Popups.SelectedProduct);
        return true;
    }
    return false;
  }
      
  private static boolean isInputComponent(Component c) {
    return c instanceof javax.swing.text.JTextComponent
        || c instanceof JComboBox
        || c instanceof JSpinner;
  }
}
