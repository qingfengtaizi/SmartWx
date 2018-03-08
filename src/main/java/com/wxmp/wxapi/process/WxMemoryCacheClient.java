package com.wxmp.wxapi.process;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.common.collect.Maps;
import com.wxmp.core.util.CacheUtils;
/**
 * 缓存工具类；
 * 目前使用 服务器内存的方式；
 * 
 * 1、开发者可以根据自己的需求使用不同的缓存方式,比如memcached
 * 2、系统默认使用单个公众账号的缓存处理，如果有多个账号，请开发者自行处理
 * 
 */
public class WxMemoryCacheClient {
	
	
	private static Logger log = LogManager.getLogger(WxMemoryCacheClient.class);
	
	
	public static void addMpAccount(MpAccount account){
		Map<String,MpAccount> mpAccountMap = (Map<String, MpAccount>) CacheUtils.get("mpAccountMap");
		if(mpAccountMap==null){
			mpAccountMap=Maps.newHashMap();
		}
		if(account != null){
			mpAccountMap.put(account.getAccount(), account);
			CacheUtils.put("mpAccountMap", mpAccountMap);
		}
	}
//	
	public static MpAccount getMpAccount(String account){
		Map<String,MpAccount> mpAccountMap = (Map<String, MpAccount>) CacheUtils.get("mpAccountMap");
		return mpAccountMap.get(account);
	}
	
	//获取唯一的公众号,如果需要多账号，请自行处理
	public static MpAccount getSingleMpAccount(){
		Map<String,MpAccount> mpAccountMap = (Map<String, MpAccount>) CacheUtils.get("mpAccountMap");
		if(mpAccountMap==null){
			mpAccountMap=Maps.newHashMap();
		}
		MpAccount sigleAccount = null;
		for(String key : mpAccountMap.keySet()){
			sigleAccount = mpAccountMap.get(key);
			break;
		}
		return sigleAccount;
	}
	
	public static AccessToken addAccessToken(String account ,AccessToken token){
		Map<String,AccessToken> accountAccessTokenMap = (Map<String, AccessToken>) CacheUtils.get("accountAccessTokenMap");
		if(accountAccessTokenMap==null){
			accountAccessTokenMap=Maps.newHashMap();
		}
		if (null == accountAccessTokenMap) {
			accountAccessTokenMap = Maps.newHashMap();
		}
		if(token != null){
			accountAccessTokenMap.put(account, token);
		}
		CacheUtils.put("accountAccessTokenMap", accountAccessTokenMap);
		return token;
	}
	
	/**
	 * accessToken的获取，绝对不要从缓存中直接获取，请从WxApiClient中获取；
	 * @param account
	 * @return
	 */
	public static AccessToken getAccessToken(String account){
		Map<String,AccessToken> accountAccessTokenMap = (Map<String, AccessToken>) CacheUtils.get("accountAccessTokenMap");
		if(accountAccessTokenMap==null){
			accountAccessTokenMap=Maps.newHashMap();
		}
		return accountAccessTokenMap.get(account);
	}
	
	/**
	 * 获取唯一的公众号的accessToken,如果需要多账号，请自行处理
	 * accessToken的获取，绝对不要从缓存中直接获取，请从WxApiClient中获取；
	 * @return
	 */
	public static AccessToken getSingleAccessToken(){
		Map<String,AccessToken> accountAccessTokenMap = (Map<String, AccessToken>) CacheUtils.get("accountAccessTokenMap");
		if(accountAccessTokenMap==null){
			accountAccessTokenMap=Maps.newHashMap();
		}
		AccessToken accessToken = null;
		for(String key : accountAccessTokenMap.keySet()){
			accessToken = accountAccessTokenMap.get(key);
			break;
		}
		return accessToken;
	}
	
	/**
	 * 添加JSTicket到缓存
	 * @param account
	 * @param jsTicket
	 * @return
	 */
	public static JSTicket addJSTicket(String account ,JSTicket jsTicket){
		Map<String,JSTicket> accountJSTicketMap = (Map<String, JSTicket>) CacheUtils.get("accountJSTicketMap");
		if(null==accountJSTicketMap){
			accountJSTicketMap=Maps.newHashMap();
		}
		if(jsTicket != null){
			accountJSTicketMap.put(account, jsTicket);
		}
		CacheUtils.put("accountJSTicketMap", accountJSTicketMap);
		return jsTicket;
	}
	
	/**
	 * JSTicket的获取，绝对不要从缓存中直接获取，请从JSTicket中获取；
	 * @param account
	 * @return
	 */
	public static JSTicket getJSTicket(String account){
		Map<String,JSTicket> accountJSTicketMap = (Map<String, JSTicket>) CacheUtils.get("accountJSTicketMap");
		if(null==accountJSTicketMap){
			accountJSTicketMap=Maps.newHashMap();
		}
		return accountJSTicketMap.get(account);
	}
	
	/**
	 * 获取唯一的公众号的JSTicket,如果需要多账号，请自行处理
	 * JSTicket的获取，绝对不要从缓存中直接获取，请从WxApiClient中获取；
	 * @return
	 */
	public static JSTicket getSingleJSTicket(){
		Map<String,JSTicket> accountJSTicketMap = (Map<String, JSTicket>) CacheUtils.get("accountJSTicketMap");
		if(null==accountJSTicketMap){
			accountJSTicketMap=Maps.newHashMap();
		}
		JSTicket jsTicket = null;
		for(String key : accountJSTicketMap.keySet()){
			jsTicket = accountJSTicketMap.get(key);
			break;
		}
		return jsTicket;
	}
	
	
	//处理openid缓存
	public static String getOpenid(String sessionid){
		Map<String,String> sessionOpenIdMap = (Map<String,String>) CacheUtils.get("sessionOpenIdMap");
		if(null==sessionOpenIdMap){
			sessionOpenIdMap=Maps.newHashMap();
		}
		if(!StringUtils.isBlank(sessionid)){
			return sessionOpenIdMap.get(sessionid);
		}
		return null;
	}
	
	public static String setOpenid(String sessionid, String openid){
		Map<String,String> sessionOpenIdMap = (Map<String,String>) CacheUtils.get("sessionOpenIdMap");
		if(null==sessionOpenIdMap){
			sessionOpenIdMap=Maps.newHashMap();
		}
		if(!StringUtils.isBlank(sessionid) && !StringUtils.isBlank(openid)){
			sessionOpenIdMap.put(sessionid, openid);
		}
		CacheUtils.put("sessionOpenIdMap", sessionOpenIdMap);
		return openid;
	}
	
	//处理OAuth的Token
	public static AccessToken addOAuthAccessToken(String account ,OAuthAccessToken token){
		Map<String,OAuthAccessToken> accountOAuthTokenMap = (Map<String, OAuthAccessToken>) CacheUtils.get("accountOAuthTokenMap");
		if(null==accountOAuthTokenMap){
			accountOAuthTokenMap=Maps.newHashMap();
		}
		if(token != null){
			accountOAuthTokenMap.put(account, token);
		}
		CacheUtils.put("accountOAuthTokenMap", accountOAuthTokenMap);
		return token;
	}
	
	/**
	 * OAuthAccessToken的获取，绝对不要从缓存中直接获取，请从WxApiClient中获取；
	 * @param account
	 * @return
	 */
	public static OAuthAccessToken getOAuthAccessToken(String account){
		Map<String,OAuthAccessToken> accountOAuthTokenMap = (Map<String, OAuthAccessToken>) CacheUtils.get("accountOAuthTokenMap");
		if(null==accountOAuthTokenMap){
			accountOAuthTokenMap=Maps.newHashMap();
		}
		return accountOAuthTokenMap.get(account);
	}
	
	/**
	 * 获取唯一的公众号的accessToken,如果需要多账号，请自行处理
	 * OAuthAccessToken的获取，绝对不要从缓存中直接获取，请从WxApiClient中获取；
	 * @return
	 */
	public static OAuthAccessToken getSingleOAuthAccessToken(){
		Map<String,OAuthAccessToken> accountOAuthTokenMap = (Map<String, OAuthAccessToken>) CacheUtils.get("accountOAuthTokenMap");
		if(null==accountOAuthTokenMap){
			accountOAuthTokenMap=Maps.newHashMap();
		}
		OAuthAccessToken token = null;
		for(String key : accountOAuthTokenMap.keySet()){
			token = accountOAuthTokenMap.get(key);
			break;
		}
		return token;
	}
}


