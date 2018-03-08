package com.wxmp.backstage.sys.mapper;

import com.wxmp.backstage.sys.domain.SysUser;

/** 
 * @title : 
 * @description : 
 * @projectname : wxmp
 * @classname : SysUserDao
 * @version 1.0
 * @author : hermit
 * @createtime : 2017年5月9日 下午5:40:50
*/
public interface SysUserDao {

	/**
	 * 根据用户名密码查询
	 * @param sysUser
	 * @return
	 */
    public SysUser getSysUser(SysUser sysUser);
    
	/**
	 * 根据用户名密码查询
	 * @param sysUser
	 * @return
	 */
    public SysUser getSysUserById(String userId);
    
    /**
     * 修改登录密码
     * @param sysUser
     */
    public void updateLoginPwd(SysUser sysUser);
}
