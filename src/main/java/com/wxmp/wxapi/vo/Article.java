package com.wxmp.wxapi.vo;

import lombok.Data;

/**
 * 图文消息
 */
@Data
public class Article {
	
	private String Title;//标题
	private String Description;//描述
	private String PicUrl;//图片链接
	private String Url;//原文链接
}
