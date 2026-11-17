import java.util.ArrayList;

public class FilterManager {
    private static ArrayList<QueryConfig> presets;
    private static QueryConfig currentPreset;
    
    public static void SwitchPreset(int i) {
        if(i < 0)
            i = 0;
        else if(i >= presets.size())
            i = presets.size() - 1;
        currentPreset = presets.get(i);
    }
    
    public static void RemoveCurrentPreset() {
        int i = indexOf(currentPreset);
        presets.Remove(currentPreset);
        
        if(presets.size() == 0) {
            AddPreset();
            return;        
        }
        
        if(i >= presets.size())
            i = presets.size() - 1;
        
        currentPreset = presets.get(i);
    }
    
    public static void AddPreset() {
        currentPreset = new QueryConfig(0, 50);
        presets.add(currentPreset);
    }
    
    public 
}

