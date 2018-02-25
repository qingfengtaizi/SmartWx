package com.wxmp.core.util;

import java.io.Serializable;

/**
 * 保存在session中的User
 * 
 */
public class SessionUser implements Serializable{
	private static final long serialVersionUID = -8293251468274977994L;
	
	private Long id;
	private String loginname;
	private String realname;
	private String headImage;
	private Integer enable;
	private String openid;
	/**
	 * 1-超级管理员；
	 */
	private Integer ruleid;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public Integer getRuleid() {
		return ruleid;
	}
	public void setRuleid(Integer ruleid) {
		this.ruleid = ruleid;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	public Integer getEnable() {
		return enable;
	}
}
