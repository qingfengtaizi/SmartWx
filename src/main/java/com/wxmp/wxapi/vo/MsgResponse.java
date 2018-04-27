package com.wxmp.wxapi.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 公众号回复给用户的消息-基本信息
 * 
 */
@Data
public class MsgResponse implements Serializable{
	
	private String MsgType;
	private String FromUserName;
	private String ToUserName;
	private Long CreateTime;
}
