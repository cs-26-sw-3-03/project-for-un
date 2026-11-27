import java.util.*;

public class HighlightManager {

	static final Integer STARTING_AMOUNT = 0;

	private static HashMap<Integer, Object> highlightsHash = new HashMap<Integer, Object>();
	private static HashMap<Object, Integer> highlightsAmount = new HashMap<Object, Integer>();

	public static void HighlightAddProduct(Integer row, Object id){
		if (!CheckHighlightProduct(id)) {
			highlightsHash.remove(row); //failsafe
			highlightsHash.put(row, id);
			highlightsAmount.put(id, STARTING_AMOUNT);
		}
	}

	public static void HighlightRemoveProduct(Integer row){
			highlightsAmount.remove(HighlightGetProduct(row));	//since hashmap does nothing if no key with this value is found
			highlightsHash.remove(row);													//there is no reason to care for failsafes in this case.
	}															

	public static Boolean CheckHighlightProduct(Object id){
    return highlightsHash.containsValue(id);
	}

	public static Object[] GetHighlightProductsObject(){
		Object[] arr = new Object[highlightsHash.size()];

		int num = 0;

		for (Object i : highlightsHash.values()){
			arr[num] = i;
			num++;
		}

		return arr;
	}

	public static String[] GetHighlightProductsString(){
		String[] arr = new String[highlightsHash.size()];

		int num = 0;

		for (Object i : highlightsHash.values()){
			arr[num] = i.toString();
			num++;
		}

		for (int j = 0; j < arr.length; j++){
			System.out.println(arr[j]);
		}
		System.out.println("/");

		return arr;
	}

	public static Object HighlightGetProduct(Integer row){
		return highlightsHash.get(row);
	}

	public static void HighlightProductsDebug(){
		System.out.println(" ");
		for (Object i : highlightsHash.values()){
			System.out.println(i);
		}
		System.out.println("/");
	}

	public static Integer HighlightHashSize(){
		return highlightsHash.size();
	}

	public static Integer HighlightProductAmount(Object id){
		return highlightsAmount.get(id);
	}

	public static void HighlightProductChangeAmount(Object id, Integer i){
		highlightsAmount.put(id, i);
	}

}
