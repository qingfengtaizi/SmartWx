package com.wxmp.core.util;
//
//import net.oschina.j2cache.CacheChannel;
//import net.oschina.j2cache.CacheObject;
//
///**
// * Cache工具类
// * @author hermit
// */
public class CacheJ2Utils {
//
//	private static final String WX_CACHE = "wxCache";
//	private static CacheChannel cache = null;
//
//	static {
//		cache = J2CacheUtil.getChannel();
//	}
//	/**
//	 * 获取WX_CACHE缓存
//	 * @param key
//	 * @return
//	 */
//	public static Object get(String key) {
//		return get(WX_CACHE, key);
//	}
//
//	/**
//	 * 写入WX_CACHE缓存
//	 * @param key
//	 * @return
//	 */
//	public static void put(String key, Object value) {
//		put(WX_CACHE, key, value);
//	}
//
//	/**
//	 * 从WX_CACHE缓存中移除
//	 * @param key
//	 * @return
//	 */
//	public static void remove(String key) {
//		remove(WX_CACHE, key);
//	}
//
//	/**
//	 * 获取缓存
//	 * @param cacheName
//	 * @param key
//	 * @return
//	 */
//	public static Object get(String cacheName, String key) {
//		CacheObject object = cache.get(cacheName, key);
//		return object == null ? null : object.getValue();
//	}
//
//	/**
//	 * 写入缓存
//	 * @param cacheName
//	 * @param key
//	 * @param value
//	 */
//	public static void put(String cacheName, String key, Object value) {
//		cache.set(cacheName,key,value);
//	}
//
//	/**
//	 * 从缓存中移除
//	 * @param cacheName
//	 * @param key
//	 */
//	public static void remove(String cacheName, String key) {
//		cache.evict(cacheName,key);
//	}
//
}
