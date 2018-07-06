/*
 * FileName：SysConfigServiceImpl.java 
 * <p>
 * Copyright (c) 2017-2020, <a href="http://www.webcsn.com">hermit (794890569@qq.com)</a>.
 * <p>
 * Licensed under the GNU General Public License, Version 3 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl-3.0.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
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
