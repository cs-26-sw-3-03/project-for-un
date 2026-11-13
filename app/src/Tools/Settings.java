import java.nio.file.*;
import java.util.HashMap;
import java.util.Dictionary;

public class Settings {
  private static HashMap<String, String> settings = new HashMap<>();

  public static String GetSetting(String key) {
    return settings.get(key);
  }

  public static void GetSettingsFromSettingsfile() {
    String data = "";
    Path path = Path.of("settings/indstillinger.txt");
    if (!Files.exists(path)) return;
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
      for (String settingPart : wholeSetting.split("[:]")) {
        // Differentiate between the name of the setting and the value of the setting
        if (i++%2==0) {
          settingName = settingPart;
          continue;
        }
        // SettingPart here is the value of the setting
        settings.put(settingName, settingPart);
      }
    }
  }

  public static void SaveSetting(String settingToChange, String value) {
    String settingBefore = settings.get(settingToChange);
    if (settingBefore == null)
      return;

    settings.put(settingToChange, value);

    String data = "";
    Path path = Path.of("settings/indstillinger.txt");
    if (!Files.exists(path)) return;
    
    try {
      data = new String(Files.readString(path));
    } catch (java.io.IOException e) {
      e.printStackTrace();
      return;
    }

    data = data.replace(settingBefore, value);

    try {
      Files.write(path, data.getBytes());
    } catch (java.io.IOException e) {
      e.printStackTrace();
      return;
    }
  }
}
