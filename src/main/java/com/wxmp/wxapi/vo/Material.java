package com.wxmp.wxapi.vo;

import lombok.Data;

import java.util.List;

/**
 * 素材管理
 */
@Data
public class Material {

	private Integer totalCount;
	private Integer itemCount;
	private List<MaterialItem> items;
}
