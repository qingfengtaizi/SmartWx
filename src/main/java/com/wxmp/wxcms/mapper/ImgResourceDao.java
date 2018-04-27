/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.mapper;

import com.wxmp.wxcms.domain.ImgResource;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */

public interface ImgResourceDao {
	/**
	 * 获取图片信息
	 * @param id
	 * @return
	 */
	public ImgResource getImgById(String id);
	
	/**
	 * 创建资源
	 * @param img
	 * @return
	 */
	public void add(ImgResource img);
	
	/**
	 * 删除中间表记录
	 * @param otherId
	 * @return
	 */
	public void deleteByMediaId(String otherId);
	
}   
