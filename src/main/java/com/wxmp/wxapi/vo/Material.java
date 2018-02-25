package com.wxmp.wxapi.vo;

import java.util.List;

/**
 * 素材管理
 */
public class Material {

	private Integer totalCount;
	private Integer itemCount;
	private List<MaterialItem> items;
	

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public List<MaterialItem> getItems() {
		return items;
	}

	public void setItems(List<MaterialItem> items) {
		this.items = items;
	}

	
}
