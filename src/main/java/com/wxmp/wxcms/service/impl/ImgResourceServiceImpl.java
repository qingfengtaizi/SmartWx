/*
 * FileName：ImgResourceServiceImpl.java 
 * <p>
 * Copyright (c) 2017-2020, <a href="http://www.webcsn.com">hermit (794890569@qq.com)</a>.
 * <p>
 * Licensed under the GNU General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl-3.0.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.wxmp.wxcms.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wxmp.core.common.Constants;
import com.wxmp.core.util.CommonUtil;
import com.wxmp.wxapi.process.MediaType;
import com.wxmp.wxcms.domain.ImgResource;
import com.wxmp.wxcms.domain.MediaFiles;
import com.wxmp.wxcms.domain.MsgBase;
import com.wxmp.wxcms.mapper.ImgResourceDao;
import com.wxmp.wxcms.mapper.MediaFilesDao;
import com.wxmp.wxcms.mapper.MsgBaseDao;
import com.wxmp.wxcms.service.ImgResourceService;

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
	@Resource
	private MsgBaseDao baseDao;
	
	@Override
	public ImgResource getImg(String id) {
		return imgResourceDao.getImgById(id);
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

		//添加base表
		MsgBase base = new MsgBase();
		base.setCreateTime(new Date());
		base.setMsgtype(MediaType.Image.name());
		baseDao.add(base);
		//添加到素材表中
		MediaFiles entity = new MediaFiles();
		entity.setMediaId(img.getMediaId());
		entity.setMediaType(MediaType.Image.name());
		entity.setBaseId(base.getId());
		entity.setCreateTime(new Date(System.currentTimeMillis()));
		entity.setUpdateTime(new Date(System.currentTimeMillis()));
		mediaFilesDao.add(entity);
		return img.getUrl();
	}

	@Override
	public List<ImgResource> getImgListByPage(ImgResource entity) {
		return imgResourceDao.getImgListByPage(entity);
	}
	
	@Override
	public boolean removeOtherToImg(String otherId) {
		return false;
	}


	@Override
	public boolean updateImgFlag(String id, Integer flag) {
		return false;
	}

	@Override
	public boolean delImg(String id) {
		ImgResource img = imgResourceDao.getImgById(id);
//		MsgBase base = new MsgBase();
//		base.setId(img.);
//		baseDao.delete(base);
		mediaFilesDao.deleteByMediaId(img.getMediaId());
		imgResourceDao.deleteByMediaId(img.getMediaId());
		
		return true;
	}



}
