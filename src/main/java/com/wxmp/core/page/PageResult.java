package com.wxmp.core.page;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author hermit
 * @date 2017年8月16日
 */
@Data
public class PageResult<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 分页数据
	 */
	private List<T> data=new ArrayList<T>();
	
	/**
	 * 分页信息
	 */
	private Page page=new Page();
}
