package com.wxmp.backstage.img.mapper;

import java.util.List;

import com.wxmp.backstage.img.domain.ImgResource;

/** 
 * @title : 
 * @description : 
 * @projectname : wxmp
 * @classname : ImgResourceDao
 * @version 1.0
 * @author : hermit
 * @createtime : 2017年6月13日 下午5:06:35
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
