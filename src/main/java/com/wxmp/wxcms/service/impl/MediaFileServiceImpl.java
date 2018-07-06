/*
 * FileName：MediaFileServiceImpl.java 
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

import com.wxmp.wxapi.process.MsgType;
import com.wxmp.wxcms.domain.MediaFiles;
import com.wxmp.wxcms.domain.MsgBase;
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
		MsgBase base = new MsgBase();
		base.setCreateTime(new Date());
		base.setMsgtype(entity.getMediaType());
		baseDao.add(base);
		//关联回复表
		entity.setBaseId(base.getId());
		//需要对base表添加数据
		mediaFilesDao.add(entity);
	}

	@Override
	public void deleteByMediaId(String mediaId) {
		MediaFiles media = mediaFilesDao.getFileByMediaId(mediaId);
		MsgBase base = new MsgBase();
		base.setId(media.getBaseId());
		baseDao.delete(base);
		mediaFilesDao.deleteByMediaId(mediaId);
	}

	@Override
	public MediaFiles getFileByMediaId(String mediaId) {
		return mediaFilesDao.getFileByMediaId(mediaId);
	}

	@Override
	public List<MediaFiles> getMediaListByPage(MediaFiles entity) {
		return mediaFilesDao.getMediaListByPage(entity);
	}

}
