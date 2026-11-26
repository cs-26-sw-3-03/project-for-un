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
  //
  //   public foo() {
  //     setBackground(CustomTheme.GetColor("primær_baggrund"));
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
  // "normal_knap_standard":           "#FFFFFF", Hvid
  // "normal_knap_fremhævet":          "#FFAAAA", Meget lys rød
  // "normal_knap_tryk":               "#FF4444", Lidt lys rød
  // "filter_knap_aktiv_standard":     "#AAFFAA", Meget lys grøn
  // "filter_knap_aktiv_fremhævet":    "#66FF66", Lidt lys grøn
  // "filter_knap_aktiv_tryk":         "#11FF11", Grøn
  // "filter_knap_inaktiv_standard":   "#FFAAAA", Meget lys rød
  // "filter_knap_inaktiv_fremhævet":  "#FF6666", Forholdsvis lys rød
  // "filter_knap_inaktiv_tryk":       "#FF1111", Rød
  // "primær_baggrund":                "#DDDDDD", Meget lys grå
  // "sekundær_baggrund":              "#BBBBBB", Lidt lys grå
  // "accent_farve":                   "#BBCCDD", Pastel baby blå(?)
  //
  
  public static java.awt.Color GetColor(String color) {
    return themes.get(Settings.GetSetting("THEME")).get(color);
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
