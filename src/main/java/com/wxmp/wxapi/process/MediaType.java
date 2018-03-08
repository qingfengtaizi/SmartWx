package com.wxmp.wxapi.process;

/**
 * 多媒体文件类型
 * 
 */

public enum MediaType {

	News("news"),//图文
	Image("image"),//图片
	Voice("voice"),//语音
	Video("video"),//视频
	Thumb("thumb");//缩略图
	
	private String name;
	
	private MediaType(String name) {
	     this.name = name;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
}
