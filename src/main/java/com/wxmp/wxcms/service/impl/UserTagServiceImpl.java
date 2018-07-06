/*
 * FileName：UserTagServiceImpl.java 
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

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wxmp.core.exception.WxErrorException;
import com.wxmp.wxapi.process.HttpMethod;
import com.wxmp.wxapi.process.MpAccount;
import com.wxmp.wxapi.process.WxApi;
import com.wxmp.wxapi.process.WxApiClient;
import com.wxmp.wxcms.domain.UserTag;
import com.wxmp.wxcms.mapper.UserTagDao;
import com.wxmp.wxcms.service.UserTagService;

@Service
public class UserTagServiceImpl implements UserTagService {

	@Resource
	private UserTagDao userTagDao;
	
	private Logger logger=Logger.getLogger(UserTagServiceImpl.class);
	
	@Override
	public UserTag getById(Integer id) {
		return userTagDao.getById(id);
	}

	@Override
	public List<UserTag> listForPage(UserTag searchEntity) {
		return userTagDao.getUserTagListByPage(searchEntity);
	}

	@Override
	public void add(UserTag entity) {
		userTagDao.add(entity);
	}

	@Override
	public void update(UserTag entity) {
		userTagDao.update(entity);

	}

	@Override
	public void delete(UserTag entity) {
		userTagDao.delete(entity);
	}


	@Override
	public Integer deleteBatchIds(String[] ids) {
		return userTagDao.deleteBatchIds(ids);
	}


	@Override
	public Integer getMaxId() {
		return userTagDao.getMaxId();
	}

}
