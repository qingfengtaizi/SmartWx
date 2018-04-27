/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.domain;

import com.wxmp.core.page.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Data
public class ImgResource extends Page implements Serializable {
    private String  id;
	/**
	 * 图片原名称
	 */
	private String  trueName;
	
	/**
	 * 微信返回的mediaId
	 */
	private String mediaId;
	
	/**
	 * 图片尾缀名类型
	 */
	private String  type;
	
	/**
	 * 图片存储名称
	 */
	private String  name;
	
	/**
	 * 图片路径
	 */
	private String  url;
	
	/**
	 * 图片http访问路径
	 */
	private String  httpUrl;	
	
	/**
	 * 图片大小byte
	 */
	private Integer  size;
	
	/**
	 * 创建时间
	 */
	private Date  createTime;
	
	/**
	 * 修改时间
	 */
	private Date  updateTime;

	/**
	 * 图片状态字段：0.未引用 ，1.已被引用
	 */
	private Integer flag;

}
