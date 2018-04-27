/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.domain;

import java.io.Serializable;

import com.wxmp.core.page.Page;

import lombok.Data;
/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Data
public class MsgArticle  extends Page implements Serializable {

	private Integer arId;
	private String title;
	private String author;
	private String content;
	private String digest;
	private Integer showCoverPic;
	private String picUrl;
	private String url;
	private String thumbMediaId;
	private String contentSourceUrl;
	private String mediaId;
	private Integer newsIndex;
	private Integer newsId;
}
