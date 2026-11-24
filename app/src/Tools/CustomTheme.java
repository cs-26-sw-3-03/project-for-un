import java.awt.Color;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomTheme {
  // LinkedHashMap keeps insertion order (stable dropdown order + stable file layout).
  public static HashMap<String, HashMap<String, java.awt.Color>> themes = new LinkedHashMap<>();

  // Name of the user-editable theme (key in Temaer.txt / value of THEME setting)
  public static final String CUSTOM_THEME = "brugerdefineret";

  private static final Path THEME_FILE = Path.of("settings/Temaer.txt");

  // Usage:
  // HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme();
  // Color primary = themeColors.get("primær_baggrund");
  // - Se alle farver på settings/Temaer.txt
  //
  // Example:
  // public class foo extends JPanel {
  //   private static HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme();
  //
  //   public foo() {
  //     setBackground(themeColors.get("primær_baggrund"));
  //
  //     JLabel labelButton = new JLabel("Press me!");
  //     labelButton.setBackground(themeColors.get("normal_knap_standard"));
  //     labelButton.setForeground(themeColors.get("tekst_farve"));
  //
  //     add(labelButton);
  //   }
  // }
  //
  // Colors (see Temaer.txt for the values of each theme):
  //
  // normal_knap_standard / _fremhævet / _tryk
  // filter_knap_aktiv_standard / _fremhævet / _tryk
  // filter_knap_inaktiv_standard / _fremhævet / _tryk
  // primær_baggrund, sekundær_baggrund, accent_farve
  // tekst_farve  (text colour - needed for dark themes)
  //
  // Themes: standard, lys (light), mørk (dark), brugerdefineret (editable in settings)

  public static HashMap<String, java.awt.Color> getTheme() {
    HashMap<String, java.awt.Color> theme = themes.get(Settings.GetSetting("THEME"));
    // Fall back to "standard" if the saved theme doesn't exist (e.g. file edited by hand)
    return theme != null ? theme : themes.get("standard");
  }

  /** Changes one colour of one theme in memory. Call saveThemesToThemefile() to persist. */
  public static void setColor(String themeName, String colorName, Color color) {
    HashMap<String, Color> theme = themes.get(themeName);
    if (theme != null) theme.put(colorName, color);
  }

  public static String toHex(Color c) {
    return String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
  }

  /** Writes all themes back to settings/Temaer.txt in the same format the reader expects. */
  public static void saveThemesToThemefile() {
    StringBuilder sb = new StringBuilder();
    int t = 0;
    for (Map.Entry<String, HashMap<String, Color>> theme : themes.entrySet()) {
      if (t++ > 0) sb.append(",\n");
      sb.append('"').append(theme.getKey()).append("\": {\n");
      int c = 0;
      int size = theme.getValue().size();
      for (Map.Entry<String, Color> color : theme.getValue().entrySet()) {
        sb.append("  \"").append(color.getKey()).append("\": \"")
          .append(toHex(color.getValue())).append('"');
        sb.append(++c < size ? ",\n" : "\n");
      }
      sb.append('}');
    }
    sb.append('\n');

    try {
      if (THEME_FILE.getParent() != null) Files.createDirectories(THEME_FILE.getParent());
      Files.writeString(THEME_FILE, sb.toString());
    } catch (java.io.IOException e) {
      e.printStackTrace();
    }
  }

  public static void ReadThemesFromThemefile() {
    String data = "";
    Path path = THEME_FILE;
    if (!Files.exists(path)) return;

    try {
      data = new String(Files.readString(path));
    } catch (java.io.IOException e) {
      e.printStackTrace();
      return;
    }

    data = data.replace(" ", "");
    data = data.replace("\n", "");
    data = data.replace("\r", "");
    data = data.replace(":", "");
    data = data.replace(",", "");
    data = data.replace("\"\"", "\"");

    String[] dataPiece = data.split("[{//}]");
    int i = 0;
    String themeName = "";
    for (String wholeThemeString: dataPiece) {
      // Differentiate between themeName and themeMap
      if (i++%2==0) {
        themeName = wholeThemeString.replace("\"","");
        continue;
      }
      HashMap<String, java.awt.Color> colorMap = new LinkedHashMap<>();

      //Replace " 's around the string
      wholeThemeString = wholeThemeString.substring(1, wholeThemeString.length()-1);
      String[] themePieces = wholeThemeString.split("[\"]");

      int j = 0;
      String colorName = "";
      for (String colorPiece: themePieces) {
        // Differentiate between colorName and colorValue
        if (j++%2==0) {
          colorName = colorPiece;
          continue;
        }
        // colorPiece here is color value, decoding from hex to java.awt.Color
        colorMap.put(colorName, Color.decode(colorPiece));
      }

      // put themeNames with their respective colormap into the themes hashMap
      themes.put(themeName, colorMap);
    }
  }
}
