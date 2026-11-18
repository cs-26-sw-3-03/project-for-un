import java.util.ArrayList;


public class FilterManager {
    private ArrayList<QueryConfig> presets;
    private QueryConfig currentPreset;
    
    private static FilterManager INSTANCE = null;

    private FilterManager() {
        presets = new ArrayList<QueryConfig>();
        //ReadFromFile()
        if(presets.size() == 0)
            AddPreset();
    }

    
    public static FilterManager GetInstance() {
        if(INSTANCE == null)
            INSTANCE = new FilterManager();
        return INSTANCE;
    }

    
    public int PresetCount() {
        return presets.size();
    }
    
    public int GetCurrentIndex() {
        return presets.indexOf(currentPreset);
    }
    
    public void SwitchPreset(int i) {
        if(i < 0)
            i = 0;
        else if(i >= presets.size())
            i = presets.size() - 1;
        currentPreset = presets.get(i);
    }
    
    public void RemoveCurrentPreset() {
        int i = presets.indexOf(currentPreset);
        presets.remove(currentPreset);
        
        if(presets.size() == 0) {
            AddPreset();
            return;        
        }
        
        if(i >= presets.size())
            i = presets.size() - 1;
        
        currentPreset = presets.get(i);
    }
    
    public void AddPreset() {
        currentPreset = new QueryConfig(0, 50);
        presets.add(currentPreset);
    }
    
    
    //The order of cycling is defined in the Filter class
    public Filter CycleFilter(Attribute attribute) {
        Filter filter = currentPreset.GetFilter(attribute);
        Filter.FType fType = filter.GetFType();
        
        do {
            int nextFTypeOrdinal = (fType.ordinal() + 1) % Filter.FType.values().length;
            fType = Filter.FType.values()[nextFTypeOrdinal];
            filter = FilterFactory.MakeFilter(fType);
        } while(!filter.TypeAllowed(AttributeDescriber.TypeOf(attribute)));
        
        currentPreset.SetFilter(attribute, filter);
        return filter;
    }
}

