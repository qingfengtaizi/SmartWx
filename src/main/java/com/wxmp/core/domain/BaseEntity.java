package com.wxmp.core.domain;

import java.util.Date;

public class BaseEntity {

	private Long id;
	private Date createtime = new Date();//创建时间
	
	
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
