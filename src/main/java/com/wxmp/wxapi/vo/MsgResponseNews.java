package com.wxmp.wxapi.vo;

import lombok.Data;

import java.util.List;


/**
 * 公众号回复给用户的消息 - 图文消息
 * 
 */
@Data
public class MsgResponseNews extends MsgResponse{

	private static final long serialVersionUID = -472806392674241312L;
	
	private int ArticleCount;
	private List<Article> Articles;
}
