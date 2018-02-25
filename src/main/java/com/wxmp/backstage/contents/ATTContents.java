package com.wxmp.backstage.contents;

import java.util.HashMap;
import java.util.Map;
/**
 * @author : hermit
 */

public class ATTContents {
	
//	public static final String COMMON_SEARCH_SERVER_URL = "http://127.0.0.1:8090/common-search/zoua/insert/";
	

	/**
	 * 图片资源状态：0：未引用	1：已被引用
	 */
	public static final Integer IMG_FLAG0 = 0;
	public static final Integer IMG_FLAG1 = 1;
	
	/**
	 * Token加密密钥
	 */
	public static final String TOKEN_KEY = "_GUyFjkigkjgUKgkjg_h$hjh^JKGGF4542";
	
	/**
	 * 字典编号：科目
	 */
	public static final String DIC_SITE_SUBJECT = "0";
	
	/**
	 * 字典编号：准驾车辆类型
	 */
	public static final String DIC_CART_DICTYPE = "1";
	
	/**
	 * 字典编号：车辆来源
	 */
	public static final String DIC_CART_SOURCE = "2";
	public static Map<String,String> map = new HashMap<String,String>();
	
	/**
	 * 时间段是否购买：已购买
	 */
	public static final Integer CAR_BUY_TRUE = 1;
	
	/**
	 * 时间段是否购买：未购买
	 */
	public static final Integer CAR_BUY_FALSE = 0;
	
	/**
	 * 某日排班是否发布：已发布
	 */
	public static final Integer CAR_PUSH_TRUE = 1;
	
	/**
	 * 某日排班是否发布：未发布
	 */
	public static final Integer CAR_PUSH_FALSE = 0;
}
