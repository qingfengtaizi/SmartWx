package com.wxmp.wxcms.domain;

import java.util.ArrayList;
import java.util.List;


/**
 * 图文消息
 * 
 *
 */
public class MsgNewsVO {

	private String createTimeStr;
	private List<MsgNews> msgNewsList = new ArrayList<MsgNews>();
	
	
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public List<MsgNews> getMsgNewsList() {
		return msgNewsList;
	}
	public void setMsgNewsList(List<MsgNews> msgNewsList) {
		this.msgNewsList = msgNewsList;
	}
	
	
	
}