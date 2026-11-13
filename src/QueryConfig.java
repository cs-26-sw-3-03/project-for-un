import java.util.HashSet;
import java.util.LinkedHashMap;


//QueryConfig is a collection of all search parameters for a query. It has five parts:
//  shown attributes:
//    Show
//    Hide
//    IsHidden
//    ShownCount
//    GetShowSet - returns Set<Attribute>
//  sorted attributes:
//    Sort
//    UnSort
//    HasSort
//    IsAscending - returns null if HasSort is false
//    SortCount
//    GetSortMap - returns Map<Attribute, Boolean>
//  filtered attributes:
//    SetFilter
//    RemoveFilter
//    HasFilter
//    GetFilter
//    FilterCount
//    GetAttributeFilterMap - returns AttributeFilterMap
//  offset - the number of rows to skip before returning:
//    SetOffset
//    GetOffset
//  limit - the max number of rows to return:
//    SetLimit
//    GetLimit


public class QueryConfig {
    private HashSet<Attribute> showSet;
    private LinkedHashMap<Attribute, Boolean> sortMap;
    private AttributeFilterMap filterMap;
    int offset;
    int limit;
    
    public QueryConfig(int offset, int limit) {
        showSet = new HashSet<Attribute>();
        sortMap = new LinkedHashMap<Attribute, Boolean>();
        filterMap = new AttributeFilterMap();
        this.offset = offset;
        this.limit = limit;
    }
    
    public void Show(Attribute attribute) {showSet.add(attribute);}
    public void Hide(Attribute attribute) {showSet.remove(attribute);}
    public boolean IsHidden(Attribute attribute) {return showSet.contains(attribute);}
    public int ShownCount() {return showSet.size();}
    public HashSet<Attribute> GetShowSet() {
        HashSet<Attribute> out = new HashSet<Attribute>();
        for(Attribute attribute : showSet)
            out.add(attribute);
        return out;
    }
    
    
    public void Sort(Attribute attribute, boolean ascending) {sortMap.put(attribute, ascending);}
    public void UnSort(Attribute attribute) {sortMap.remove(attribute);}
    public boolean HasSorting(Attribute attribute) {return sortMap.containsKey(attribute);}
    public Boolean IsAscending(Attribute attribute) {return true;}//return sortMap.get(attribute);}
    public int SortCount() {return sortMap.size();}
    public LinkedHashMap<Attribute, Boolean> GetSortMap() {
        LinkedHashMap<Attribute, Boolean> out = new LinkedHashMap<Attribute, Boolean>();
        for(Attribute attribute : Attribute.values()) {
            if(sortMap.containsKey(attribute))
                out.put(attribute, (Boolean) (boolean) sortMap.get(attribute));
        }
        return out;
    }
    
    
    public void SetFilter(Attribute attribute, Filter filter) {filterMap.Put(attribute, filter);}
    public void RemoveFilter(Attribute attribute) {filterMap.Remove(attribute);}
    public boolean HasFilter(Attribute attribute) {return filterMap.Contains(attribute);}
    public Filter GetFilter(Attribute attribute) {return filterMap.Get(attribute);}
    public int FilterCount() {return filterMap.Size();}
    public AttributeFilterMap GetAttributeFilterMap() {return filterMap.Clone();}
    
    public void SetOffset(int offset) {this.offset = offset;}
    public int GetOffset() {return offset;}
    
    public void SetLimit(int limit) {this.limit = limit;}
    public int GetLimit() {return limit;}
}
