package com.wxmp.wxapi.process;

import java.util.SortedMap;
import java.util.TreeMap;

import com.wxmp.core.util.wx.SecurityUtil;
import com.wxmp.core.util.wx.SignUtil;


public class WxSign {
	private String appId;
	private String timestamp;
	private String nonceStr;
	private String signature;
	
	public WxSign(){
		
	}
	
	public WxSign(String appId,String jsTicket,String url){
		String timestamp = String.valueOf(System.currentTimeMillis()/1000);
		String nonceStr = SecurityUtil.getRandomString(8);
		SortedMap<String,String> map = new TreeMap<String, String>();
		map.put("jsapi_ticket", jsTicket);
		map.put("noncestr", nonceStr);
		map.put("timestamp", timestamp);
		map.put("url", url);
		this.appId = appId;
		this.nonceStr = nonceStr;
		this.timestamp = timestamp;
		this.signature = SignUtil.signature(map);
	}
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
	
	
	
}
