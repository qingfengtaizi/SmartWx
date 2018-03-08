package com.wxmp.core.util;

import java.util.HashMap;
import java.util.Map;

public class ApplicatonStaticUtil {
	
	public static String THEME = "_app_static_theme_";
	

	private static Map<String,Object> appMap = new HashMap<String,Object>();
	
	public static void addAppStaticData(String key ,Object obj){
		appMap.put(key, obj);
	}
	
	public static Object getAppStaticData(String key){
		return appMap.get(key);
	}
	
	public static boolean hasAppStaticData(String key){
		return appMap.containsKey(key);
	}
	
}
