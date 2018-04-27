/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
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
