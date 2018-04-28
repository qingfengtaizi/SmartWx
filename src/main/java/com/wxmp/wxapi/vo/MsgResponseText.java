package com.wxmp.wxapi.vo;


import lombok.Data;

/**
 * 公众号回复给用户的消息 - 文本消息
 * 
 */
@Data
public class MsgResponseText extends MsgResponse{

	private static final long serialVersionUID = 4956088110027867013L;
	
	private String Content;
}
