package com.wxmp.core.util;

import java.util.UUID;

public class CommonUtil {

	public static String getUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static String stringCap(String str){
		return str.substring(0,1).toLowerCase() + str.substring(1);
	}
	
}
