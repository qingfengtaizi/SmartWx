/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.service.impl;

import com.wxmp.core.common.Constants;
import com.wxmp.wxcms.domain.ImgResource;
import com.wxmp.wxcms.domain.MediaFiles;
import com.wxmp.wxcms.mapper.ImgResourceDao;
import com.wxmp.wxcms.mapper.MediaFilesDao;
import com.wxmp.wxcms.service.ImgResourceService;
import com.wxmp.core.util.CommonUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.wxmp.core.util.DateUtilOld.COMMON_FULL;

import java.util.Date;

import javax.annotation.Resource;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Service
@Transactional
public class ImgResourceServiceImpl implements ImgResourceService {

	@Resource
    private ImgResourceDao imgResourceDao;
	@Resource
	private MediaFilesDao mediaFilesDao;
	
	@Override
	public ImgResource getImg(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addImg(ImgResource img) {
		img.setFlag(Constants.IMG_FLAG0);
		img.setCreateTime(new Date(System.currentTimeMillis()));
		img.setUpdateTime(img.getCreateTime());
		//主键id
		String id = CommonUtil.getUID();
		img.setId(id);
		imgResourceDao.add(img);
		//添加到素材表中
		MediaFiles entity = new MediaFiles();
		entity.setMediaId(img.getMediaId());
		entity.setMediaType("image");
		entity.setCreateTime(new Date(System.currentTimeMillis()));
		entity.setUpdateTime(new Date(System.currentTimeMillis()));
		mediaFilesDao.add(entity);
		return img.getUrl();
	}


	@Override
	public boolean removeOtherToImg(String otherId) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean updateImgFlag(String id, Integer flag) {
		// TODO Auto-generated method stub
		return false;
	}

}
