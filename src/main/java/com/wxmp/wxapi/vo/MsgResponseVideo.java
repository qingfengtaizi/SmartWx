package com.wxmp.wxapi.vo;

/**
 * 
 * 公众号回复给用户的消息 - 视频消息
 * 
 * 
 */


public class MsgResponseVideo extends MsgResponse{

	private static final long serialVersionUID = 6762623431582364815L;
	
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
	
}
