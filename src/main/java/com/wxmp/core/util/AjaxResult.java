/*
 * FileName：AjaxResult.java 
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
package com.wxmp.core.util;

import com.wxmp.core.common.Constants;
import com.wxmp.core.page.Page;

/**
 * ajax返回结果对象
 * 
 * @author hermit
 * @date 2017 -06-10 14:11:14
 */
public class AjaxResult {
    /**
     * 成功标识：true/false
     */
    private boolean success = false;
    
    /**
     * 提示信息
     */
    private String msg = "";
    
    /**
     * 数据对象
     */
    private Object data = null;
    
    /**
     * 分页对象
     */
    private Page page = null;
    
//    /**
//     * 其他参数（map对象）
//     */
//    private Map<String, Object> attr = new HashMap<>();
    
    /**
     * 设置成功标识：true/false
     * 
     * @param success boolean类型
     * @author hermit
     * @date 2016 -10-09 08:59:55
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    /**
     * 设置提示信息
     *
     * @param msg string类型
     * @author hermit
     * @date 2016 -10-09 08:59:35
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    /**
     * 设置返回数据对象
     *
     * @param data object对象
     * @author hermit
     * @date 2016 -10-09 09:00:04
     */
    public void setData(Object data) {
        this.data = data;
    }
    
//    /**
//     * 其他参数（map对象）
//     *
//     * @return
//     * @author hermit
//     * @date 2016 -10-09 09:03:53
//     */
//    public Map<String, Object> getAttr() {
//        return attr;
//    }
    
    /**
     * 信息操作成功
     *
     * @return
     * @author hermit
     * @date 2016 -12-14 14:52:39
     */
    public static AjaxResult success() {
        AjaxResult result = new AjaxResult();
        result.setSuccess(true);
        result.setMsg(Constants.MSG_SUCCESS);
        return result;
    }
    
    /**
     * 信息操作成功
     *
     * @return
     * @author hermit
     * @date 2016 -12-14 14:52:39
     */
    public static AjaxResult success(Object data) {
        AjaxResult result = new AjaxResult();
        result.setSuccess(true);
        result.setData(data);
        result.setMsg(Constants.MSG_SUCCESS);
        return result;
    }

    /**
     * 信息操作成功
     *
     * @return
     * @author hermit
     * @date 2016 -12-14 14:52:39
     */
    public static AjaxResult success(Object data, String msg) {
        AjaxResult result = new AjaxResult();
        result.setSuccess(true);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }
    /**
     * 信息保存成功
     * 
     * @return
     * @author hermit
     * @date 2016 -12-14 14:52:39
     */
    public static AjaxResult saveSuccess() {
        AjaxResult result = new AjaxResult();
        result.setSuccess(true);
        result.setMsg(Constants.MSG_SUCCESS_SAVE);
        return result;
    }
    
    /**
     * 信息保存成功
     *
     * @return
     * @author hermit
     * @date 2016 -12-14 14:52:39
     */
    public static AjaxResult updateSuccess() {
        AjaxResult result = new AjaxResult();
        result.setSuccess(true);
        result.setMsg(Constants.MSG_SUCCESS_UPDATE);
        return result;
    }
    
    /**
     * 信息删除成功
     *
     * @return
     * @author hermit
     * @date 2016 -12-14 14:52:39
     */
    public static AjaxResult deleteSuccess() {
        AjaxResult result = new AjaxResult();
        result.setSuccess(true);
        result.setMsg(Constants.MSG_SUCCESS_DELETE);
        return result;
    }
    
    /**
     * 处理失败
     * 
     * @return
     */
    public static AjaxResult failure() {
        AjaxResult result = new AjaxResult();
        result.setSuccess(false);
        return result;
    }
    
    /**
     * 处理失败
     * 
     * @param msg 失败消息
     * @return
     */
    public static AjaxResult failure(String msg) {
        AjaxResult result = new AjaxResult();
        result.setSuccess(false);
        result.setMsg(msg);
        return result;
    }
    
    /**
     * 处理失败
     * 
     * @param msg 失败消息
     * @param data 数据
     * @return
     */
    public static AjaxResult failure(String msg, Object data) {
        AjaxResult result = new AjaxResult();
        result.setSuccess(false);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    
    public Page getPage() {
        return page;
    }
    
    public void setPage(Page page) {
        this.page = page;
    }
    
    public boolean getSuccess() {
        return success;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public Object getData() {
        return data;
    }
}
