import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.event.*;
import java.util.HashMap;


public class SettingsPage extends JPanel {
  private static HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme();

  public SettingsPage() {
    setBackground(themeColors.get("primær_baggrund"));

    List<String> themes = new ArrayList<>(CustomTheme.themes.keySet());
    String[] toggleModes = {"toggle", "hold"};

    JComboBox themeDropdown = new JComboBox(themes.toArray());
    JComboBox toggleModesDropdown = new JComboBox(toggleModes);

    themeDropdown.setSelectedItem(Settings.getSetting("THEME"));
    toggleModesDropdown.setSelectedItem(Settings.getSetting("SELECTED_PRODUCTS_TOGGLE_MODE"));

    themeDropdown.addActionListener(e -> {
      String selectedItem = (String)((JComboBox)e.getSource()).getSelectedItem();
      Settings.SaveSetting("THEME", selectedItem);
    });
    toggleModesDropdown.addActionListener(e -> {
      String selectedItem = (String)((JComboBox)e.getSource()).getSelectedItem();
      Settings.SaveSetting("SELECTED_PRODUCTS_TOGGLE_MODE", selectedItem);
    });

    add(themeDropdown);
    add(toggleModesDropdown);
  }
}
