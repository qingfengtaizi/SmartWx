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
