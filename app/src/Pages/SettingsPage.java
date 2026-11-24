import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;


public class SettingsPage extends JPanel {
  private static HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme();

  public SettingsPage() {
    setBackground(themeColors.get("primær_baggrund"));

    List<String> themes = new ArrayList<>(CustomTheme.themes.keySet());
    String[] toggleModes = {"toggle", "hold"};

    JComboBox themeDropdown = new JComboBox(themes.toArray());
    JComboBox toggleModesDropdown = new JComboBox(toggleModes);

    themeDropdown.setSelectedItem(Settings.GetSetting("THEME"));
    toggleModesDropdown.setSelectedItem(Settings.GetSetting("SELECTED_PRODUCTS_TOGGLE_MODE"));

    themeDropdown.addActionListener(e -> {
      String selectedItem = (String)((JComboBox)e.getSource()).getSelectedItem();
      Settings.SaveSetting("THEME", selectedItem);
      themeColors = CustomTheme.getTheme();
      PageSwitcher.switchPage(Pages.SETTINGS);
    });
    toggleModesDropdown.addActionListener(e -> {
      String selectedItem = (String)((JComboBox)e.getSource()).getSelectedItem();
      Settings.SaveSetting("SELECTED_PRODUCTS_TOGGLE_MODE", selectedItem);
      Main.window.revalidate();
      Main.window.repaint();
    });

    add(themeDropdown);
    add(toggleModesDropdown);

    // Colour editor is only shown for the editable theme
    if (CustomTheme.CUSTOM_THEME.equals(Settings.GetSetting("THEME"))) {
      add(buildCustomThemeEditor());
    }
  }

  /** One swatch button per colour in the custom theme. Click -> HSB colour chooser. */
  private JPanel buildCustomThemeEditor() {
    JPanel panel = new JPanel(new GridLayout(0, 3, 6, 6));
    panel.setOpaque(false);

    // Copy so we can safely modify the map from the button handlers
    for (Map.Entry<String, Color> entry : new ArrayList<>(themeColors.entrySet())) {
      String key = entry.getKey();
      Color color = entry.getValue();

      JButton swatch = new JButton(key.replace('_', ' '));
      swatch.setBackground(color);
      swatch.setForeground(readableTextColor(color));
      swatch.setOpaque(true);          // some look-and-feels ignore background otherwise
      swatch.setContentAreaFilled(true);

      swatch.addActionListener(e -> {
        Color picked = pickColor(this, key.replace('_', ' '), color);
        if (picked == null) return;   // cancelled
        CustomTheme.setColor(CustomTheme.CUSTOM_THEME, key, picked);
        CustomTheme.saveThemesToThemefile();
        themeColors = CustomTheme.getTheme();
        PageSwitcher.switchPage(Pages.SETTINGS);   // rebuild so every page uses the new colour
      });

      panel.add(swatch);
    }
    return panel;
  }

  /** Modal JColorChooser with only the HSB slider panel. Returns null if cancelled. */
  private static Color pickColor(Component parent, String title, Color initial) {
    JColorChooser chooser = new JColorChooser(initial);
    chooser.setChooserPanels(new AbstractColorChooserPanel[] { new HsbChooserPanel() });

    final Color[] result = { null };
    JDialog dialog = JColorChooser.createDialog(
        parent, title, true, chooser,
        e -> result[0] = chooser.getColor(),   // OK
        null);                                 // Cancel
    dialog.setVisible(true);                   // blocks until closed (modal)
    return result[0];
  }

  /** Black or white, whichever is easier to read on the given background. */
  private static Color readableTextColor(Color bg) {
    double luminance = (0.299 * bg.getRed() + 0.587 * bg.getGreen() + 0.114 * bg.getBlue()) / 255.0;
    return luminance > 0.5 ? Color.BLACK : Color.WHITE;
  }
}
