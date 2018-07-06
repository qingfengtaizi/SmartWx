/*
 * FileName：QiniuUtil.java 
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiniuUtil {

    private static Logger logger = Logger.getLogger(QiniuUtil.class);

    // Zone对象
    /**
     * Zone.zone0() =>华东
     * Zone.zone1() =>华北
     * Zone.zone2() =>华南
     * Zone.zoneNa0() =>北美
     */
    private static Zone zone = Zone.zone1();

    private static UploadManager uploadManager = new UploadManager(new Configuration(zone));

    private static String ak = "emLrorprCtWbof9kWlr6nD4Vkb_wB934WxqbCDzc";

    private static String sk = "BTnrYlSVC78t38Ivnmf4tIbvLSsbbGEDIc1T2Be";

    private static String bt = "e-smart1";

    /**
     * 
    *
    * @Title: uploadByFile 
    * @Description: TODO 上传文件到七牛 
    * @param @param file
    * @param @return    设定文件 
    * @return QiniuResult    返回类型 
    * @throws
     */
    public static QiniuResult uploadByFile(File file) {

        try {

            return uploadByByte(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 
    *
    * @Title: uploadByByte 
    * @Description: TODO 上传数组到七牛
    * @param @param bte
    * @param @return    设定文件 
    * @return QiniuResult    返回类型 
    * @throws
     */
    public static QiniuResult uploadByByte(byte[] bte) {
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(ak, sk);
        String upToken = auth.uploadToken(bt);
        try {
            Response response = uploadManager.put(bte, key, upToken);
            // 解析上传成功的结果
            logger.info("qiniu Response body=> " + response.bodyString());
            return QiniuResult.forBoject(response);
        } catch (QiniuException e) {
            e.getStackTrace();
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 
    *
    * @Title: uploadByInputStream 
    * @Description: TODO 上传文件流到七牛 
    * @param @param inp
    * @param @return    设定文件 
    * @return QiniuResult    返回类型 
    * @throws
     */
    public static QiniuResult uploadByInputStream(InputStream inp) {

        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(ak, sk);
        String upToken = auth.uploadToken(bt);
        try {
            Response response = uploadManager.put(inp, key, upToken, null, null);
            // 解析上传成功的结果
            logger.info("qiniu Response body=> " + response.bodyString());
            return QiniuResult.forBoject(response);
        } catch (QiniuException e) {
            e.getStackTrace();
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] args) {
        File file = new File("D:\\test.jpg");
        System.out.println(JSONObject.toJSON(uploadByFile(file)).toString());
    }

}
