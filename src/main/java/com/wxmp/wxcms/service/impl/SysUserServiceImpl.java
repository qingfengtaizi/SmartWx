/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.service.impl;

import com.wxmp.wxcms.domain.SysUser;
import com.wxmp.wxcms.mapper.SysUserDao;
import com.wxmp.wxcms.service.SysUserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Service
public class SysUserServiceImpl  implements SysUserService{

	@Resource
	private SysUserDao sysUserDao;
	
	@Override
	public SysUser getSysUser(SysUser sysUser) {
		SysUser resUser = null;
		
		resUser = this.sysUserDao.getSysUser(sysUser);

		if(resUser != null){
			return resUser;
		}else{
			return null;
		}
	}


	/* (non-Javadoc)
	 * @see com.wxmp.backstage.sys.ISysUserService#getSysUserById(java.lang.String)
	 */
	@Override
	public SysUser getSysUserById(String userId) {
		SysUser resUser = null;
		
		resUser = this.sysUserDao.getSysUserById(userId);

		if(resUser != null){
			return resUser;
		}else{
			return null;
		}
	}


	@Override
	public int updateLoginPwd(SysUser sysUser) {
		int n = 0;
		try {
			sysUserDao.updateLoginPwd(sysUser);
			n = 1;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return n;
	}
}
