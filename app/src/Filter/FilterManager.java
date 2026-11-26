import java.util.ArrayList;


public class FilterManager {
    private ArrayList<QueryConfig> presets;
    private int currentIndex;
    private QueryConfig currentPreset;
    
    private static FilterManager INSTANCE = null;
    
    public static final int OFFSET = 0;
    public static final int LIMIT = 100;

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
        return currentIndex;
    }
    
    public void SwitchPreset(int i) {
        currentIndex = Math.clamp(i, 0, presets.size() - 1);
        currentPreset = presets.get(currentIndex).Clone();
    }
    
    public void RemoveCurrentPreset() {
        presets.remove(currentIndex);
        
        if(presets.size() == 0) {
            AddPreset();
            return;        
        }
        
        if(currentIndex >= presets.size())
            currentIndex = presets.size() - 1;
        
        SwitchPreset(currentIndex);
    }
    
    public void AddPreset() {
        QueryConfig newPreset = new QueryConfig(OFFSET, LIMIT);
        
        final Attribute[] attributes = Attribute.values();
        final FType[] fTypes = FType.values();
        
        for(Attribute attribute : attributes) {
            //Finds the first filter that allows this attribute
            for(FType fType : fTypes) {
                Filter filter = FilterFactory.MakeFilter(fType);
                if(filter.AttributeAllowed(attribute)) {
                    newPreset.SetFilter(attribute, filter);
                    break;
                }
            }
        }
        presets.add(newPreset);
        currentIndex = presets.indexOf(newPreset);
        currentPreset = newPreset.Clone();
    }
    
    public void SetPreset(QueryConfig preset) {
        currentPreset = preset;
    }
    
    public void SavePreset() {
        presets.set(currentIndex, currentPreset);
        //SaveToFile()
    }
    
    public QueryConfig GetCurrentPreset() {
        return currentPreset;
    }
    
    public QueryConfig GetCurrentSavedPreset() {
        return presets.get(currentIndex);
    }
    
    
    //The order of cycling is defined in the FType file
    public Filter CycleFilter(Attribute attribute) {
        Filter filter = currentPreset.GetFilter(attribute);
        FType fType = filter.GetFType();
        
        do {
            int nextFTypeOrdinal = (fType.ordinal() + 1) % FType.values().length;
            fType = FType.values()[nextFTypeOrdinal];
            filter = FilterFactory.MakeFilter(fType);
        } while(!filter.AttributeAllowed(attribute));
        
        currentPreset.SetFilter(attribute, filter);
        return filter;
    }
}

