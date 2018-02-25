package com.wxmp.wxcms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wxmp.wxcms.domain.AccountMenu;
import com.wxmp.wxcms.mapper.AccountMenuDao;
import com.wxmp.wxcms.service.AccountMenuService;


@Service
public class AccountMenuServiceImpl implements AccountMenuService{

	@Autowired
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