package com.wxmp.core.page;

import java.util.List;

/**
 * 分页接口
 */
public interface Page<E> extends Iterable<E> {

	/**
	 * 起始页号
	 * @return
	 */
	int getFirstPageNum();
	
	/**
	 * 终止页号
	 * @return
	 */
	int getLastPageNum();

    /**
     * 当前页号
     * @return
     */
    int getPageNum();

    /**
     * 页面尺寸
     * @return
     */
    int getPageSize();

    /**
     * 页面上的内容
     * @return
     */
    List<E> getItems();

    /**
	 * 是第一页
	 * @return
	 */
    boolean isFirstPage();

	/**
	 * 是最后一页
	 * @return
	 */
	boolean isLastPage();

    /**
     * 是否为空内容
     * @return
     */
    boolean isEmpty();
    
}


