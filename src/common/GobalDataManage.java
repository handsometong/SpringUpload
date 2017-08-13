package common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class GobalDataManage {


	private static Map<String, Map<String,String>> dicMap = null;
	
	private static Map<String,String> provinceMap = new HashMap<String,String>();
	private static Map<String,String> cityMap = new HashMap<String,String>();
	private static Map<String,String> areaMap = new HashMap<String,String>();
	
	

	
	private static Map<String,Object> systemInfoMap = new HashMap<String,Object>();
	
	private static Map<String,String> stringsMap = null;
	
	public static void putSystemInfo(String key,Object value){
		systemInfoMap.put(key, value);
	}
	
	public static Object getSystemInfo(String key){
		return systemInfoMap.get(key);
	}
	
	



	public static Map<String, String> getDicItemMap(String dicCode) {
		if (dicMap == null) {
			return null;
		}

		return dicMap.get(dicCode);
	}


	public static String getDicItemName(String dicObjId,String code) {
		Map<String, String> map = getDicItemMap(dicObjId);
		if(map != null){
			return map.get(code);
		}
		return "";
	}
	

	public static String getDicItemCode(String dicObjId,String name){
		String code = "";
		Map<String, String> map = getDicItemMap(dicObjId);
		if(map != null){
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next().toString();
				String itemName = map.get(key);
				if(name.equals(itemName)){
					code = key;
					break;
				}
			}
		}
		return code;
	}
	

	
}
