/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.wxmp.wxcms.domain.SysConfig;
import com.wxmp.wxcms.mapper.SysConfigDao;
import com.wxmp.wxcms.service.SysConfigService;
import org.springframework.stereotype.Service;


/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {
    @Resource
    private SysConfigDao configDao;


    @Override
    public List<SysConfig> getConfigList() {
        return configDao.getConfigList();
    }

    @Override
    public Map<String, String> getSysConfigToMap() {
        List<SysConfig> allList = this.getConfigList();
        Map<String,String> map = new HashMap<>();
        for (SysConfig config : allList) {
            map.put(config.getJkey(),config.getJvalue());
        }
        return map;
    }

    @Override
    public String getValue(String key) {
        return configDao.getValue(key);
    }

    @Override
    public void update(Map<String, String> params, HttpServletRequest request) {
        for(Map.Entry entry : params.entrySet()){
                configDao.update((String)entry.getKey(),(String)entry.getValue());
            }
    }
    @Override
    public String getMysqlVsesion() {
        return configDao.getMysqlVsesion();
    }
}
