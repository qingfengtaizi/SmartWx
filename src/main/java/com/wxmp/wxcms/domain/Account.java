package com.wxmp.wxcms.domain;

import java.util.Date;

import com.wxmp.wxapi.process.MpAccount;

/**
 * 微信公众账号
 * @author : hermit
 */
public class Account extends MpAccount{

	private String name;//名称
	private Long id;
	private Date createtime = new Date();//创建时间
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}