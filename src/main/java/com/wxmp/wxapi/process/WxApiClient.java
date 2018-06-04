/*
 * FileName：WxApiClient.java 
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

import java.io.UnsupportedEncodingException;
import java.util.*;

import com.wxmp.core.exception.WxError;
import com.wxmp.core.util.wx.WxUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wxmp.core.common.Identities;
import com.wxmp.core.exception.WxErrorException;
import com.wxmp.core.util.DateUtil;
import com.wxmp.wxapi.vo.Material;
import com.wxmp.wxapi.vo.MaterialArticle;
import com.wxmp.wxapi.vo.MaterialItem;
import com.wxmp.wxapi.vo.TemplateMessage;
import com.wxmp.wxcms.domain.AccountFans;
import com.wxmp.wxcms.domain.MsgNews;

/**
 * 微信 客户端，统一处理微信相关接口
 */
@Component
public class WxApiClient {
    
    private static Logger logger = Logger.getLogger(WxApiClient.class);
    
    // 获取accessToken
    public static String getAccessToken(MpAccount mpAccount)
        throws WxErrorException {
        // 获取唯一的accessToken，如果是多账号，请自行处理
        AccessToken token = WxMemoryCacheClient.getAccessToken();
        if (token != null && !token.isExpires() && WxApi.getCallbackIp(token.getAccessToken())) {// 不为空，并且没有过期
            logger.info("服务器缓存 accessToken == " + token.toString());
            return token.getAccessToken();
        } else {
            token = WxApi.getAccessToken(mpAccount.getAppid(), mpAccount.getAppsecret());
            if (token != null) {
                if (token.getErrcode() == null) {
                    WxMemoryCacheClient.addAccessToken(mpAccount.getAccount(), token);
                    return token.getAccessToken();
                } else {
                    throw new WxErrorException(WxError.newBuilder().setErrorCode(token.getErrcode()).setErrorMsg(token.getErrmsg()).build());
                }
            }
            return null;
        }
    }
    
    // 获取jsTicket
    public static String getJSTicket(MpAccount mpAccount)
        throws WxErrorException {
        // 获取唯一的JSTicket，如果是多账号，请自行处理
        JSTicket jsTicket = WxMemoryCacheClient.getJSTicket();
        if (jsTicket != null && !jsTicket.isExpires()) {// 不为空，并且没有过期
            return jsTicket.getTicket();
        } else {
            String token = getAccessToken(mpAccount);
            jsTicket = WxApi.getJSTicket(token);
            if (jsTicket != null) {
                if (jsTicket.getErrcode() == null) {
                    WxMemoryCacheClient.addJSTicket(mpAccount.getAccount(), jsTicket);
                    return jsTicket.getTicket();
                }else{
                    throw new WxErrorException(WxError.newBuilder().setErrorCode(jsTicket.getErrcode()).setErrorMsg(jsTicket.getErrmsg()).build());
                }
            }
            return null;
        }
    }
    
    // 获取OAuthAccessToken
    public static OAuthAccessToken getOAuthAccessToken(MpAccount mpAccount, String code)
        throws WxErrorException {
        // 获取唯一的accessToken，如果是多账号，请自行处理
        OAuthAccessToken token = WxMemoryCacheClient.getOAuthAccessToken();
        if (token != null && !token.isExpires()) {// 不为空，并且没有过期
            return token;
        } else {
            token = WxApi.getOAuthAccessToken(mpAccount.getAppid(), mpAccount.getAppsecret(), code);
            if (token != null) {
                if (token.getErrcode() != null) {// 获取失败
                    throw new WxErrorException(WxError.newBuilder().setErrorCode(-1).setErrorMsg(token.getErrmsg()).build());
                } else {
                    token.setOpenid(null);// 获取OAuthAccessToken的时候设置openid为null；不同用户openid缓存
                    WxMemoryCacheClient.addOAuthAccessToken(mpAccount.getAccount(), token);
                    return token;
                }
            }
            return null;
        }
    }
    
    // 获取openId
    public static String getOAuthOpenId(MpAccount mpAccount, String code)
        throws WxErrorException {
        OAuthAccessToken token = WxApi.getOAuthAccessToken(mpAccount.getAppid(), mpAccount.getAppsecret(), code);
        if (token != null) {
            if (token.getErrcode() != null) {// 获取失败
                throw new WxErrorException(WxError.newBuilder().setErrorCode(-1).setErrorMsg(token.getErrmsg()).build());
            } else {
                return token.getOpenid();
            }
        }
        return null;
    }
    
    // 发布菜单
    public static JSONObject publishMenus(String menus, MpAccount mpAccount)
        throws WxErrorException {
        String accessToken = getAccessToken(mpAccount);
        String url = WxApi.getMenuCreateUrl(accessToken);
        JSONObject rstObj = WxApi.httpsRequest(url, HttpMethod.POST, menus);
        if (WxUtil.isWxError(rstObj)) {
            throw new WxErrorException(WxError.fromJson(rstObj));
        }
        return rstObj;
    }
    
    // 创建个性化菜单
    public static JSONObject publishAddconditionalMenus(String menus, MpAccount mpAccount)
        throws WxErrorException {
        String accessToken = getAccessToken(mpAccount);
        String url = WxApi.getMenuAddconditionalUrl(accessToken);
        JSONObject rstObj = WxApi.httpsRequest(url, HttpMethod.POST, menus);
        if (WxUtil.isWxError(rstObj)) {
            throw new WxErrorException(WxError.fromJson(rstObj));
        }
        return rstObj;
    }
    
    // 删除菜单
    public static JSONObject deleteMenu(MpAccount mpAccount)
        throws WxErrorException {
        String accessToken = getAccessToken(mpAccount);
        String url = WxApi.getMenuDeleteUrl(accessToken);
        JSONObject rstObj =  WxApi.httpsRequest(url, HttpMethod.POST, null);
        if (WxUtil.isWxError(rstObj)) {
            throw new WxErrorException(WxError.fromJson(rstObj));
        }
        return rstObj;
    }
    
    // 根据openId获取粉丝信息
    public static AccountFans syncAccountFans(String openId, MpAccount mpAccount)
        throws WxErrorException {
        String accessToken = getAccessToken(mpAccount);
        logger.info("获取用户信息接口accessToken：" + accessToken);
        String url = WxApi.getFansInfoUrl(accessToken, openId);
        logger.info("获取用户信息接口url：" + url);
        JSONObject jsonObj = WxApi.httpsRequest(url, "GET", null);
        if (null != jsonObj) {
            logger.info("获取用户信息接口返回结果：" + jsonObj.toString());
            if (jsonObj.containsKey("errcode")) {
                throw new WxErrorException(WxError.fromJson(jsonObj));
            } else {
                AccountFans fans = new AccountFans();
                fans.setOpenId(jsonObj.getString("openid"));// 用户的标识
                fans.setSubscribeStatus(new Integer(jsonObj.getIntValue("subscribe")));// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                if (jsonObj.containsKey("subscribe_time")) {
                    fans.setSubscribeTime(DateUtil.timestampToDate(jsonObj.getString("subscribe_time")));// 用户关注时间
                }
                if (jsonObj.containsKey("nickname")) {// 昵称
                    try {
                        String nickname = jsonObj.getString("nickname");
                        fans.setNickname(nickname.getBytes("UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                if (jsonObj.containsKey("sex")) {// 用户的性别（1是男性，2是女性，0是未知）
                    fans.setGender(jsonObj.getIntValue("sex"));
                }
                if (jsonObj.containsKey("language")) {// 用户的语言，简体中文为zh_CN
                    fans.setLanguage(jsonObj.getString("language"));
                }
                if (jsonObj.containsKey("country")) {// 用户所在国家
                    fans.setCountry(jsonObj.getString("country"));
                }
                if (jsonObj.containsKey("province")) {// 用户所在省份
                    fans.setProvince(jsonObj.getString("province"));
                }
                if (jsonObj.containsKey("city")) {// 用户所在城市
                    fans.setCity(jsonObj.getString("city"));
                }
                if (jsonObj.containsKey("headimgurl")) {// 用户头像
                    fans.setHeadimgurl(jsonObj.getString("headimgurl"));
                }
                if (jsonObj.containsKey("remark")) {
                    fans.setRemark(jsonObj.getString("remark"));
                }
                fans.setStatus(1);
                fans.setCreateTime(new Date());
                return fans;
            }
        }
        return null;
    }
    
    /**
     * 获取素材
     * @param mediaType 素材类型
     * @param offset 开始位置
     * @param count 获取数量
     * @return
     */
    public static Material syncBatchMaterial(MediaType mediaType, Integer offset, Integer count, MpAccount mpAccount)
        throws WxErrorException {
        String accessToken = getAccessToken(mpAccount);
        String url = WxApi.getBatchMaterialUrl(accessToken);
        JSONObject bodyObj = new JSONObject();
        bodyObj.put("type", mediaType.toString());
        bodyObj.put("offset", offset);
        bodyObj.put("count", count);
        String body = bodyObj.toString();
        try {
            JSONObject jsonObj = WxApi.httpsRequest(url, "POST", body);
            if (jsonObj.containsKey("errcode")) {// 获取素材失败
                throw new WxErrorException(WxError.fromJson(jsonObj));
            } else {
                Material material = new Material();
                material.setTotalCount(jsonObj.getIntValue("total_count"));
                material.setItemCount(jsonObj.getIntValue("item_count"));
                JSONArray arr = jsonObj.getJSONArray("item");
                if (arr != null && arr.size() > 0) {
                    List<MaterialItem> itemList = new ArrayList<MaterialItem>();
                    for (int i = 0; i < arr.size(); i++) {
                        JSONObject item = arr.getJSONObject(i);
                        MaterialItem materialItem = new MaterialItem();
                        materialItem.setMediaId(item.getString("media_id"));
                        materialItem.setUpdateTime(item.getLong("update_time") * 1000L);
                        if (item.containsKey("content")) {// mediaType=news （图文消息）
                            JSONArray articles = item.getJSONObject("content").getJSONArray("news_item");
                            List<MaterialArticle> newsItems = new ArrayList<MaterialArticle>();
                            for (int j = 0; j < articles.size(); j++) {
                                JSONObject article = articles.getJSONObject(j);
                                MaterialArticle ma = new MaterialArticle();
                                ma.setTitle(article.getString("title"));
                                ma.setThumb_media_id(article.getString("thumb_media_id"));
                                ma.setShow_cover_pic(article.getIntValue("show_cover_pic"));
                                ma.setAuthor(article.getString("author"));
                                ma.setContent_source_url(article.getString("content_source_url"));
                                ma.setContent(article.getString("content"));
                                ma.setUrl(article.getString("url"));
                                newsItems.add(ma);
                            }
                            materialItem.setNewsItems(newsItems);
                        } else {
                            materialItem.setName(item.getString("name"));
                            materialItem.setUrl(item.getString("url"));
                        }
                        itemList.add(materialItem);
                    }
                    material.setItems(itemList);
                }
                return material;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // 上传图文消息
    public static JSONObject uploadNews(List<MsgNews> msgNewsList, MpAccount mpAccount)
        throws Exception {
        String accessToken = getAccessToken(mpAccount);
        JSONArray jsonArr = new JSONArray();
        for (MsgNews news : msgNewsList) {
            JSONObject jsonObj = new JSONObject();
            // 上传图片素材
            String mediaId = WxApi.uploadMedia(accessToken, MediaType.Image.toString(), news.getPicpath());
            jsonObj.put("thumb_media_id", mediaId);
            if (news.getAuthor() != null) {
                jsonObj.put("author", news.getAuthor());
            } else {
                jsonObj.put("author", "");
            }
            if (news.getTitle() != null) {
                jsonObj.put("title", news.getTitle());
            } else {
                jsonObj.put("title", "");
            }
            if (news.getFromurl() != null) {
                jsonObj.put("content_source_url", news.getFromurl());
            } else {
                jsonObj.put("content_source_url", "");
            }
            if (news.getBrief() != null) {
                jsonObj.put("digest", news.getBrief());
            } else {
                jsonObj.put("digest", "");
            }
            if (news.getShowpic() != null) {
                jsonObj.put("show_cover_pic", news.getShowpic());
            } else {
                jsonObj.put("show_cover_pic", 1);
            }
            jsonObj.put("content", news.getDescription());
            jsonArr.add(jsonObj);
        }
        JSONObject postObj = new JSONObject();
        postObj.put("articles", jsonArr);
        JSONObject rstObj = WxApi.httpsRequest(WxApi.getUploadNewsUrl(accessToken), HttpMethod.POST, postObj.toString());
        if (WxUtil.isWxError(rstObj)) {
            throw new WxErrorException(WxError.fromJson(rstObj));
        }
        return rstObj;
    }
    
    /**
     * 根据openid群发接口
     * 
     * @param mediaId：素材的id；通过素材管理,或者上传素材获取
     * @param msgType
     * @param mpAccount
     * @return
     */
    public static JSONObject massSendByOpenIds(List<String> openids, String mediaId, MsgType msgType, MpAccount mpAccount)
        throws WxErrorException {
        if (openids != null && openids.size() > 0) {
            JSONObject postObj = new JSONObject();
            JSONObject media = new JSONObject();
            postObj.put("touser", openids);
            media.put("media_id", mediaId);
            postObj.put(msgType.toString(), media);
            postObj.put("msgtype", msgType.toString());
            String accessToken = getAccessToken(mpAccount);
            JSONObject rstObj = WxApi.httpsRequest(WxApi.getMassSendUrl(accessToken), HttpMethod.POST, postObj.toString());
            if (WxUtil.isWxError(rstObj)) {
                throw new WxErrorException(WxError.fromJson(rstObj));
            }
            return rstObj;
        }
        return null;
    }
    
    /**
     * 根据openid群发文本消息
     * 
     * @param openids
     * @param content
     * @param mpAccount
     * @return
     */
    public static JSONObject massSendTextByOpenIds(List<String> openids, String content, MpAccount mpAccount)
        throws WxErrorException {
        if (openids != null && openids.size() > 0) {
            if (openids.size() == 1) {// 根据openId群发，size至少为2
                openids.add("1");
            }
            String[] arr = (String[])openids.toArray(new String[openids.size()]);
            JSONObject postObj = new JSONObject();
            JSONObject text = new JSONObject();
            postObj.put("touser", arr);
            text.put("content", content);
            postObj.put("text", text);
            postObj.put("msgtype", MsgType.Text.toString());
            String accessToken = getAccessToken(mpAccount);
            JSONObject rstObj = WxApi.httpsRequest(WxApi.getMassSendUrl(accessToken), HttpMethod.POST, postObj.toString());
            if (WxUtil.isWxError(rstObj)) {
                throw new WxErrorException(WxError.fromJson(rstObj));
            }
            return rstObj;
        }
        return null;
    }
    
    /**
     * 发送客服消息
     * 
     * @param openid
     * @param content 消息内容
     * @return
     */
    public static JSONObject sendCustomTextMessage(String openid, String content, MpAccount mpAccount)
        throws WxErrorException {
        if (!StringUtils.isBlank(openid) && !StringUtils.isBlank(content)) {
            String accessToken = getAccessToken(mpAccount);
            content = WxMessageBuilder.prepareCustomText(openid, content);
            JSONObject rstObj = WxApi.httpsRequest(WxApi.getSendCustomMessageUrl(accessToken), HttpMethod.POST, content);
            if (WxUtil.isWxError(rstObj)) {
                throw new WxErrorException(WxError.fromJson(rstObj));
            }
            return rstObj;
        }
        return null;
    }

	/**
	 * 发送客服消息
	 *
	 * @param openid
	 * @param mpAccount 消息内容
	 * @return
	 */
	public static JSONObject sendCustomNews(String openid, MsgNews msgNews, MpAccount mpAccount)
			throws WxErrorException {
		String content = "";
		if (!StringUtils.isBlank(openid)) {
			String accessToken = getAccessToken(mpAccount);
			content = WxMessageBuilder.prepareCustomNews(openid, msgNews);
            JSONObject rstObj = WxApi.httpsRequest(WxApi.getSendCustomMessageUrl(accessToken), HttpMethod.POST, content);
            if (WxUtil.isWxError(rstObj)) {
                throw new WxErrorException(WxError.fromJson(rstObj));
            }
            return rstObj;
		}
		return null;
	}

    /**
     * 发送模板消息
     * 
     * @param tplMsg
     * @param mpAccount 消息内容
     * @return
     */
    public static JSONObject sendTemplateMessage(TemplateMessage tplMsg, MpAccount mpAccount)
        throws WxErrorException {
        if (tplMsg != null) {
            String accessToken = getAccessToken(mpAccount);
            JSONObject jsonObject = WxApi.httpsRequest(WxApi.getSendTemplateMessageUrl(accessToken), HttpMethod.POST, tplMsg.toString());
            if (WxUtil.isWxError(jsonObject)) {
                throw new WxErrorException(WxError.fromJson(jsonObject));
            }
            return jsonObject;
        }
        return null;
    }
    
    /**
     * 创建临时二维码
     * 
     * @param expireSecodes 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
     * @param scene 临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000)
     * @return
     */
    public static byte[] createQRCode(Integer expireSecodes, Integer scene, MpAccount mpAccount)
        throws WxErrorException {
        if (scene != null) {
            String accessToken = getAccessToken(mpAccount);
            String postBody = WxApi.getQrcodeJson(expireSecodes, scene);
            JSONObject jsObj = WxApi.httpsRequest(WxApi.getCreateQrcodeUrl(accessToken), HttpMethod.POST, postBody);
            if (jsObj != null) {
                String ticket = jsObj.getString("ticket");
                if (!StringUtils.isBlank(ticket)) {
                    return WxApi.httpsRequestByte(WxApi.getShowQrcodeUrl(ticket), HttpMethod.GET);
                }
                return null;
            }
        }
        return null;
    }
    
    // 创建永久字符串二维码
    public static byte[] createQRCodeLimit(String qrcodeStr, MpAccount mpAccount)
        throws WxErrorException {
        if (!StringUtils.isBlank(qrcodeStr)) {
            String accessToken = getAccessToken(mpAccount);
            String postBody = WxApi.getQrcodeLimitJson(qrcodeStr);
            JSONObject jsObj = WxApi.httpsRequest(WxApi.getCreateQrcodeUrl(accessToken), HttpMethod.POST, postBody);
            if (jsObj != null) {
                String ticket = jsObj.getString("ticket");
                if (!StringUtils.isBlank(ticket)) {
                    return WxApi.httpsRequestByte(WxApi.getShowQrcodeUrl(ticket), HttpMethod.GET);
                }
                return null;
            }
        }
        return null;
    }
    
    // 上传永久图片
    public static JSONObject uploadMaterialImg(String filePath, MpAccount mpAccount)
        throws Exception {
        String accessToken = getAccessToken(mpAccount);
        // 上传永久图片素材
        JSONObject rstObj = WxApi.addMaterial(WxApi.getMaterialImgUrl(accessToken), filePath);
        if (WxUtil.isWxError(rstObj)) {
            throw new WxErrorException(WxError.fromJson(rstObj));
        }
        return rstObj;
    }
    
    // 新增微信永久素材
    public static JSONObject addMaterial(String filePath, String materialType, MpAccount mpAccount)
        throws Exception {
        String accessToken = getAccessToken(mpAccount);
        // 上传永久图片素材
        JSONObject rstObj = WxApi.addMaterial(WxApi.getMaterialUrl(accessToken, materialType), filePath);
        if (WxUtil.isWxError(rstObj)) {
            throw new WxErrorException(WxError.fromJson(rstObj));
        }
        return rstObj;
    }
    
    /**
     * 根据media_id获取永久图文素材
     *
     * @param media_id
     * @param mpAccount
     * @return
     */
    public static JSONObject getMaterial(String media_id, MpAccount mpAccount)
        throws WxErrorException {
        JSONObject postObj = new JSONObject();
        postObj.put("media_id", media_id);
        String accessToken = getAccessToken(mpAccount);
        return WxApi.httpsRequest(WxApi.getMaterial(accessToken), HttpMethod.POST, postObj.toString());
    }
    
    /**
     * 根据media_id删除永久图文素材
     * 
     * @param media_id
     * @param mpAccount
     * @return
     */
    public static JSONObject deleteMaterial(String media_id, MpAccount mpAccount)
        throws WxErrorException {
        JSONObject postObj = new JSONObject();
        postObj.put("media_id", media_id);
        String accessToken = getAccessToken(mpAccount);
        JSONObject rstObj = WxApi.httpsRequest(WxApi.getDelMaterialURL(accessToken), HttpMethod.POST, postObj.toString());
        if (WxUtil.isWxError(rstObj)) {
            throw new WxErrorException(WxError.fromJson(rstObj));
        }
        return rstObj;
    }


    // 新增永久图文素材
    public static JSONObject addNewsMaterial(List<MsgNews> msgNewsList, String mediaId, MpAccount mpAccount)
        throws Exception {
        String accessToken = getAccessToken(mpAccount);
        JSONArray jsonArr = new JSONArray();
        for (MsgNews news : msgNewsList) {
            JSONObject jsonObj = new JSONObject();
            // 上传图片素材
            jsonObj.put("thumb_media_id", mediaId);
            if (news.getAuthor() != null) {
                jsonObj.put("author", news.getAuthor());
            } else {
                jsonObj.put("author", "");
            }
            if (news.getTitle() != null) {
                jsonObj.put("title", news.getTitle());
            } else {
                jsonObj.put("title", "");
            }
            if (news.getFromurl() != null) {
                jsonObj.put("content_source_url", news.getFromurl());
            } else {
                jsonObj.put("content_source_url", "");
            }
            if (news.getBrief() != null) {
                jsonObj.put("digest", news.getBrief());
            } else {
                jsonObj.put("digest", "");
            }
            if (news.getShowpic() != null) {
                jsonObj.put("show_cover_pic", news.getShowpic());
            } else {
                jsonObj.put("show_cover_pic", 1);
            }
            jsonObj.put("content", news.getDescription());
            jsonArr.add(jsonObj);
        }
        JSONObject postObj = new JSONObject();
        postObj.put("articles", jsonArr);
        JSONObject rstObj = WxApi.httpsRequest(WxApi.getNewsMaterialUrl(accessToken), HttpMethod.POST, postObj.toString());
        if (WxUtil.isWxError(rstObj)) {
            throw new WxErrorException(WxError.fromJson(rstObj));
        }
        return rstObj;
    }
    
    // 修改永久图文素材
    public static JSONObject updateNewsMaterial(List<MsgNews> msgNewsList, int index, String mediaId, MpAccount mpAccount)
        throws Exception {
        String accessToken = getAccessToken(mpAccount);
        MsgNews news = msgNewsList.get(0);
        JSONObject jsonObj = new JSONObject();
        
        // 上传图片素材
        jsonObj.put("thumb_media_id", news.getThumbMediaId());
        if (news.getAuthor() != null) {
            jsonObj.put("author", news.getAuthor());
        } else {
            jsonObj.put("author", "");
        }
        if (news.getTitle() != null) {
            jsonObj.put("title", news.getTitle());
        } else {
            jsonObj.put("title", "");
        }
        if (news.getFromurl() != null) {
            jsonObj.put("content_source_url", news.getFromurl());
        } else {
            jsonObj.put("content_source_url", "");
        }
        if (news.getBrief() != null) {
            jsonObj.put("digest", news.getBrief());
        } else {
            jsonObj.put("digest", "");
        }
        if (news.getShowpic() != null) {
            jsonObj.put("show_cover_pic", news.getShowpic());
        } else {
            jsonObj.put("show_cover_pic", 1);
        }
        jsonObj.put("content", news.getDescription());
        
        JSONObject postObj = new JSONObject();
        postObj.put("media_id", mediaId);
        postObj.put("index", index);
        postObj.put("articles", jsonObj);
        JSONObject rstObj = WxApi.httpsRequest(WxApi.getUpdateNewsMaterialUrl(accessToken), HttpMethod.POST, postObj.toString());
        if (WxUtil.isWxError(rstObj)) {
            throw new WxErrorException(WxError.fromJson(rstObj));
        }
        return rstObj;
    }
    
    public static JSONObject updateNewsMaterial2(JSONObject jsonObj, int index, String mediaId, MpAccount mpAccount)
        throws Exception {
        String accessToken = getAccessToken(mpAccount);
        JSONObject postObj = new JSONObject();
        postObj.put("media_id", mediaId);
        postObj.put("index", index);
        postObj.put("articles", jsonObj);
        JSONObject rstObj = WxApi.httpsRequest(WxApi.getUpdateNewsMaterialUrl(accessToken), HttpMethod.POST, postObj.toString());
        if (WxUtil.isWxError(rstObj)) {
            throw new WxErrorException(WxError.fromJson(rstObj));
        }
        return rstObj;
    }
    
    // 新增多图文永久素材
    public static JSONObject addMoreNewsMaterial(List<MsgNews> msgNewsList, MpAccount mpAccount)
        throws Exception {
        String accessToken = getAccessToken(mpAccount);
        JSONArray jsonArr = new JSONArray();
        for (MsgNews news : msgNewsList) {
            JSONObject jsonObj = new JSONObject();
            // 上传图片素材
            jsonObj.put("thumb_media_id", news.getThumbMediaId());
            if (news.getAuthor() != null) {
                jsonObj.put("author", news.getAuthor());
            } else {
                jsonObj.put("author", "");
            }
            if (news.getTitle() != null) {
                jsonObj.put("title", news.getTitle());
            } else {
                jsonObj.put("title", "");
            }
            if (news.getFromurl() != null) {
                jsonObj.put("content_source_url", news.getFromurl());
            } else {
                jsonObj.put("content_source_url", "");
            }
            if (news.getBrief() != null) {
                jsonObj.put("digest", news.getBrief());
            } else {
                jsonObj.put("digest", "");
            }
            if (news.getShowpic() != null) {
                jsonObj.put("show_cover_pic", news.getShowpic());
            } else {
                jsonObj.put("show_cover_pic", 1);
            }
            jsonObj.put("content", news.getDescription());
            jsonArr.add(jsonObj);
        }
        JSONObject postObj = new JSONObject();
        postObj.put("articles", jsonArr);
        JSONObject rstObj = WxApi.httpsRequest(WxApi.getNewsMaterialUrl(accessToken), HttpMethod.POST, postObj.toString());
        if (WxUtil.isWxError(rstObj)) {
            throw new WxErrorException(WxError.fromJson(rstObj));
        }
        return rstObj;
    }
    
    // 新增多图文永久素材
    public static JSONObject addMoreNewsMaterial2(JSONArray arryarticles, MpAccount mpAccount)
        throws Exception {
        String accessToken = getAccessToken(mpAccount);
            JSONObject postObj = new JSONObject();
            postObj.put("articles", arryarticles);
        JSONObject rstObj = WxApi.httpsRequest(WxApi.getNewsMaterialUrl(accessToken), HttpMethod.POST, postObj.toString());
        if (WxUtil.isWxError(rstObj)) {
            throw new WxErrorException(WxError.fromJson(rstObj));
        }
        return rstObj;
    }

    /**
     * 构造微信JSSDK支付参数，返回到页面
     */
    public static Map<String, String> getWSJSPayPara(MpAccount mpAccount, String openid, String timestamp, String nonceStr) {
        Map<String, String> dataMap = new HashMap<String, String>();
        String openId = openid;
        String appId = mpAccount.getAppid();
        String appsecret = mpAccount.getAppsecret();
        String partnerkey = "abcdefghijklmnopqrstuvwxyz123456";// 在微信商户平台pay.weixin.com里自己生成的那个key
        String mch_id = "1317476101";
        String body = "支付009992";
        String out_trade_no = "20160411111101";
        String finalmoney = "1";
        String notify_url = "http://www.yjydt.cn/wxmp/wxapi/wxipay_noity";
        // String notify_url="http://www.yjydt.cn/pay/wxipay_noity";
        String trade_type = "JSAPI";// 公众号支付

        dataMap = WxApi.getWSJSPayPara(openId, appId, appsecret, partnerkey, mch_id, body, out_trade_no, finalmoney, notify_url, trade_type, timestamp, nonceStr);
        return dataMap;
    }

	public static void main(String[] args) {
        String appid = "wx91961db8b6273777";
        String appsecret = "7d0377b8b30d4b3df4ba46bb7febc793";
        String mch_id = "1317476101";
        String partnerkey = "abcdefghijklmnopqrstuvwxyz123456";// 在微信商户平台pay.weixin.com里自己生成的那个key
        MpAccount mpAccount = new MpAccount();
        mpAccount.setAppid(appid);
        mpAccount.setAppsecret(appsecret);
        
        String openid = "otLBWs_uiGnrWBGgHEemPZTQLatE";
        
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = Identities.getRandomString(8);
        getWSJSPayPara(mpAccount, openid, timestamp, nonceStr);
    }
}
