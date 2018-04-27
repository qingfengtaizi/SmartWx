/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.service;

import com.wxmp.wxcms.domain.SysUser;


/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
public interface SysUserService {

	public SysUser getSysUser(SysUser sysUser);
	
	public SysUser getSysUserById(String userId);
	
	public int updateLoginPwd(SysUser sysUser);
}
