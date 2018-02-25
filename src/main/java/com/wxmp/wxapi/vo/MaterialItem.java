package com.wxmp.wxapi.vo;

import java.util.List;

/**
 * 素材
 */
public class MaterialItem {

	private String mediaId;
	private Long updateTime;
	private List<MaterialArticle> newsItems;//图文消息
	
	//视频、图片、声音
	private String name;
	private String url;
	
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public List<MaterialArticle> getNewsItems() {
		return newsItems;
	}
	public void setNewsItems(List<MaterialArticle> newsItems) {
		this.newsItems = newsItems;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}

