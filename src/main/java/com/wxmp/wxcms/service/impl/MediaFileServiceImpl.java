package com.wxmp.wxcms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wxmp.wxcms.domain.MediaFiles;
import com.wxmp.wxcms.mapper.MediaFilesDao;
import com.wxmp.wxcms.mapper.MsgBaseDao;
import com.wxmp.wxcms.service.MediaFileService;

@Service
@Transactional
public class MediaFileServiceImpl implements MediaFileService {

	@Resource
	private MediaFilesDao mediaFilesDao;
	@Resource
	private MsgBaseDao baseDao;
	
	@Override
	public void add(MediaFiles entity) {
		// TODO Auto-generated method stub
		//需要对base表添加数据
		mediaFilesDao.add(entity);
	}

	@Override
	public void deleteByMediaId(String mediaId) {
		// TODO Auto-generated method stub
		mediaFilesDao.deleteByMediaId(mediaId);
	}

	@Override
	public MediaFiles getFileByMediaId(String mediaId) {
		// TODO Auto-generated method stub
		return mediaFilesDao.getFileByMediaId(mediaId);
	}

	@Override
	public List<MediaFiles> getMediaListByPage(MediaFiles entity) {
		// TODO Auto-generated method stub
		return mediaFilesDao.getMediaListByPage(entity);
	}

}
