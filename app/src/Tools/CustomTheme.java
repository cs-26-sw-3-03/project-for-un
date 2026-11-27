import java.awt.Color;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
  //
  //   public foo() {
  //     setBackground(CustomTheme.GetColor("primær_baggrund"));
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
  
  public static final String CUSTOM_THEME = "brugerdefineret";

  public static void setColor(String themeName, String colorName, Color color) {
    themes.get(themeName).put(colorName, color);
  }

  public static void saveThemesToThemefile() {
    StringBuilder sb = new StringBuilder();
    int t = 0;
    for (java.util.Map.Entry<String, HashMap<String, Color>> theme : themes.entrySet()) {
      if (t++ > 0) sb.append(",\n");
      sb.append('"').append(theme.getKey()).append("\": {\n");
      int c = 0;
      for (java.util.Map.Entry<String, Color> color : theme.getValue().entrySet()) {
        Color v = color.getValue();
        sb.append(String.format("  \"%s\": \"#%02X%02X%02X\"", color.getKey(), v.getRed(), v.getGreen(), v.getBlue()));
        sb.append(++c < theme.getValue().size() ? ",\n" : "\n");
      }
      sb.append('}');
    }
    sb.append('\n');

    try {
      Files.writeString(Path.of("settings/Temaer.txt"), sb.toString());
    } catch (java.io.IOException e) {
      e.printStackTrace();
    }
  }

  public static java.awt.Color GetColor(String color) {
    return themes.get(Settings.GetSetting("THEME")).get(color);
  }

  public static void ReadThemesFromThemefile() {
    String data = "";
    Path path = Path.of("settings/Temaer.txt");
    if (!Files.exists(path)) return;

    try {
      data = new String(Files.readString(path, StandardCharsets.UTF_8));
    } catch (java.io.IOException e) {
      e.printStackTrace();
      return;
    }
    
    data = data.replace("\\", "");
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