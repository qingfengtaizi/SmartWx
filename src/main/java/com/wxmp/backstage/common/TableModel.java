package com.wxmp.backstage.common;

import java.util.Map;

/** 
 * @title : 表单数据分页
 * @author : hermit
 */
public class TableModel {

	/**
	 * 总条数
	 */
	private Integer total;
	
	/**
	 * 每页最大条数
	 */
	private Integer rows;
	
	/**
	 * 当前页
	 */
	private Integer page;
	
	/**
	 * 搜索条件
	 */
	private Map<String, Object> terms;
	
	/**
	 * 结果列表
	 */
	private Object data;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Map<String, Object> getTerms() {
		return terms;
	}

	public void setTerms(Map<String, Object> terms) {
		this.terms = terms;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
