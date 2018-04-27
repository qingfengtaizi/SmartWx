/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wxmp.core.page.Page;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Data
public class AccountFans extends Page implements Serializable{
	private Long id;
	private String openId;//openId，每个用户都是唯一的
	private Integer subscribeStatus;//订阅状态
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss",iso= DateTimeFormat.ISO.DATE_TIME)
	private Date subscribeTime;//订阅时间
	private byte[] nickname;//昵称,二进制保存emoji表情
	private String nicknameStr;//昵称显示
	private String wxid;//微信号
	private Integer gender;//性别 0-女；1-男；2-未知
	private String language;//语言
	private String country;//国家
	private String province;//省
	private String city;//城市
	private String headimgurl;//头像
	private String remark;//备注
	private Integer status;//用户状态 1-可用；0-不可用
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss",iso= DateTimeFormat.ISO.DATE_TIME)
	private Date createTime;//创建时间

	public String getNicknameStr() {
		if(this.getNickname() != null){
			try {
				this.nicknameStr = new String(this.getNickname(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return nicknameStr;
	}
}