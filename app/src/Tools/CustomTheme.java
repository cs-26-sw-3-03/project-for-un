import java.awt.Color;
import java.util.Dictionary;
import java.nio.file.*;
import java.util.HashMap;

public class CustomTheme {
  public static HashMap<String, HashMap<String, java.awt.Color>> themes = new HashMap<>();
  // Usage: 
  // HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme());
  // Color primary = themeColors.get("primær_baggrund");
  // - Se alle farver på app/settings/Temaer.json
  //
  // Dependencies: java.util.HashMap;
  //
  // Example: 
  // public class foo extends JPanel {
  //   private static HashMap<String, java.awt.Color> themeColors = CustomTheme.getTheme());
  //
  //   public foo() {
  //     setBackground(themeColors.get("primær_baggrund"));
  //
  //     JLabel labelButton = new JLabel("Press me!");
  //     labelButton.setBackground(themeColors.get("normal_knap_standard"));
  //
  //     add(labelButton);
  //   }
  // }
  //
  // Colors:
  //
  // Standard & brugerdefineret er lige pt. det samme
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
  
  public static HashMap<String, java.awt.Color> getTheme() {
    return themes.get(Settings.GetSetting("THEME"));
  }


  public static void ReadThemesFromThemefile() {
    String data = "";
    Path path = Path.of("settings/Temaer.txt");
    if (!Files.exists(path)) return;

    try {
      data = new String(Files.readString(path));
    } catch (java.io.IOException e) {
      e.printStackTrace();
      return;
    }

    data = data.replace(" ", "");
    data = data.replace("\n", "");
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
      HashMap<String, java.awt.Color> colorMap = new HashMap<>();

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

