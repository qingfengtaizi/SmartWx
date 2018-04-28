/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wxmp.wxcms.domain.SysConfig;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
public interface SysConfigDao {

	/**
	 * 获取所有数据
	 */
	List<SysConfig> getConfigList();

	/**
	 * 更新数据库信息
	 */
	boolean update(@Param("key") String key, @Param("value") String value);

	/**
	 * 获取单一
	 */
	String getValue(@Param("key") String key);

	/**
	 * 查询数据库版本
	 */
	public String getMysqlVsesion();
}
