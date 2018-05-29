/*
 * FileName：WxApi.java 
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
package com.wxmp.wxapi.process;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.wxmp.core.common.Identities;
import com.wxmp.core.exception.WxError;
import com.wxmp.core.exception.WxErrorException;
import com.wxmp.core.util.HttpClientUtils;
import com.wxmp.core.util.MyTrustManager;

/**
 * 微信 API、微信基本接口
 * 
 */

public class WxApi {

    // token 接口
    public static final String TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    // 创建菜单
    public static final String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

    // 创建个性化菜单
    public static final String MENU_ADDCONDITIONAL = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=%s";

    // 删除菜单
    public static final String MENU_DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s";

    // 获取账号粉丝信息
    public static final String GET_FANS_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    // 获取账号粉丝列表
    public static final String GET_FANS_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s";

    // 获取批量素材
    public static final String GET_BATCH_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=%s";

    // 上传多媒体资料接口-临时
    public static final String UPLOAD_MEDIA = "http://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";

    // 上传永久素材：图文-临时
    public static final String UPLOAD_NEWS = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=%s";

    // 群发接口
    public static final String MASS_SEND = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=%s";

    // 网页授权OAuth2.0获取code
    public static final String GET_OAUTH_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=%s&scope=%s&state=%s#wechat_redirect";

    // 网页授权OAuth2.0获取token
    public static final String GET_OAUTH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    // 网页授权OAuth2.0获取用户信息
    public static final String GET_OAUTH_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    // 生成二维码
    public static final String CREATE_QRCODE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";

    // 根据ticket获取二维码图片
    public static final String SHOW_QRCODE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s";

    // js ticket
    public static final String GET_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

    // 发送客服消息
    public static final String SEND_CUSTOM_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";

    // 模板消息接口
    public static final String SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

    // 微信服务器ip
    public static final String CALLBACKIP = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=%s";

    // 统一下单订购接口
    public static final String PAY_UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    // 上传永久图片素材
    public static final String UPLOAD_MATERIAL_IMG = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=%s";

    // 新增其他类型永久素材
    public static final String ADD_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=%s&type=%s";

    // 新增永久图文素材
    public static final String ADD_NEWS_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=%s";

    // 根据media_id来获取永久素材
    public static final String GET_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=%s";
    //获取临时素材  GET
    public static final String GET_MEDIA="https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

    // 根据media_id来删除永久图文素材
    public static final String DELETE_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=%s";

    // 修改永久图文url
    public static final String UPDATE_NEWS_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=%s";

    //素材文件后缀
    public static Map<String,String> type_fix= new HashMap<>();
    public static Map<String,String> media_fix= new HashMap<>();
    //素材文件大小
    public static Map<String,Long> type_length= new HashMap<>();
    static{
        type_fix.put("image","bmp|png|jpeg|jpg|gif");
        type_fix.put("voice","mp3|wma|wav|amr");
        type_fix.put("video","mp4");
        type_fix.put("thumb","jpg");
        media_fix.put("image","png|jpeg|jpg|gif");
        media_fix.put("voice","mp3|amr");
        media_fix.put("video","mp4");
        media_fix.put("thumb","jpg");
        type_length.put("image",new Long(2*1024*1024));
        type_length.put("voice",new Long(2*1024*1024));
        type_length.put("video",new Long(10*1024*1024));
        type_length.put("thumb",new Long(64*1024));
    }

    // 获取token接口
    public static String getTokenUrl(String appId, String appSecret) {
        return String.format(TOKEN, appId, appSecret);
    }

    // 获取上传Media接口
    public static String getUploadMediaUrl(String token, String type) {
        return String.format(UPLOAD_MEDIA, token, type);
    }

    // 获取菜单创建接口
    public static String getMenuCreateUrl(String token) {
        return String.format(MENU_CREATE, token);
    }

    // 获取个性化菜单创建接口
    public static String getMenuAddconditionalUrl(String token) {
        return String.format(MENU_ADDCONDITIONAL, token);
    }

    // 获取菜单删除接口
    public static String getMenuDeleteUrl(String token) {
        return String.format(MENU_DELETE, token);
    }

    // 获取粉丝信息接口
    public static String getFansInfoUrl(String token, String openid) {
        return String.format(GET_FANS_INFO, token, openid);
    }

    // 获取粉丝列表接口
    public static String getFansListUrl(String token, String nextOpenId) {
        if (nextOpenId == null) {
            return String.format(GET_FANS_LIST, token);
        } else {
            return String.format(GET_FANS_LIST + "&next_openid=%s", token, nextOpenId);
        }
    }

    // 获取素材列表接口
    public static String getBatchMaterialUrl(String token) {
        return String.format(GET_BATCH_MATERIAL, token);
    }

    // 获取上传图文消息接口
    public static String getUploadNewsUrl(String token) {
        return String.format(UPLOAD_NEWS, token);
    }

    // 群发接口
    public static String getMassSendUrl(String token) {
        return String.format(MASS_SEND, token);
    }

    // 网页授权OAuth2.0获取code
    public static String getOAuthCodeUrl(String appId, String redirectUrl, String scope, String state) {
        return String.format(GET_OAUTH_CODE, appId, urlEnodeUTF8(redirectUrl), "code", scope, state);
    }

    // 网页授权OAuth2.0获取token
    public static String getOAuthTokenUrl(String appId, String appSecret, String code) {
        return String.format(GET_OAUTH_TOKEN, appId, appSecret, code);
    }

    // 网页授权OAuth2.0获取用户信息
    public static String getOAuthUserinfoUrl(String token, String openid) {
        return String.format(GET_OAUTH_USERINFO, token, openid);
    }

    // 获取创建二维码接口url
    public static String getCreateQrcodeUrl(String token) {
        return String.format(CREATE_QRCODE, token);
    }

    // 获取显示二维码接口
    public static String getShowQrcodeUrl(String ticket) {
        return String.format(SHOW_QRCODE, ticket);
    }

    // 获取js ticket url
    public static String getJsApiTicketUrl(String token) {
        return String.format(GET_JSAPI_TICKET, token);
    }

    // 获取发送客服消息 url
    public static String getSendCustomMessageUrl(String token) {
        return String.format(SEND_CUSTOM_MESSAGE, token);
    }

    // 获取发送模板消息 url
    public static String getSendTemplateMessageUrl(String token) {
        return String.format(SEND_TEMPLATE_MESSAGE, token);
    }

    // 获取永久素材
    public static String getMaterial(String token) {
        return String.format(GET_MATERIAL, token);
    }

    // 删除永久图文素材
    public static String getDelMaterialURL(String token) {
        return String.format(DELETE_MATERIAL, token);
    }

    // 获取统一下单接口地址
    public static String getUnifiedOrderUrl() {
        return String.format(PAY_UNIFIEDORDER);
    }

    // 获取微信服务器ip地址（验证码token是否过期）
    public static String getCallbackIpUrl(String token) {
        return String.format(CALLBACKIP, token);
    }

    // 获取新增图文素材url
    public static String getNewsMaterialUrl(String token) {
        return String.format(ADD_NEWS_MATERIAL, token);
    }

    // 获取修改图文素材url
    public static String getUpdateNewsMaterialUrl(String token) {
        return String.format(UPDATE_NEWS_MATERIAL, token);
    }

    // 上传永久图片素材
    public static String getMaterialImgUrl(String token) {
        return String.format(UPLOAD_MATERIAL_IMG, token);
    }

    // 获取新增素材url
    public static String getMaterialUrl(String token, String type) {
        return String.format(ADD_MATERIAL, token, type);
    }


    /**
     * 获取创建临时二维码post data
     * @param expireSecodes 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
     * @param scene 临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000)
     * @return
     */
    public static String getQrcodeJson(Integer expireSecodes, Integer scene) {
        String postStr = "{\"expire_seconds\":%d,\"action_name\":\"QR_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":%d}}";
        return String.format(postStr, expireSecodes, scene);
    }

    /**
     * 获取创建临时二维码post data
     * @param scene 临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000)
     * @return
     */
    public static String getQrcodeLimitJson(Integer scene) {
        String postStr = "{\"action_name\":\"QR_LIMIT_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":%d}}";
        return String.format(postStr, scene);
    }

    // 获取永久二维码
    public static String getQrcodeLimitJson(String sceneStr) {
        String postStr = "{\"action_name\":\"QR_LIMIT_STR_SCENE\",\"action_info\":{\"scene\":{\"scene_str\":%s}}";
        return String.format(postStr, sceneStr);
    }

    // 获取接口访问凭证
    public static AccessToken getAccessToken(String appId, String appSecret) throws WxErrorException {
        AccessToken token = null;
        String tockenUrl = WxApi.getTokenUrl(appId, appSecret);
        JSONObject jsonObject = httpsRequest(tockenUrl, HttpMethod.GET, null);
        if (null != jsonObject && !jsonObject.containsKey("errcode")) {
            token = new AccessToken();
            token.setAccessToken(jsonObject.getString("access_token"));
            token.setExpiresIn(jsonObject.getIntValue("expires_in"));
        } else if (null != jsonObject) {
            throw new WxErrorException(WxError.fromJson(jsonObject));
        }
        return token;
    }

    // 获取微信服务器ip
    public static boolean getCallbackIp(String token) {
        String tockenUrl = WxApi.getCallbackIpUrl(token);
        JSONObject jsonObject = httpsRequest(tockenUrl, HttpMethod.GET, null);
        if (null != jsonObject && !jsonObject.containsKey("errcode")) {
            return true;
        }
        return false;
    }

    // 获取js ticket
    public static JSTicket getJSTicket(String token) throws WxErrorException{
        JSTicket jsTicket = null;
        String jsTicketUrl = WxApi.getJsApiTicketUrl(token);
        JSONObject jsonObject = httpsRequest(jsTicketUrl, HttpMethod.GET, null);
        if (null != jsonObject && jsonObject.containsKey("errcode") && jsonObject.getIntValue("errcode") == 0) {
            try {
                jsTicket = new JSTicket();
                jsTicket.setTicket(jsonObject.getString("ticket"));
                jsTicket.setExpiresIn(jsonObject.getIntValue("expires_in"));
            } catch (JSONException e) {
                jsTicket = null;// 获取token失败
            }
        } else if (null != jsonObject) {
            throw new WxErrorException(WxError.fromJson(jsonObject));
        }
        return jsTicket;
    }

    // 获取OAuth2.0 Token
    public static OAuthAccessToken getOAuthAccessToken(String appId, String appSecret, String code) throws WxErrorException{
        OAuthAccessToken token = null;
        String tockenUrl = getOAuthTokenUrl(appId, appSecret, code);
        JSONObject jsonObject = httpsRequest(tockenUrl, HttpMethod.GET, null);
        if (null != jsonObject && !jsonObject.containsKey("errcode")) {
            try {
                token = new OAuthAccessToken();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getIntValue("expires_in"));
                token.setOpenid(jsonObject.getString("openid"));
                token.setScope(jsonObject.getString("scope"));
            } catch (JSONException e) {
                token = null;// 获取token失败
            }
        } else if (null != jsonObject) {
            throw new WxErrorException(WxError.fromJson(jsonObject));
        }
        return token;
    }

    /**
     * 上传多媒体文件
     * 返回media_id
     */
    public static String uploadMedia(String accessToken, String mediaType, String mediaUri) {
        String uploadMediaUrl = String.format(UPLOAD_MEDIA, accessToken, mediaType);
        String boundary = "----------" + System.currentTimeMillis();// 设置边界
        try {
            URL uploadUrl = new URL(uploadMediaUrl);
            HttpURLConnection uploadConn = (HttpURLConnection) uploadUrl.openConnection();
            uploadConn.setDoOutput(true);
            uploadConn.setDoInput(true);
            uploadConn.setRequestMethod("POST");
            // 设置请求头Content-Type
            uploadConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            // 获取媒体文件上传的输出流（往微信服务器写数据）
            OutputStream outputStream = uploadConn.getOutputStream();

            URL mediaUrl = new URL(mediaUri);
            HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl.openConnection();
            meidaConn.setDoOutput(true);
            meidaConn.setRequestMethod("GET");

            // 从请求头中获取内容类型
            String contentType = meidaConn.getHeaderField("Content-Type");
            // 根据内容类型判断文件扩展名
            String fileExt = ".jpg";
            // 请求体开始
            outputStream.write(("--" + boundary + "\r\n").getBytes());
            outputStream.write(
                    String.format("Content-Disposition: form-data; name=\"media\"; filename=\"file1%s\"\r\n", fileExt)
                            .getBytes());
            outputStream.write(String.format("Content-Type: %s\r\n\r\n", contentType).getBytes());

            // 获取媒体文件的输入流（读取文件）
            BufferedInputStream bis = new BufferedInputStream(meidaConn.getInputStream());
            byte[] buf = new byte[8096];
            int size = 0;
            while ((size = bis.read(buf)) != -1) {
                outputStream.write(buf, 0, size);
            }
            // 请求体结束
            outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
            outputStream.close();
            bis.close();
            meidaConn.disconnect();

            // 获取媒体文件上传的输入流（从微信服务器读数据）
            InputStream inputStream = uploadConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            uploadConn.disconnect();
            // 使用JSON-lib解析返回结果
            JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
            if (jsonObject.containsKey("media_id"))
                return jsonObject.getString("media_id");
            return null;
        } catch (Exception e) {
            String error = String.format("上传媒体文件失败：%s", e);
        }
        return null;
    }

    /**
     * 新增图文永久素材
     * @param materialUri
     * @param filePath
     * @return
     * @throws Exception
     */
    public static JSONObject addMaterial(String materialUri, String filePath) throws Exception {
        String result = null;
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            try {
                throw new IOException("文件不存在");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /**
        * 第一部分
        */
        URL urlObj = new URL(materialUri);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
            JSONObject jsonObject = JSONObject.parseObject(result.toString());

            if (jsonObject != null) {
                return jsonObject;
            }
        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
            throw new IOException("数据读取异常");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return null;
    }
    /**
     *  永久素材添加-不包含图文
     * @param accessToken 微信token
     * @param type 素材类型（image/voice/video/thumb）
     * @param fileUrl 文件的绝对路径
     * @param params 视频数据
     * @return
     * @throws WxErrorException
     */
    public static JSONObject addMateria(String accessToken,String type,String fileUrl,Map<String,String> params) throws WxErrorException{

        File file =new File(fileUrl);
        if(!file.exists()){
            throw new WxErrorException(WxError.newBuilder().setErrorCode(-2).setErrorMsg("文件不存在").build());
        }
        String fileName=file.getName();
        //获取后缀名
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        long length=file.length();
        //此处做判断是为了尽可能的减少对微信API的调用次数
        if(type_fix.get(type).indexOf(suffix)==-1){
            throw new WxErrorException(WxError.newBuilder().setErrorCode(40005).setErrorMsg("不合法的文件类型").build());
        }
        if(length>type_length.get(type)){
            throw new WxErrorException(WxError.newBuilder().setErrorCode(40006).setErrorMsg("不合法的文件大小").build());
        }
        String url=String.format(ADD_MATERIAL, accessToken,type);
        String result = HttpClientUtils.sendHttpPost(url, file, params);
        WxError wxError = WxError.fromJson(result);
        if(wxError.getErrorCode()!=0){
            throw new WxErrorException(wxError);
        }

        return JSONObject.parseObject(result);
    }

    /**
     *  永久素材下载-包含图片、语音、缩略图
     * @param accessToken 微信token
     * @param mediaId 多媒体素材ID
     * @param file 文件夹目录 例如D://down
     * @return
     * @throws WxErrorException
     */
    public static File downlodMateria(String accessToken, String mediaId, File file)
        throws WxErrorException {
        String url = String.format(GET_MATERIAL, accessToken);
        Map map = new HashMap();
        map.put("media_id", mediaId);
        Object obj = HttpClientUtils.sendHttpPostFile(url, map, file);
        if (obj instanceof String) {
            WxError wxError = WxError.fromJson((String)obj);
            throw new WxErrorException(wxError);
        }
        if (null == obj) {
            throw new WxErrorException(WxError.newBuilder().setErrorCode(-3).setErrorMsg("下载出错").build());
        }
        
        return (File)obj;
    }
    /**
     *  临时素材添加
     * @param accessToken 微信token
     * @param type 素材类型（image/voice/video/thumb）
     * @param fileUrl 文件的绝对路径
     * @return
     * @throws WxErrorException
     */
    public static JSONObject addMedia(String accessToken,String type,String fileUrl) throws WxErrorException{

        File file =new File(fileUrl);
        if(!file.exists()){
            throw new WxErrorException(WxError.newBuilder().setErrorCode(-2).setErrorMsg("文件不存在").build());
        }
        String fileName=file.getName();
        //获取后缀名
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        //文件大小，单位：b
        long length=file.length();
        //此处做判断是为了尽可能的减少对微信API的调用次数
        if(media_fix.get(type).indexOf(suffix)==-1){
            throw new WxErrorException(WxError.newBuilder().setErrorCode(40005).setErrorMsg("不合法的文件类型").build());
        }
        if(length>type_length.get(type)){
            throw new WxErrorException(WxError.newBuilder().setErrorCode(40006).setErrorMsg("不合法的文件大小").build());
        }
        String url=String.format(UPLOAD_MEDIA, accessToken,type);
        String result = HttpClientUtils.sendHttpPost(url, file, null);
        WxError wxError = WxError.fromJson(result);
        if(wxError.getErrorCode()!=0){
            throw new WxErrorException(wxError);
        }

        return JSONObject.parseObject(result);
    }

    /**
     * 临时素材下载-包含图片、语音、视频、缩略图
     * @param accessToken 微信token
     * @param mediaId 临时素材Id
     * @param file 要下载到的目录 例如D://temp
     * @return
     * @throws WxErrorException
     */
    public static File downlodMedia(String accessToken,String mediaId,File file)throws WxErrorException{
        String url=String.format(GET_MEDIA, accessToken,mediaId);
        Object obj = HttpClientUtils.sendHttpGetFile(url, file);
        if(obj instanceof String){
            WxError wxError = WxError.fromJson((String)obj);
            throw new WxErrorException(wxError);
        }
        if(null==obj){
            throw new WxErrorException(WxError.newBuilder().setErrorCode(-3).setErrorMsg("下载出错").build());
        }

        return (File)obj;
    }
    /**
     * 构造微信JSSDK支付参数，返回到页面
     */
    public static Map<String, String> getWSJSPayPara(String openId, String appId, String appsecret, String partnerkey,
                                                     String mch_id, String body, String outTradeNo, String payMoney, String notify_url, String trade_type,
                                                     String str_timestamp, String str_nonceStr) {
        // 支付金额
        float sessionmoney = Float.parseFloat(payMoney);
        payMoney = String.format("%.2f", sessionmoney);
        payMoney = payMoney.replace(".", "");

        System.out.println("微信端充值金额：=======" + payMoney);
        String nonce_str = str_nonceStr; // 签名用的noncestr和timestamp必须与wx.config中的nonceStr和timestamp相同。
        // 订单生成的机器 IP
        String spbill_create_ip = "123.57.243.49";

        System.out.println("ip地址：=============" + spbill_create_ip);

        long currentTime = System.currentTimeMillis() + 0 * 60 * 1000;
        String strCurrentTime = com.wxmp.core.util.wx.TenpayUtil2.getWXTime(currentTime);
        long currentTimeLay30m = currentTime + 30 * 60 * 1000;// 30分钟后 【要求至少5分钟过期】
        String strCurrentTimeLay30m = com.wxmp.core.util.wx.TenpayUtil2.getWXTime(currentTimeLay30m);
        // 订 单 生 成 时 间 非必输
        String time_start = strCurrentTime;
        // 订单失效时间 非必输
        String time_expire = strCurrentTimeLay30m;

        SortedMap<String, String> packageParams = new TreeMap<String, String>();
        packageParams.put("appid", appId);
        packageParams.put("mch_id", mch_id);
        packageParams.put("device_info", "WEB");// PC网页或公众号内支付请传"WEB"
        packageParams.put("body", body);
        packageParams.put("openid", openId); // jssdk模式时这个必填
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("out_trade_no", outTradeNo);
        packageParams.put("total_fee", payMoney);
        packageParams.put("spbill_create_ip", spbill_create_ip);
        packageParams.put("notify_url", notify_url);
        packageParams.put("trade_type", trade_type);

        com.wxmp.core.util.wx.RequestHandler reqHandler = new com.wxmp.core.util.wx.RequestHandler(null, null);
        reqHandler.init(appId, appsecret, partnerkey);

        // ##生成加密签名，此签名用于统一订单接口
        String sign = reqHandler.createSign(packageParams);
        String xml = "<xml>" + "<openid>" + openId + "</openid>" + "<appid>" + appId + "</appid>" + "<mch_id>" + mch_id
                + "</mch_id>" + "<device_info>" + "WEB" + "</device_info>" + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>" + outTradeNo + "</out_trade_no>"
                + "<total_fee>" + payMoney + "</total_fee>" + "<spbill_create_ip>" + spbill_create_ip
                + "</spbill_create_ip>" + "<notify_url>" + notify_url + "</notify_url>" + "<trade_type>" + trade_type
                + "</trade_type>" + "<sign>" + sign + "</sign>" + "</xml>";

        System.out.println("手机端外部订单号：out_trade_no" + outTradeNo);
        System.out.println("======================================：");
        System.out.println("手机端统一订单接口传参：" + xml);

        Map<String, String> dataMap = new HashMap<String, String>();
        String createOrderURL = getUnifiedOrderUrl();
        String prepay_id = "";
        String errMsg = "";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = Identities.getRandomString(8);
        // #######jssdk config配置时已设置，从前台传过来######
        timestamp = str_timestamp;
        nonceStr = str_nonceStr;// nonce_str:下统一订单时的随机字符串，此时这三个随机变量完全相等
        String paySign = "";

        try {
            String retXmlStr = httpsRequestByXml(createOrderURL, HttpMethod.POST, xml);
            System.out.println("手机端统一订单接口结果:" + retXmlStr);
            if (null != retXmlStr && retXmlStr.contains("return_code")) {
                Map map = com.wxmp.core.util.wx.TenpayUtil2.doXMLParseByDom4j(retXmlStr);
                String return_code = (String) map.get("return_code");
                if ("SUCCESS".equals(return_code)) {
                    String result_code = (String) map.get("result_code");
                    if ("SUCCESS".equals(result_code)) {
                        prepay_id = (String) map.get("prepay_id");
                        errMsg = "0";
                    } else {
                        prepay_id = "";
                        errMsg = (String) map.get("err_code_des");
                    }
                } else {
                    prepay_id = "";
                    errMsg = (String) map.get("return_msg");
                }

                // ###生成支付签名,此签名 给 微信支付的调用使用（前台页面使用）
                SortedMap<String, String> packageParams_paySign = new TreeMap<String, String>();
                packageParams_paySign.put("appId", appId);
                packageParams_paySign.put("timeStamp", timestamp);// 后台生成签名时是大写的timeStamp
                packageParams_paySign.put("nonceStr", nonceStr);// 签名用的noncestr和timestamp必须与wx.config中的nonceStr和timestamp相同。
                packageParams_paySign.put("package", "prepay_id=" + prepay_id);
                packageParams_paySign.put("signType", "MD5");
                com.wxmp.core.util.wx.RequestHandler reqHandler_paySign = new com.wxmp.core.util.wx.RequestHandler(null,
                        null);
                reqHandler_paySign.init(appId, appsecret, partnerkey);
                paySign = reqHandler_paySign.createSign(packageParams_paySign);

                /**
                 * 最后别忘记一定要设置：微信支付授权目录设置
                 * 微信支付授权目录设置
                 注意这里所指的是目录，所以一定要以左斜杠“/”结尾，不是设置支付的url网址
                 例如设置的是：http://www.newfms.com/order/pay/
                 那么真正支付url是
                 http://www.newfms.com/order/pay/
                 http://www.newfms.com/order/pay/1
                 如果是这样的支付url就会报错：http://www.newfms.com/order/pay
                 */

            } else {
                System.out.println("统一支付接口出错");
                prepay_id = "";
                errMsg = "统一支付接口出错";
            }
        } catch (Exception e1) {
            System.out.println("系统异常\n" + e1.getMessage());
            prepay_id = "";
            errMsg = "系统异常\n" + e1.getMessage();
        } // try-catch end

        // ###返回给 微信支付的调用使用（前台页面使用）
        dataMap.put("appid", appId);
        dataMap.put("timestamp", timestamp);// 前台页面调用jssdk支付时是小写的timestamp
        dataMap.put("nonceStr", nonceStr);
        dataMap.put("package", "prepay_id=" + prepay_id);
        dataMap.put("signType", "MD5");
        dataMap.put("paySign", paySign);
        dataMap.put("errMsg", errMsg);

        return dataMap;
    }

    // 发送请求
    public static JSONObject httpsRequest(String requestUrl, String requestMethod) {
        return httpsRequest(requestUrl, requestMethod, null);
    }

    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            TrustManager[] tm = { new MyTrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static String httpsRequestByXml(String requestUrl, String requestMethod, String outputStr) {
        String retStrXml = "";
        try {
            TrustManager[] tm = { new MyTrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            conn.disconnect();
            retStrXml = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retStrXml;
    }

    public static byte[] httpsRequestByte(String requestUrl, String requestMethod) {
        return httpsRequestByte(requestUrl, requestMethod, null);
    }

    public static byte[] httpsRequestByte(String requestUrl, String requestMethod, String outputStr) {
        try {
            TrustManager[] tm = { new MyTrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            InputStream inputStream = conn.getInputStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            return output.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String urlEnodeUTF8(String str) {
        String result = str;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
