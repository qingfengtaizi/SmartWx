/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxapi.process;

/**
 * 消息类型
 * 
 */

public enum OAuthScope {

	Base("snsapi_base"),//用户openid
	Userinfo("userinfo");//用户信息
	
	private String name;
	
	private OAuthScope(String name) {
	     this.name = name;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
	
}
