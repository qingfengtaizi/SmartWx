package com.wxmp.wxcms.service;

import java.util.List;

import com.wxmp.wxcms.domain.MediaFiles;
/**
 * 语音和视频逻辑层
 * @author nigulading
 *
 */
public interface MediaFileService {

	/**
	 *  分页
	 * @param entity
	 * @return
	 */
	public List<MediaFiles> getMediaListByPage(MediaFiles entity);
	/**
	 * 添加
	 * @param entity
	 */
	public void add(MediaFiles entity);
	
	/**
	 * 删除
	 * @param mediaId
	 */
	public void deleteByMediaId(String mediaId);
	/**
	 * 获取单条数据
	 * @param mediaId
	 * @return
	 */
	public MediaFiles getFileByMediaId(String mediaId);
}
