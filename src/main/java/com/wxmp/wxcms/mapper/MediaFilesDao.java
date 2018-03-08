package com.wxmp.wxcms.mapper;

import java.util.List;

import com.wxmp.wxcms.domain.MediaFiles;

/** 
 * @version 1.0
 * @author : hermit
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
