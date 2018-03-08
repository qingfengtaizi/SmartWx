package com.wxmp.wxapi.vo;

/**
 * 公众号回复给用户的消息 - 音乐消息
 * 
 */

public class MsgResponseMusic extends MsgResponse{

	private static final long serialVersionUID = -1533051743403128524L;
	
	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		Music = music;
	}
	
}
