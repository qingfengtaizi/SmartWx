package com.wxmp.wxapi.vo;


import com.wxmp.core.page.Page;
import lombok.Data;

import java.io.Serializable;

/**
 * 素材-图文
 */
@Data
public class MaterialArticle extends Page implements Serializable{
	private String thumb_media_id;
	private String author;
	private String title;
	private String content_source_url;
	private String content;
	private String digest;
	private int show_cover_pic;
	private String url;
}
