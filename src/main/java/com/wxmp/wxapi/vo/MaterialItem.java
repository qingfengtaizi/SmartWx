package com.wxmp.wxapi.vo;

import lombok.Data;

import java.util.List;

/**
 * 素材
 */
@Data
public class MaterialItem {

	private String mediaId;
	private Long updateTime;
	private List<MaterialArticle> newsItems;//图文消息
	
	//视频、图片、声音
	private String name;
	private String url;
}

