package com.wxmp.wxapi.vo;

/**
 * 素材总数
 */
public class MaterialCount {

	private Integer voiceCount;//语音总数量 
	private Integer videoCount;//视频总数
	private Integer imageCount;//图片总数
	private Integer newsCount;//图文总数
	
	
	public Integer getVoiceCount() {
		return voiceCount;
	}
	public void setVoiceCount(Integer voiceCount) {
		this.voiceCount = voiceCount;
	}
	public Integer getVideoCount() {
		return videoCount;
	}
	public void setVideoCount(Integer videoCount) {
		this.videoCount = videoCount;
	}
	public Integer getImageCount() {
		return imageCount;
	}
	public void setImageCount(Integer imageCount) {
		this.imageCount = imageCount;
	}
	public Integer getNewsCount() {
		return newsCount;
	}
	public void setNewsCount(Integer newsCount) {
		this.newsCount = newsCount;
	}
	
	
}
