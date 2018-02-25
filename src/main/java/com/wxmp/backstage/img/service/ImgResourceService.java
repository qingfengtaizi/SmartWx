package com.wxmp.backstage.img.service;

import com.wxmp.backstage.img.domain.ImgResource;

/** 
 * @title : 
 * @description : 
 * @projectname : wxmp
 * @classname : ImgResourceService
 * @version 1.0
 * @author : hermit
 * @createtime : 2017年6月12日 上午4:46:53
*/
public interface ImgResourceService {
	/**
	 * 获取图片信息
	 * @param id
	 * @return
	 */
	public ImgResource getImg(String id);
	
	/**
	 * 创建资源
	 * @param img
	 * @return
	 */
	public String addImg(ImgResource img);
	
	/**
	 * 删除中间表记录
	 * @param otherId
	 * @return
	 */
	public boolean removeOtherToImg(String otherId);
	
	/**
	 * 删除图片记录
	 * @param id
	 * @return
	 */
	public boolean updateImgFlag(String id, Integer flag);
}
