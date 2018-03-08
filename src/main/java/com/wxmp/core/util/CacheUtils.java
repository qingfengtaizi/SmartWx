/**
 * Copyright &copy; 2012-2014 <a href="http://www.xspace.com">xspace</a> All rights reserved.
 */
package com.wxmp.core.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Cache工具类
 * @author xspace
 * @version 2013-5-29
 */
public class CacheUtils {
	
	private static CacheManager cacheManager = ((CacheManager)SpringContextHolder.getBean("cacheManager"));

	private static final String WX_CACHE = "wxCache";
	
	private static final byte[] _lock = new byte[0];
	
	/**
	 * 获取WX_CACHE缓存
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return get(WX_CACHE, key);
	}
	
	/**
	 * 写入WX_CACHE缓存
	 * @param key
	 * @return
	 */
	public static void put(String key, Object value) {
		put(WX_CACHE, key, value);
	}
	
	/**
	 * 从WX_CACHE缓存中移除
	 * @param key
	 * @return
	 */
	public static void remove(String key) {
		remove(WX_CACHE, key);
	}
	
	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName, String key) {
		Element element = getCache(cacheName).get(key);
		return element==null?null:element.getObjectValue();
	}

	/**
	 * 写入缓存
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName, String key, Object value) {
		Element element = new Element(key, value);
		getCache(cacheName).put(element);
	}

	/**
	 * 从缓存中移除
	 * @param cacheName
	 * @param key
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(key);
	}
	
	/**
	 * 获得一个Cache，没有则创建一个。
	 * @param cacheName
	 * @return
	 */
	private static Cache getCache(String cacheName){
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null){
			synchronized (_lock) {
				if (cache == null){
					cacheManager.addCache(cacheName);
					cache = cacheManager.getCache(cacheName);
					cache.getCacheConfiguration().setEternal(true);
				}
			}
		}
		return cache;
	}

	public static CacheManager getCacheManager() {
		return cacheManager;
	}
	
}
