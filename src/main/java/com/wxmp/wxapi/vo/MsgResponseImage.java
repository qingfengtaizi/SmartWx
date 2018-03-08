package com.wxmp.wxapi.vo;


/**
 * 公众号回复给用户的消息 - 图片消息
 * 
 */

public class MsgResponseImage extends MsgResponse{

	private static final long serialVersionUID = -1223581527093112044L;
	
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}
	
}
