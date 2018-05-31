/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.domain;
import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wxmp.core.page.Page;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author fuzi Kong
 * @version 2.0
 * @date 2018-05-30 10:54:58
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class UserTag extends Page implements Serializable {

	private static final long serialVersionUID = 4576996489117070188L;
	private Integer id;//主键ID
	private String name;//标签名称
	private Integer count = 0;//该标签的粉丝数量
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss",iso= DateTimeFormat.ISO.DATE_TIME)
	private Date createTime = new Date();//创建时间
}