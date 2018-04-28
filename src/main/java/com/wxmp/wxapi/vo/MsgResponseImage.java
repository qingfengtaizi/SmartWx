package com.wxmp.wxapi.vo;


import lombok.Data;

/**
 * 公众号回复给用户的消息 - 图片消息
 * 
 */
@Data
public class MsgResponseImage extends MsgResponse{

	private Image Image;
}
