package com.wxmp.wxapi.vo;

/**
 * 
 * 公众号回复给用户的消息 - 语音消息
 * 
 * 
 */


public class MsgResponseVoice extends MsgResponse{

	private static final long serialVersionUID = 6762623431582364815L;
	
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
	
}
