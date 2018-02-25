package com.wxmp.wxcms.domain;


/**
 * 文本消息
 * 
 *
 */
public class MsgText extends MsgBase{

	private String content;//消息内容
	private Long baseId;//消息主表id
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getBaseId() {
		return baseId;
	}
	public void setBaseId(Long baseId) {
		this.baseId = baseId;
	}

}