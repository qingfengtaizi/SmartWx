/*
 * FileName：MsgNewsCtrl.java 
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
package com.wxmp.wxcms.ctrl;

import static com.wxmp.core.util.DateUtilOld.COMMON_FULL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonParseException;
import com.wxmp.core.common.BaseCtrl;
import com.wxmp.core.spring.SpringFreemarkerContextPathUtil;
import com.wxmp.core.util.AjaxResult;
import com.wxmp.wxapi.process.MediaType;
import com.wxmp.wxapi.process.MpAccount;
import com.wxmp.wxapi.process.WxApiClient;
import com.wxmp.wxapi.process.WxMemoryCacheClient;
import com.wxmp.wxcms.domain.MediaFiles;
import com.wxmp.wxcms.domain.MsgArticle;
import com.wxmp.wxcms.domain.MsgNews;
import com.wxmp.wxcms.service.MsgArticleService;
import com.wxmp.wxcms.service.MsgNewsService;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Controller
@RequestMapping("/msgnews")
public class MsgNewsCtrl extends BaseCtrl {

    @Autowired
    private MsgNewsService entityService;

    @Autowired
    private MsgArticleService articleService;

    @RequestMapping(value = "/detail")
    @ResponseBody
    public AjaxResult getById(String id) {
        return AjaxResult.success(entityService.getById(id));
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public AjaxResult list(MsgNews searchEntity) {
        List<MsgNews> pageList = entityService.getWebNewsListByPage(searchEntity);
        return getResult(searchEntity, pageList);
    }

    /**
     * 将保存到本地的素材信息同步到微信
     * 
     * @param newsId
     * @return
     */
    @RequestMapping(value = "/sendNewsMaterial", method = RequestMethod.POST)
    @ResponseBody
    public String sendNewsMaterial(String newsId, HttpServletRequest request) {
        String code = "";
        MsgNews msgNews = entityService.getById(newsId);
        List<MsgNews> msgNewsList = new ArrayList<MsgNews>();
        msgNewsList.add(msgNews);
        MpAccount mpAccount = WxMemoryCacheClient.getMpAccount();// 获取缓存中的唯一账号
        // 添加永久图片
        String materialType = MediaType.Image.toString();
        String imgPath = msgNews.getPicpath();
        String fileName = imgPath.substring(imgPath.lastIndexOf("/") + 1, imgPath.length());
        String filePath = SpringFreemarkerContextPathUtil.getImgBasePath(request, fileName);
        // 返回mediaId
        JSONObject imgResultObj = WxApiClient.addMaterial(filePath, materialType, mpAccount);
        // 上传图片的id
        String imgMediaId = "";
        String imgUrl = "";
        if (imgResultObj != null && imgResultObj.containsKey("media_id")) {
            imgMediaId = imgResultObj.getString("media_id");
            imgUrl = imgResultObj.getString("url");

            JSONObject newsResultObj = WxApiClient.addNewsMaterial(msgNewsList, imgMediaId, mpAccount);
            if (newsResultObj != null && newsResultObj.containsKey("media_id")) {
                String newsMediaId = newsResultObj.getString("media_id");
                MsgNews entity = new MsgNews();
                entity.setId(Long.valueOf(newsId));
                entity.setMediaId(newsMediaId);
                entityService.updateMediaId(entity);
                code = "1";
            } else {
                code = newsResultObj.toString();
            }
        } else {
            // 上传永久图片失败
            code = "-1";
        }

        return code;
    }

    /**
     * 添加单图文
     * 
     * @param msgNews
     * @param request
     * @return
     */
    @RequestMapping(value = "/addSingleNews", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addSingleNews(MsgNews msgNews, HttpServletRequest request) throws Exception {

        String filePath = request.getSession().getServletContext().getRealPath("/");

        String description = msgNews.getDescription();
        String description2 = msgNews.getDescription();

        description = description.replaceAll("'", "\"");
        // 去多个img的src值
        String subFilePath = "";
        String subOldFilePath = "";
        if (description.contains("img")) {
            Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
            Matcher m = p.matcher(description);

            while (m.find()) {
                String imgSrc = m.group(1);
                subOldFilePath += imgSrc + ",";
                String[] split = imgSrc.split("/");
                int k = imgSrc.indexOf(split[split.length - 2]);
                String subImgSrc = imgSrc.substring(k, imgSrc.length());
                subFilePath += filePath + subImgSrc + ",";
            }
        }
        MpAccount mpAccount = WxMemoryCacheClient.getMpAccount();// 获取缓存中的唯一账号
        if (StringUtils.isNotBlank(subFilePath)) {

            subFilePath = subFilePath.substring(0, subFilePath.length() - 1);
            subOldFilePath = subOldFilePath.substring(0, subOldFilePath.length() - 1);

            // 本地图片地址
            String[] imgPathArry = subFilePath.split(",");
            String[] imgOldPathArry = subOldFilePath.split(",");

            String[] newPathArry = new String[imgPathArry.length];
            for (int i = 0; i < imgPathArry.length; i++) {
                String newFilePath = imgPathArry[i];
                // 添加永久图片
                String materialType = MediaType.Image.toString();
                // 将图片上传到微信，返回url
                JSONObject imgResultObj = WxApiClient.uploadMaterialImg(newFilePath, mpAccount);

                // 上传图片的id
                // String contentImgMediaId = "";
                String contentContentUrl = "";
                if (imgResultObj != null && imgResultObj.containsKey("url")) {
                    // 微信返回来的媒体素材id
                    // contentImgMediaId = imgResultObj.getString("media_id");
                    // 图片url
                    contentContentUrl = imgResultObj.getString("url");
                }
                newPathArry[i] = contentContentUrl;
            }

            for (int i = 0; i < imgPathArry.length; i++) {
                description = description.replace(imgOldPathArry[i], newPathArry[i]);
            }
        }

        // 内容保存
        msgNews.setDescription(description);

        List<MsgNews> msgNewsList = new ArrayList<MsgNews>();
        msgNewsList.add(msgNews);
        // 封面图片媒体id
        String imgMediaId = msgNews.getThumbMediaId();

        JSONObject resultObj = WxApiClient.addNewsMaterial(msgNewsList, imgMediaId, mpAccount);

        if (resultObj != null && resultObj.containsKey("media_id")) {
            String newsMediaId = resultObj.getString("media_id");
            JSONObject newsResult = WxApiClient.getMaterial(newsMediaId, mpAccount);

            JSONArray articles = newsResult.getJSONArray("news_item");
            JSONObject article = (JSONObject) articles.get(0);
            MsgNews newsPo = new MsgNews();
            newsPo.setMultType(1);// 指定为1，代表单图文
            newsPo.setTitle(article.getString("title"));
            newsPo.setAuthor(article.getString("author"));
            newsPo.setBrief(article.getString("digest"));
            newsPo.setDescription(description2);
            newsPo.setFromurl(article.getString("content_source_url"));
            newsPo.setUrl(article.getString("url"));
            newsPo.setShowpic(article.getIntValue("show_cover_pic"));
            newsPo.setPicpath(msgNews.getPicpath());
            newsPo.setMediaId(newsMediaId);
            newsPo.setThumbMediaId(imgMediaId);
            newsPo.setNewsIndex(0);

            MediaFiles entity = new MediaFiles();
            entity.setMediaId(newsMediaId);
            entity.setMediaType("news");
            entity.setCreateTime(COMMON_FULL.getLongDate(Long.parseLong(newsResult.getString("create_time"))));
            entity.setUpdateTime(COMMON_FULL.getLongDate(Long.parseLong(newsResult.getString("update_time"))));

            int resultCount = this.entityService.addSingleNews(newsPo, entity);

            if (resultCount > 0) {
                return AjaxResult.success();
            } else {
                return AjaxResult.failure();
            }
        }
        return AjaxResult.failure();

    }

    /**
     * 添加多图文
     * 
     * @param rows
     * @param request
     * @return
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    @RequestMapping(value = "/addMoreNews", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addMoreNews(String rows, HttpServletRequest request) throws JsonParseException, IOException {

        String filePath = request.getSession().getServletContext().getRealPath("/");

        List<MsgArticle> listArts = new ArrayList<MsgArticle>();// 数据库所有图文集合
        JSONArray arrays = JSONArray.parseArray(rows);
        JSONArray arryarticles = new JSONArray();// 微信所用json集合
        MsgNews msgNew = new MsgNews();
        for (int i = 0; i < arrays.size(); i++) {
            JSONObject obj = arrays.getJSONObject(i);
            JSONObject json = new JSONObject();
            json.put("title", obj.getString("title"));
            json.put("author", obj.getString("author"));
            json.put("thumb_media_id", obj.getString("thumbMediaId") == null ? "" : obj.getString("thumbMediaId"));
            json.put("digest", obj.getString("brief") == null ? "" : obj.getString("brief"));
            json.put("show_cover_pic", obj.getIntValue("showpic"));
            json.put("content_source_url", obj.getString("fromurl") == null ? "" : obj.getString("fromurl"));
            MsgArticle art = new MsgArticle();
            art.setNewsIndex(i);
            art.setTitle(obj.getString("title"));
            art.setAuthor(obj.getString("author") == null ? "" : obj.getString("author"));
            art.setContentSourceUrl(obj.getString("fromurl")==null?"":obj.getString("fromurl"));
            art.setDigest(obj.getString("brief")==null?"":obj.getString("brief"));
            art.setPicUrl(obj.getString("picpath")==null?"":obj.getString("picpath"));
            art.setShowCoverPic(obj.getIntValue("showpic"));
            art.setThumbMediaId(obj.getString("thumbMediaId")==null?"":obj.getString("thumbMediaId"));
            art.setContent(obj.getString("description")==null?"":obj.getString("description"));
            if (i == 0) {
                msgNew.setAuthor(art.getAuthor());
                msgNew.setBrief(art.getDigest());
                msgNew.setDescription(art.getContent());
                msgNew.setFromurl(art.getContentSourceUrl());
                msgNew.setMultType(2);
                msgNew.setPicpath(art.getPicUrl());
                msgNew.setShowpic(art.getShowCoverPic());
                msgNew.setTitle(art.getTitle());
                msgNew.setThumbMediaId(art.getThumbMediaId());
            }
            // 注意这是图文正文部分
            String description = obj.getString("description")==null?"":obj.getString("description");
            description = description.replaceAll("'", "\"");
            // 去多个img的src值
            String subFilePath = "";
            String subOldFilePath = "";
            if (description.contains("img")) {
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher m = p.matcher(description);

                while (m.find()) {
                    String imgSrc = m.group(1);
                    subOldFilePath += imgSrc + ",";
                    String[] split = imgSrc.split("/");
                    int k = imgSrc.indexOf(split[split.length - 2]);
                    String subImgSrc = imgSrc.substring(k, imgSrc.length());
                    subFilePath += filePath + subImgSrc + ",";
                }
            }
            if (StringUtils.isNotBlank(subFilePath)) {

                subFilePath = subFilePath.substring(0, subFilePath.length() - 1);
                subOldFilePath = subOldFilePath.substring(0, subOldFilePath.length() - 1);

                MpAccount mpAccount = WxMemoryCacheClient.getMpAccount();// 获取缓存中的唯一账号
                // 本地图片地址
                String[] imgPathArry = subFilePath.split(",");
                String[] imgOldPathArry = subOldFilePath.split(",");

                String[] newPathArry = new String[imgPathArry.length];
                for (int j = 0; j < imgPathArry.length; j++) {
                    String newFilePath = imgPathArry[j];
                    // 添加永久图片
                    String materialType = MediaType.Image.toString();
                    // 将图片上传到微信，返回url
                    JSONObject imgResultObj = WxApiClient.uploadMaterialImg(newFilePath, mpAccount);

                    // 上传图片的id
                    // String contentImgMediaId = "";
                    String contentContentUrl = "";
                    if (imgResultObj != null && imgResultObj.containsKey("url")) {
                        // 微信返回来的媒体素材id
                        // contentImgMediaId = imgResultObj.getString("media_id");
                        // 图片url
                        contentContentUrl = imgResultObj.getString("url");
                    }
                    newPathArry[j] = contentContentUrl;
                }

                for (int j = 0; j < imgPathArry.length; j++) {
                    description = description.replace(imgOldPathArry[j], newPathArry[j]);
                }
            }
            json.put("content", description);
            arryarticles.add(json);
            listArts.add(art);
        }

        MpAccount mpAccount = WxMemoryCacheClient.getMpAccount();// 获取缓存中的唯一账号
        // 添加多图文永久素材
        JSONObject resultObj = WxApiClient.addMoreNewsMaterial2(arryarticles, mpAccount);
        // 数据库存储使用
        List<MsgArticle> listArticles = new ArrayList<MsgArticle>();// 数据库所有图文集合

        if (resultObj != null && resultObj.containsKey("media_id")) {
            String newsMediaId = resultObj.getString("media_id");
            msgNew.setMediaId(newsMediaId);
            JSONObject newsResult = WxApiClient.getMaterial(newsMediaId, mpAccount);

            JSONArray articles = newsResult.getJSONArray("news_item");

            for (int i = 0; i < articles.size(); i++) {
                JSONObject article = (JSONObject) articles.get(i);
                if (i == 0) {
                    msgNew.setUrl(article.getString("url"));
                }
                MsgArticle msgart = listArts.get(i);
                msgart.setUrl(article.getString("url"));
                msgart.setMediaId(newsMediaId);
                listArticles.add(msgart);
            }
            msgNew.setArticles(listArticles);

            int bl = this.entityService.addMoreNews(msgNew);
            if (bl == 1) {
                return AjaxResult.success();
            }
        }
        return AjaxResult.failure();
    }

    /**
     * 删除永久图文素材
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteMaterial", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult deleteMaterial(String id) {
        MsgNews news = entityService.getById(id);
        MpAccount mpAccount = WxMemoryCacheClient.getMpAccount();// 获取缓存中的唯一账号
        // 添加多图文永久素材
        JSONObject jsonObject = WxApiClient.deleteMaterial(news.getMediaId(), mpAccount);

        if (null != jsonObject && jsonObject.containsKey("errcode") && jsonObject.getIntValue("errcode") == 0) {

            try {
                this.entityService.delete(news);
                return AjaxResult.deleteSuccess();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return AjaxResult.failure();
    }

    /**
     * 跳转到更新页面
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/toUpdateSingleNews", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult toUpdateSingleNews(String id) {
        MsgNews newsObj = entityService.getById(id);

        return AjaxResult.success(newsObj);
    }

    /**
     * 更新单图文素材
     * 
     * @param msgNews
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateSingleNews", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateSingleNews(MsgNews msgNews, HttpServletRequest request) {
        String filePath = request.getSession().getServletContext().getRealPath("/");

        String description = msgNews.getDescription();
        String description2 = msgNews.getDescription();
        description = description.replaceAll("'", "\"");
        // 去多个img的src值
        String subFilePath = "";
        String subOldFilePath = "";
        if (description.contains("img")) {
            Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
            Matcher m = p.matcher(description);

            while (m.find()) {
                String imgSrc = m.group(1);
                subOldFilePath += imgSrc + ",";
                String[] split = imgSrc.split("/");
                int k = imgSrc.indexOf(split[split.length - 2]);
                String subImgSrc = imgSrc.substring(k, imgSrc.length());
                subFilePath += filePath + subImgSrc + ",";
            }
        }
        if (StringUtils.isNotBlank(subFilePath)) {
            subFilePath = subFilePath.substring(0, subFilePath.length() - 1);
            subOldFilePath = subOldFilePath.substring(0, subOldFilePath.length() - 1);
            // 本地图片地址
            String[] imgPathArry = subFilePath.split(",");
            String[] imgOldPathArry = subOldFilePath.split(",");

            String[] newPathArry = new String[imgPathArry.length];
            for (int i = 0; i < imgPathArry.length; i++) {
                String newFilePath = imgPathArry[i];
                // 添加永久图片
                String materialType = MediaType.Image.toString();
                // 将图片上传到微信，返回url
                JSONObject imgResultObj = WxApiClient.uploadMaterialImg(newFilePath,
                        WxMemoryCacheClient.getMpAccount());

                // 上传图片的id
                // String contentImgMediaId = "";
                String contentContentUrl = "";
                if (imgResultObj != null && imgResultObj.containsKey("url")) {
                    // 微信返回来的媒体素材id
                    // contentImgMediaId = imgResultObj.getString("media_id");
                    // 图片url
                    contentContentUrl = imgResultObj.getString("url");
                }
                newPathArry[i] = contentContentUrl;
            }

            for (int i = 0; i < imgPathArry.length; i++) {
                description = description.replace(imgOldPathArry[i], newPathArry[i]);
            }
        }

        // 内容保存
        msgNews.setDescription(description);
        List<MsgNews> msgNewsList = new ArrayList<MsgNews>();

        msgNewsList.add(msgNews);

        String mediaId = msgNews.getMediaId();

        MpAccount mpAccount = WxMemoryCacheClient.getMpAccount();// 获取缓存中的唯一账号

        JSONObject resultObj = WxApiClient.updateNewsMaterial(msgNewsList, 0, mediaId, mpAccount);

        if (null != resultObj && resultObj.containsKey("errcode") && resultObj.getIntValue("errcode") == 0) {
            msgNews.setDescription(description2);
            try {
                // 更新成功
                this.entityService.updateSingleNews(msgNews);
                return AjaxResult.updateSuccess();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return AjaxResult.failure();
    }

    /**
     * 跳转到更新页面
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/toUpdateMoreNews")
    @ResponseBody
    public AjaxResult toUpdateMoreNews(String id) {
        MsgNews newsObj = entityService.getById(id);
        return AjaxResult.success(newsObj.getArticles());
    }

    /**
     * 更新多图文
     * 
     * @param rows
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateSubMoreNews", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateMoreNews(String rows, HttpServletRequest request) {
        String filePath = request.getSession().getServletContext().getRealPath("/");
        MsgArticle article = (MsgArticle) JSONObject.parseObject(rows, MsgArticle.class);
        String description = article.getContent();
        String description2 = article.getContent();
        description = description.replaceAll("'", "\"");
        // 去多个img的src值
        String subFilePath = "";
        String subOldFilePath = "";
        if (description.contains("img")) {
            Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
            Matcher m = p.matcher(description);

            while (m.find()) {
                String imgSrc = m.group(1);
                subOldFilePath += imgSrc + ",";
                String[] split = imgSrc.split("/");
                int k = imgSrc.indexOf(split[split.length - 2]);
                String subImgSrc = imgSrc.substring(k, imgSrc.length());
                subFilePath += filePath + subImgSrc + ",";
            }
        }
        if (StringUtils.isNotBlank(subFilePath)) {

            subFilePath = subFilePath.substring(0, subFilePath.length() - 1);
            subOldFilePath = subOldFilePath.substring(0, subOldFilePath.length() - 1);

            // 本地图片地址
            String[] imgPathArry = subFilePath.split(",");
            String[] imgOldPathArry = subOldFilePath.split(",");

            String[] newPathArry = new String[imgPathArry.length];
            for (int i = 0; i < imgPathArry.length; i++) {
                String newFilePath = imgPathArry[i];
                // 添加永久图片
                String materialType = MediaType.Image.toString();
                // 将图片上传到微信，返回url
                JSONObject imgResultObj = WxApiClient.uploadMaterialImg(newFilePath,
                        WxMemoryCacheClient.getMpAccount());

                // 上传图片的id
                // String contentImgMediaId = "";
                String contentContentUrl = "";
                if (imgResultObj != null && imgResultObj.containsKey("url")) {
                    // 微信返回来的媒体素材id
                    // contentImgMediaId = imgResultObj.getString("media_id");
                    // 图片url
                    contentContentUrl = imgResultObj.getString("url");
                }
                newPathArry[i] = contentContentUrl;
            }

            for (int i = 0; i < imgPathArry.length; i++) {
                description = description.replace(imgOldPathArry[i], newPathArry[i]);
            }
        }

        // 内容保存
        article.setContent(description);
        MpAccount mpAccount = WxMemoryCacheClient.getMpAccount();// 获取缓存中的唯一账号

        JSONObject jsonObj = new JSONObject();
        // 上传图片素材
        jsonObj.put("thumb_media_id", article.getThumbMediaId());
        if (article.getAuthor() != null) {
            jsonObj.put("author", article.getAuthor());
        } else {
            jsonObj.put("author", "");
        }
        if (article.getTitle() != null) {
            jsonObj.put("title", article.getTitle());
        } else {
            jsonObj.put("title", "");
        }
        if (article.getContentSourceUrl() != null) {
            jsonObj.put("content_source_url", article.getContentSourceUrl());
        } else {
            jsonObj.put("content_source_url", "");
        }
        if (article.getDigest() != null) {
            jsonObj.put("digest", article.getDigest());
        } else {
            jsonObj.put("digest", "");
        }
        if (article.getShowCoverPic() != null) {
            jsonObj.put("show_cover_pic", article.getShowCoverPic());
        } else {
            jsonObj.put("show_cover_pic", 1);
        }
        jsonObj.put("content", article.getContent());

        JSONObject resultObj = WxApiClient.updateNewsMaterial2(jsonObj, article.getNewsIndex(), article.getMediaId(),
                mpAccount);

        if (null != resultObj && resultObj.containsKey("errcode") && resultObj.getIntValue("errcode") == 0) {
            article.setContent(description2);
            // 更新成功
            this.articleService.update(article);
            // 修改图文news表数据
            MsgNews msgNews = this.entityService.getById(String.valueOf(article.getNewsId()));
            List<MsgArticle> newArticles = msgNews.getArticles();
            if (newArticles.get(0).getArId() == article.getArId()) {
                // 这里只修改title 为了模糊查询的时候可以查询到数据
                msgNews.setTitle(article.getTitle());
                this.entityService.updateMediaId(msgNews);
            }
            return AjaxResult.updateSuccess();
        } else {
            return AjaxResult.failure();
        }

    }

}
