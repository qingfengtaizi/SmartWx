/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wxmp.wxcms.domain.SysConfig;


/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
public interface SysConfigService {

    public List<SysConfig> getConfigList();
    
    public Map<String, String> getSysConfigToMap();
    
    public String getValue(String key);
    
    public void update(Map<String, String> params, HttpServletRequest request);
    
    /**
     * 查询数据库版本
     */
    public String getMysqlVsesion();

}
