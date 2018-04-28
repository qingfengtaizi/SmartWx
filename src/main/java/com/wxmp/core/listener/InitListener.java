/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.core.listener;

import com.wxmp.core.spring.SpringContextHolder;
import com.wxmp.core.util.CacheUtils;
import com.wxmp.wxapi.process.WxMemoryCacheClient;
import com.wxmp.wxcms.domain.Account;
import com.wxmp.wxcms.domain.SysConfig;
import com.wxmp.wxcms.service.AccountService;
import com.wxmp.wxcms.service.SysConfigService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
public class InitListener implements ServletContextListener {
    
    public InitListener() {
    }
    
    public void contextInitialized(ServletContextEvent sce) {
        try {
            //放入公众号
            AccountService accountService = SpringContextHolder.getBean("accountService");
            Account account = accountService.getSingleAccount();
            WxMemoryCacheClient.addMpAccount(account);
            //读取所有缓存
            SysConfigService configService = SpringContextHolder.getBean("sysConfigService");
            List<SysConfig> configList = configService.getConfigList();
            for (SysConfig config : configList) {
                CacheUtils.put(config.getJkey().toUpperCase(), config.getJvalue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
