/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxapi.process;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信公众号信息
 */
@Data
public class MpAccount implements Serializable{
	private static final long serialVersionUID = -6315146640254918207L;
	
	private String account;//账号
	private String appid;//appid
	private String appsecret;//appsecret
	private String url;//验证时用的url
	private String token;//token
	//ext
	private Integer msgcount;//自动回复消息条数;默认是5条

	public Integer getMsgcount() {
		if(msgcount == null)
			msgcount = 5;//默认5条
		return msgcount;
	}

}
