package com.wxmp.wxapi.vo;

import lombok.Data;

/**
 * 公众号回复给用户的消息 - 音乐消息
 * 
 */
@Data
public class MsgResponseMusic extends MsgResponse{

	private Music Music;

}
