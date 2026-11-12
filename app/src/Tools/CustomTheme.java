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
  
  public static HashMap<String, java.awt.Color> getTheme() {
    return themes.get(Settings.settings.get("tema"));
  }


  public static void tryprintfile() {
    String data = "";
    Path path = Path.of("settings/Temaer.json");
    if (!Files.exists(path)) return;

    // data = new String(Files.readString(path));
    try {
      data = new String(Files.readString(path));
    } catch (java.io.IOException e) {
      e.printStackTrace();
    }

    data = data.replace(" ", "");
    data = data.replace("\n", "");
    data = data.replace(":", "");
    data = data.replace(",", "");
    data = data.replace("\"\"", "\"");

    String[] dataPiece = data.split("[{//}]");
    int i = 0;
    String themeNameIteration = "";
    for (String s : dataPiece) {
      if (i++%2==0) {
        themeNameIteration = s.replace("\"","");
        continue;
      }
      HashMap<String, java.awt.Color> colorMap = new HashMap<>();

      s = s.substring(1, s.length()-1);
      String[] themePieces = s.split("[\"]");

      int j = 0;
      String str = "";
      for (String st : themePieces) {
        if (j++%2==0) {
          str = st;
          continue;
        }
        colorMap.put(str, Color.decode(st));
      }

      themes.put(themeNameIteration, colorMap);
    }
    System.out.println(themes);
  }
}

