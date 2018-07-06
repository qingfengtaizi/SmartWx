/*
 * FileName：BaseCtrl.java 
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
package com.wxmp.core.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.wxmp.core.page.Page;
import com.wxmp.core.util.AjaxResult;
import com.wxmp.core.util.StringUtil;

/**
 * Controller基础公共类
 *
 * @author hermit
 * @date 2017 -06-28 20:41:23
 */
public class BaseCtrl {
    
    @Resource
    protected HttpServletRequest request;
    
    @Resource
    protected HttpServletResponse response;
    
    /**
     * 初始化属性，在每个方法执行前执行该赋值方法
     *
     * @param request
     * @param response
     * @author hermit
     * @date 2017 -10-17 15:57:10
     */
    @ModelAttribute
    public void setAttribute(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public AjaxResult getResult(Page page, Object data){
        AjaxResult result = new AjaxResult();
        Page newPage = new Page();
        newPage.setPage(page.getPage());
        newPage.setPageSize(page.getPageSize());
        newPage.setTotal(page.getTotal());
        newPage.setTotalPage(page.getTotalPage());
        result.setPage(newPage);
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    protected static boolean validate(HttpServletRequest request, String validateCode) {
        if(StringUtil.isBlank(validateCode)) {
            return false;
        } else {
            String code = (String)request.getSession().getAttribute("adminVerifyCode");
            return validateCode.toUpperCase().equals(code);
        }
    }

//    /**
//     * 获取用户
//     * @return
//     */
//    public SysUser getUser() {
//        return SessionUtil.getUser();
//    }
}
