package com.wxmp.wxcms.service.impl;

import com.wxmp.wxcms.domain.AccountMenu;
import com.wxmp.wxcms.mapper.AccountMenuDao;
import com.wxmp.wxcms.service.AccountMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : hermit
 */
@Service
public class AccountMenuServiceImpl implements AccountMenuService{

	@Resource
	private AccountMenuDao entityDao;

	public AccountMenu getById(String id){
		return entityDao.getById(id);
	}

	public List<AccountMenu> listForPage(AccountMenu searchEntity){
		return entityDao.listForPage(searchEntity);
	}

	public List<AccountMenu> listParentMenu(){
		return entityDao.listParentMenu();
	}
	
	public void add(AccountMenu entity){
		entityDao.add(entity);
	}

	public void update(AccountMenu entity){
		entityDao.update(entity);
	}

	public void delete(AccountMenu entity){
		entityDao.delete(entity);
	}



}