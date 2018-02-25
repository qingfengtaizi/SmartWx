package com.wxmp.wxcms.domain;

import com.wxmp.core.domain.BaseEntity;

/**
 * 消息基本信息
 * 
 *
 */
public class MsgBase extends BaseEntity{

	private String msgtype;//消息类型;
	private String inputcode;//关注者发送的消息
	private String rule;//规则，目前是 “相等”
	private Integer enable;//是否可用
	private Integer readcount;//消息阅读数
	private Integer favourcount;//消息点赞数
	
	
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public String getInputcode() {
		return inputcode;
	}
	public void setInputcode(String inputcode) {
		this.inputcode = inputcode;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public Integer getReadcount() {
		return readcount;
	}
	public void setReadcount(Integer readcount) {
		this.readcount = readcount;
	}
	public Integer getFavourcount() {
		return favourcount;
	}
	public void setFavourcount(Integer favourcount) {
		this.favourcount = favourcount;
	}
	
}