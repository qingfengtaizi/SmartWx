package com.wxmp.wxcms.ctrl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wxmp.core.spring.SpringFreemarkerContextPathUtil;
import com.wxmp.core.util.PropertiesConfigUtil;
import com.wxmp.core.util.UploadUtil;
import com.wxmp.wxapi.process.MediaType;
import com.wxmp.wxapi.process.MpAccount;
import com.wxmp.wxapi.process.WxApiClient;
import com.wxmp.wxapi.process.WxMemoryCacheClient;
import com.wxmp.wxcms.domain.MediaFiles;
import com.wxmp.wxcms.domain.MsgBase;
import com.wxmp.wxcms.domain.MsgNews;
import com.wxmp.wxcms.service.MsgBaseService;
import com.wxmp.wxcms.service.MsgNewsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */

@Controller
@RequestMapping("/msgnews")
public class MsgNewsCtrl {

	@Autowired
	private MsgNewsService entityService;

	@Autowired
	private MsgBaseService baseService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(String id) {
		entityService.getById(id);
		return new ModelAndView();
	}

	@RequestMapping(value = "/list")
	public ModelAndView list(@ModelAttribute MsgNews searchEntity) {
		ModelAndView modelAndView = new ModelAndView("wxcms/msgnewsList");
		List<MsgNews> pageList = entityService.listForPage(searchEntity);
		modelAndView.addObject("pageList", pageList);
		modelAndView.addObject("cur_nav", "news");
		return modelAndView;
	}

	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(MsgNews entity) {
		ModelAndView mv = new ModelAndView("wxcms/msgnewsMerge");
		mv.addObject("cur_nav", "news");

		if (entity.getId() != null) {
			MsgNews news = entityService.getById(entity.getId().toString());
			mv.addObject("entity", news);
			mv.addObject("baseEntity", baseService.getById(news.getBaseId().toString()));
		} else {
			mv.addObject("entity", new MsgNews());
			mv.addObject("baseEntity", new MsgBase());
		}
		return mv;
	}

	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(HttpServletRequest request, MsgNews entity,
			@RequestParam(value = "imageFile", required = false) MultipartFile file) {
		String contextPath = request.getContextPath();
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ contextPath;
		String realPath = request.getSession().getServletContext().getRealPath("/");

		// 读取配置文上传件的路径
		if (PropertiesConfigUtil.getProperty("upload.properties", "upload.path") != null) {
			realPath = PropertiesConfigUtil.getProperty("upload.properties", "upload.path").toString();
		}

		if (file != null && file.getSize() > 0) {
			String tmpPath = UploadUtil.doUpload(realPath, file);// 上传文件，上传文件到
																	// /res/upload/
																	// 下
			entity.setPicpath(url + tmpPath);
		} else {
			if (entity.getId() != null) {// 更新
				entity.setPicpath(entityService.getById(entity.getId().toString()).getPicpath());
			}
		}

		if (!StringUtils.isEmpty(entity.getFromurl())) {
			String fromUrl = entity.getFromurl();
			if (!fromUrl.startsWith("http://")) {
				entity.setFromurl("http://" + fromUrl);
			}
		} else {
			entity.setUrl(url + "/wxweb/msg/newsread");// 设置微信访问的url
		}

		if (entity.getId() != null) {// 跟新
			entityService.update(entity);
		} else {
			entityService.add(entity);
		}
		return new ModelAndView("redirect:/msgnews/list");
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(MsgNews entity) {
		entityService.delete(entity);
		return new ModelAndView("redirect:/msgnews/list");
	}

	@RequestMapping(value = "/textUploadImg")
	public ModelAndView textUploadImg() {
		ModelAndView mv = new ModelAndView("wxcms/msgnewsList");
		String filePath = "D:/img/Tulips.jpg";
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			try {
				throw new IOException("文件不存在");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
		// 添加永久图片
		String materialType = MediaType.Image.toString();
		JSONObject result = WxApiClient.addMaterial(filePath, materialType, mpAccount);

		return mv;
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
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
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
	public String addSingleNews(MsgNews msgNews, HttpServletRequest request) {

		String filePath = request.getSession().getServletContext().getRealPath("/");

		String description = msgNews.getDescription();
		description = description.replaceAll("'","\"");
		//去多个img的src值
		String subFilePath = "";
		String subOldFilePath = "";
		if (description.contains("img")) {
			Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
			Matcher m = p.matcher(description);

			while (m.find()) {
				// System.out.println(m.group()+"-------------↓↓↓↓↓↓");
				System.out.println(m.group(1));
				String imgSrc = m.group(1);
				subOldFilePath +=  imgSrc + ",";
				String subImgSrc = imgSrc.substring(imgSrc.indexOf('/', 2)+1, imgSrc.length());
				subFilePath += filePath + subImgSrc + ",";
			}
		}
		subFilePath = subFilePath.substring(0, subFilePath.length() -1);
		subOldFilePath = subOldFilePath.substring(0, subOldFilePath.length() -1);
		
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
        //本地图片地址
		String[] imgPathArry = subFilePath.split(",");
		String[] imgOldPathArry = subOldFilePath.split(",");
		
	    String[] newPathArry = new String[imgPathArry.length];
        for(int i=0;i<imgPathArry.length;i++){
    	    String newFilePath = imgPathArry[i];
	    	// 添加永久图片
	   		String materialType = MediaType.Image.toString();
	   		// 将图片同步到微信，返回mediaId
	   		JSONObject imgResultObj = WxApiClient.addMaterial(newFilePath, materialType, mpAccount);
	
	   		// 上传图片的id
	   		String contentImgMediaId = "";
	   		String contentContentUrl = "";
	   		if (imgResultObj != null && imgResultObj.containsKey("media_id")) {
	   			// 微信返回来的媒体素材id
	   			contentImgMediaId = imgResultObj.getString("media_id");
	   			// 图片url
	   			contentContentUrl = imgResultObj.getString("url");
	   		} 
    	    newPathArry[i] = contentContentUrl;
        }
        
	    for(int i=0;i<imgPathArry.length;i++){
	    	description =  description.replace(imgOldPathArry[i], newPathArry[i]);
	    }
		
		//内容保存
		msgNews.setDescription(description);
 
		String code = "";
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

			newsPo.setTitle(article.getString("title"));
			newsPo.setAuthor(article.getString("author"));
			newsPo.setBrief(article.getString("digest"));
			newsPo.setDescription(article.getString("content"));
			newsPo.setFromurl(article.getString("url"));
			newsPo.setUrl(article.getString("content_source_url"));
			newsPo.setShowpic(article.getInt("show_cover_pic"));
			newsPo.setPicpath(msgNews.getPicpath());
			newsPo.setMediaId(newsMediaId);
			newsPo.setThumbMediaId(imgMediaId);
			newsPo.setNewsIndex(0);

			MediaFiles entity = new MediaFiles();
			entity.setMediaId(newsMediaId);
			entity.setMediaType("news");
			entity.setCreateTime(Long.parseLong(newsResult.getString("create_time")));
			entity.setUpdateTime(Long.parseLong(newsResult.getString("update_time")));

			int resultCount = this.entityService.addSingleNews(newsPo, entity);

			if (resultCount > 0) {
				code = "1";
			} else {
				code = "-1";
			}

		} else {
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
	@RequestMapping(value = "/addSingleNews_old", method = RequestMethod.POST)
	@ResponseBody
	public String addSingleNews_old(MsgNews msgNews, HttpServletRequest request) {
		String code = "";
		List<MsgNews> msgNewsList = new ArrayList<MsgNews>();
		msgNewsList.add(msgNews);
		// 封面图片媒体id
		String imgMediaId = msgNews.getThumbMediaId();

		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
		JSONObject resultObj = WxApiClient.addNewsMaterial(msgNewsList, imgMediaId, mpAccount);

		if (resultObj != null && resultObj.containsKey("media_id")) {
			String newsMediaId = resultObj.getString("media_id");
			JSONObject newsResult = WxApiClient.getMaterial(newsMediaId, mpAccount);

			JSONArray articles = newsResult.getJSONArray("news_item");
			JSONObject article = (JSONObject) articles.get(0);
			MsgNews newsPo = new MsgNews();

			newsPo.setTitle(article.getString("title"));
			newsPo.setAuthor(article.getString("author"));
			newsPo.setBrief(article.getString("digest"));
			newsPo.setDescription(article.getString("content"));
			newsPo.setFromurl(article.getString("url"));
			newsPo.setUrl(article.getString("content_source_url"));
			newsPo.setShowpic(article.getInt("show_cover_pic"));
			newsPo.setPicpath(msgNews.getPicpath());
			newsPo.setMediaId(newsMediaId);
			newsPo.setThumbMediaId(imgMediaId);
			newsPo.setNewsIndex(0);

			MediaFiles entity = new MediaFiles();
			entity.setMediaId(newsMediaId);
			entity.setMediaType("news");
			entity.setCreateTime(Long.parseLong(newsResult.getString("create_time")));
			entity.setUpdateTime(Long.parseLong(newsResult.getString("update_time")));

			int resultCount = this.entityService.addSingleNews(newsPo, entity);

			if (resultCount > 0) {
				code = "1";
			} else {
				code = "-1";
			}

		} else {
			code = "-1";
		}
		return code;
	}

	/**
	 * 添加多图文
	 * 
	 * @param msgNews
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addMoreNews", method = RequestMethod.POST)
	@ResponseBody
	public String addMoreNews(String rows, HttpServletRequest request) {
		String code = "1";

		Gson gson = new Gson();
		List<MsgNews> msgNewsList = gson.fromJson(rows, new TypeToken<List<MsgNews>>() {
		}.getType());

		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
		// 添加多图文永久素材
		JSONObject resultObj = WxApiClient.addMoreNewsMaterial(msgNewsList, mpAccount);

		if (resultObj != null && resultObj.containsKey("media_id")) {
			String newsMediaId = resultObj.getString("media_id");
			JSONObject newsResult = WxApiClient.getMaterial(newsMediaId, mpAccount);

			JSONArray articles = newsResult.getJSONArray("news_item");

			for (int i = 0; i < articles.size(); i++) {
				JSONObject article = (JSONObject) articles.get(i);

				MsgNews msgNewPo = msgNewsList.get(i);

				MsgNews newsPo = new MsgNews();

				newsPo.setTitle(article.getString("title"));
				newsPo.setAuthor(article.getString("author"));
				newsPo.setBrief(article.getString("digest"));
				newsPo.setDescription(article.getString("content"));
				newsPo.setFromurl(article.getString("url"));
				newsPo.setUrl(article.getString("content_source_url"));
				newsPo.setShowpic(article.getInt("show_cover_pic"));
				newsPo.setMediaId(newsMediaId);
				newsPo.setPicpath(msgNewPo.getPicpath());
				newsPo.setThumbMediaId(article.getString("thumb_media_id"));
				newsPo.setNewsIndex(i);

				int newsCount = this.entityService.addMoreNews(newsPo);
			}

			MediaFiles entity = new MediaFiles();
			entity.setMediaId(newsMediaId);
			entity.setMediaType("more");
			entity.setCreateTime(System.currentTimeMillis());
			entity.setUpdateTime(System.currentTimeMillis());

			int fileCount = this.entityService.addMediaFiles(entity);

			if (fileCount > 0) {
				code = "1";
			} else {
				code = "-1";
			}

		} else {
			code = "-1";
		}
		return code;
	}

	/**
	 * 删除永久图文素材
	 * 
	 * @param mediaId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteMaterial", method = RequestMethod.POST)
	@ResponseBody
	public String deleteMaterial(String mediaId, HttpServletRequest request) {
		String code = "0";
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
		// 添加多图文永久素材
		JSONObject jsonObject = WxApiClient.deleteMaterial(mediaId, mpAccount);

		if (null != jsonObject && jsonObject.containsKey("errcode") && jsonObject.getInt("errcode") == 0) {

			try {
				this.entityService.deleteNews(mediaId);
				code = "1";
			} catch (Exception e) {
				code = "-1";
				e.printStackTrace();
			}

		} else {
			// 删除微信图文信息失败
			code = "-1";
		}
		return code;
	}

	/**
	 * 跳转到更新页面
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/toUpdateSingleNews")
	public ModelAndView toUpdateSingleNews(String mediaId) {
		ModelAndView mv = new ModelAndView("wxcms/editsinglenews");
		List<MsgNews> newsList = entityService.getByMediaId(mediaId);

		MsgNews newsObj = (MsgNews) newsList.get(0);
		mv.addObject("newsObj", newsObj);
		return mv;
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
	public String updateSingleNews(MsgNews msgNews, HttpServletRequest request) {
		String code = "";
		List<MsgNews> msgNewsList = new ArrayList<MsgNews>();

		msgNewsList.add(msgNews);

		String mediaId = msgNews.getMediaId();

		System.out.println("媒体id：================" + mediaId);

		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号

		JSONObject resultObj = WxApiClient.updateNewsMaterial(msgNewsList, 0, mediaId, mpAccount);

		if (null != resultObj && resultObj.containsKey("errcode") && resultObj.getInt("errcode") == 0) {

			try {
				// 更新成功
				this.entityService.updateSingleNews(msgNews);
				code = "1";
			} catch (Exception e) {
				code = "-1";
				e.printStackTrace();
			}

		} else {
			// 更新图文消息失败
			code = "-1";
		}
		return code;
	}

	/**
	 * 跳转到更新页面
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/toUpdateMoreNews")
	public ModelAndView toUpdateMoreNews(String mediaId) {
		ModelAndView mv = new ModelAndView("wxcms/editmorenews");
		List<MsgNews> newsList = entityService.getByMediaId(mediaId);

		MsgNews newsObj = (MsgNews) newsList.get(0);
		mv.addObject("newsList", newsList);
		mv.addObject("newsObj", newsObj);
		return mv;
	}

	/**
	 * 更新多图文
	 * 
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateMoreNews", method = RequestMethod.POST)
	@ResponseBody
	public String updateMoreNews(String rows, HttpServletRequest request) {
		String code = "0";
		Gson gson = new Gson();
		List<MsgNews> msgNewsList = gson.fromJson(rows, new TypeToken<List<MsgNews>>() {
		}.getType());

		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号

		for (int i = 0; i < msgNewsList.size(); i++) {
			MsgNews news = msgNewsList.get(i);
			JSONObject resultObj = WxApiClient.updateNewsMaterial(msgNewsList, i, news.getMediaId(), mpAccount);
			if (null != resultObj && resultObj.containsKey("errcode") && resultObj.getInt("errcode") == 0) {
				// 更新成功
				this.entityService.updateSingleNews(news);
				code = "1";
			} else {
				code = "-1";
			}
		}

		return code;
	}

}