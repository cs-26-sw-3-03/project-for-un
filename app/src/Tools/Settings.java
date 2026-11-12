import java.nio.file.*;
import java.util.HashMap;
import java.util.Dictionary;

public class Settings {
  public static HashMap<String, String> settings = new HashMap<>();

  public static void GetSettingsFromSettingsfile() {
    String data = "";
    Path path = Path.of("settings/indstillinger.txt");
    if (!Files.exists(path)) return;
    // data = new String(Files.readString(path));
    try {
      data = new String(Files.readString(path));
    } catch (java.io.IOException e) {
      e.printStackTrace();
      return;
    }
    data = data.replace("\n", "");
    data = data.replace("\"", "");
    data = data.replace(" ", "");
    String[] dataPiece = data.split("[,]");
    for (String wholeSetting : dataPiece) {
      int i = 0;
      String settingName = "";
      for (String settingPart : s.split("[:]")) {
        // Differentiate between settingName and settingValue
        if (i++%2==0) {
          settingName = settingPart;
          continue;
        }
        // SettingPart here is settingValue
        settings.put(settingName, settingPart);
      }
    }
  }

  public static void saveSetting(String settingToChange, String value) {
    String settingBefore = settings.get(settingToChange);
    if (settingBefore == null)
      return;

    settings.put(settingToChange, value);

    String data = "";
    Path path = Path.of("settings/indstillinger.txt");
    if (!Files.exists(path)) return;
    
    // data = new String(Files.readString(path));
    try {
      data = new String(Files.readString(path));
    } catch (java.io.IOException e) {
      e.printStackTrace();
      return;
    }

    data = data.replace(settingBefore, value);

    // Files.write(path, data.getBytes());
    try {
      Files.write(path, data.getBytes());
    } catch (java.io.IOException e) {
      e.printStackTrace();
      return;
    }
  }
}
