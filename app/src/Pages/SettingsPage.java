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
    // setBackground(new Color(220,220,220));
    setBackground(themeColors.get("primær_baggrund"));

    List<String> themes = new ArrayList<>(CustomTheme.themes.keySet());
    String[] toggleModes = {"toggle", "hold"};

    JComboBox themeDropdown = new JComboBox(themes.toArray());
    JComboBox toggleModesDropdown = new JComboBox(toggleModes);

    themeDropdown.setEnabled(true);
    toggleModesDropdown.setEnabled(true);

    themeDropdown.addActionListener(e -> {
      JComboBox cb = (JComboBox)e.getSource();
      String selectedItem = (String)cb.getSelectedItem();
      Settings.saveSetting("tema", selectedItem);
    });
    toggleModesDropdown.addActionListener(e -> {
      JComboBox cb = (JComboBox)e.getSource();
      String selectedItem = (String)cb.getSelectedItem();
      Settings.saveSetting("valgteProdukterSkifteTilstand", selectedItem);
    });

    add(themeDropdown);
    add(toggleModesDropdown);
  }
}
