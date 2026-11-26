import java.util.*;

public class HighlightManager {

	private static HashMap<Integer, Object> highlightsHash = new HashMap<Integer, Object>();

	public static void HighlightAddProduct(Integer row, Object id){
		if (!CheckHighlightProduct(id)) {
			highlightsHash.remove(row); //failsafe
			highlightsHash.put(row, id);
		}
	}

	public static void HighlightRemoveProduct(Integer row){
		highlightsHash.remove(row); //since hashmap does nothing if no key with this value is found
	}															//there is no reason to care for failsafes in this cas.

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

}
