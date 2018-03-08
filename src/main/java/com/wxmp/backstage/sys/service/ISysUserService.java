package com.wxmp.backstage.sys.service;

import com.wxmp.backstage.sys.domain.SysUser;


/***
 * 
 * @title : 
 * @description : 
 * @projectname : wxmp
 * @classname : ISysUserService
 * @version 1.0
 * @author : sw_jee_dev
 * @createtime : 2017年4月2日 上午10:04:02
 */
public interface ISysUserService {


	

	/**
	 * 帐号是否存在
	 * @param account
	 * @return
	 */
	public boolean isAccount(String account);
	
	public SysUser getSysUser(SysUser sysUser);
	
	public SysUser getSysUserById(String userId);
	
	public int updateLoginPwd(SysUser sysUser);
	
}
