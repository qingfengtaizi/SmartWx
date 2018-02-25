package com.wxmp.wxapi.vo;
/**
 * 图文消息
 *  
 * 
 */
public class Article {
	
	private String Title;//标题
	private String Description;//描述
	private String PicUrl;//图片链接
	private String Url;//原文链接
	
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	
}
