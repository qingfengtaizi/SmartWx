package com.wxmp.wxapi.vo;

import lombok.Data;

/**
 * 
 * 公众号回复给用户的消息 - 语音消息
 * 
 * 
 */

@Data
public class MsgResponseVoice extends MsgResponse{

	private static final long serialVersionUID = 6762623431582364815L;
	
	private Voice Voice;
}
