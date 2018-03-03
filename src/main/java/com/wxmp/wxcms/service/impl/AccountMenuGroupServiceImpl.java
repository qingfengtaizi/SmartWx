package com.wxmp.wxcms.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.wxmp.core.page.Pagination;
import com.wxmp.wxcms.domain.AccountMenu;
import com.wxmp.wxcms.domain.AccountMenuGroup;
import com.wxmp.wxcms.mapper.AccountMenuDao;
import com.wxmp.wxcms.mapper.AccountMenuGroupDao;
import com.wxmp.wxcms.service.AccountMenuGroupService;

import javax.annotation.Resource;


@Service
public class AccountMenuGroupServiceImpl implements AccountMenuGroupService{

	@Resource
	private AccountMenuGroupDao entityDao;

	@Resource
	private AccountMenuDao menuDao;

	public AccountMenuGroup getById(String id){
		return entityDao.getById(id);
	}

	public List<AccountMenuGroup> list(AccountMenuGroup searchEntity){
		return entityDao.list(searchEntity);
	}

	public Pagination<AccountMenuGroup> paginationEntity(AccountMenuGroup searchEntity ,Pagination<AccountMenuGroup> pagination){
		Integer totalItemsCount = entityDao.getTotalItemsCount(searchEntity);
		List<AccountMenuGroup> items = entityDao.paginationEntity(searchEntity,pagination);
		pagination.setTotalItemsCount(totalItemsCount);
		pagination.setItems(items);
		return pagination;
	}

	public void add(AccountMenuGroup entity){
		entityDao.add(entity);
	}

	public void update(AccountMenuGroup entity){
		entityDao.update(entity);
	}

	public void delete(AccountMenuGroup entity){
		entityDao.deleteAllMenu(entity);
		entityDao.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.wxmp.wxcms.service.AccountMenuGroupService#deleteById(long)
	 */
	@Override
	public void deleteById(long id) {
		AccountMenu searchEntity = new AccountMenu();
		searchEntity.setGid(id);
		List<AccountMenu> menuList = menuDao.listForPage(searchEntity);
		if(menuList != null && menuList.size() > 0){
				//删除菜单组
				entityDao.deleteGroupById(id);
				//删除菜单
				entityDao.deleteMenuByGId(id);
		}else{
			entityDao.deleteGroupById(id);
		}
	}
}

