package com.wxmp.wxcms.mapper;

import java.util.List;

import com.wxmp.wxcms.domain.MediaFiles;

/** 
 * @title : 
 * @description : 
 * @projectname : wxmp
 * @classname : MediaFilesDao
 * @version 1.0
 * @author : hermit
 * @createtime : 2016年6月14日 下午1:24:58 
*/
public interface MediaFilesDao {

	public void add(MediaFiles entity);
	
	public List<MediaFiles> getMediaFileList();
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteByMediaId(String mediaId);
}
