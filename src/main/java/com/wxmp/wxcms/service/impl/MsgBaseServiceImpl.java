package com.wxmp.wxcms.service.impl;

import com.wxmp.wxcms.domain.MsgBase;
import com.wxmp.wxcms.mapper.MsgBaseDao;
import com.wxmp.wxcms.service.MsgBaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : hermit
 */
@Service
public class MsgBaseServiceImpl implements MsgBaseService{

	@Resource
	private MsgBaseDao entityDao;

	public MsgBase getById(String id){
		return entityDao.getById(id);
	}

	public List<MsgBase> listForPage(MsgBase searchEntity){
		return entityDao.listForPage(searchEntity);
	}

	public void add(MsgBase entity){
		entityDao.add(entity);
	}

	public void update(MsgBase entity){
		entityDao.update(entity);
	}

	public void delete(MsgBase entity){
		entityDao.delete(entity);
	}



}