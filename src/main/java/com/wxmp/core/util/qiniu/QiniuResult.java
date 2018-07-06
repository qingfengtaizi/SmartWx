/*
 * FileName：QiniuResult.java 
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
package com.wxmp.core.util.qiniu;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

public class QiniuResult {

    private int code;// 状态码 200表示成功

    private String msg;// 信息

    private double duration;// 耗时

    private String address;// 服务器ip地址

    private String hash;

    private String key;

    private String contentType;

    private String url;// 图片调用地址

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static QiniuResult forBoject(Response res) throws QiniuException {
        QiniuResult result = new QiniuResult();
        result.setAddress(res.address);
        result.setCode(res.statusCode);
        result.setContentType(res.contentType());
        result.setDuration(res.duration);
        JSONObject json = JSONObject.parseObject(res.bodyString());

        result.setHash(json.getString("hash"));
        result.setKey(json.getString("key"));

        result.setMsg(res.error);
        //
        result.setUrl("http://oyeve9iiu.bkt.clouddn.com/" + json.getString("key"));
        return result;
    }

}
