/*
 * FileName：JsonView.java 
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
package com.wxmp.core.spring;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSONObject;

public class JsonView {

    // 错误代码 0-成功
    private Integer errcode = 0;

    // 消息
    private String message;

    // 数据
    private Object data;

    public JsonView(Integer errcode, String message) {
        this.errcode = errcode;
        this.message = message;
    }

    public JsonView(Integer errcode) {
        this.errcode = errcode;
    }

    public JsonView() {
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public String toIso8859String() throws UnsupportedEncodingException {
        return new String(this.toString().getBytes(), "ISO-8859-1");
    }

}
