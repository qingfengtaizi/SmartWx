/**
 * Copyright &copy; 2017-2018 <a href="http://www.webcsn.com">webcsn</a> All rights reserved.
 *
 * @author hermit
 * @date 2018-04-17 10:54:58
 */
package com.wxmp.wxcms.ctrl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxmp.core.common.BaseCtrl;
import com.wxmp.core.exception.BusinessException;
import com.wxmp.core.util.AjaxResult;
import com.wxmp.wxcms.domain.ImgResource;
import com.wxmp.wxcms.domain.SysUser;
import com.wxmp.wxcms.service.SysUserService;
import com.wxmp.core.spring.SpringFreemarkerContextPathUtil;
import com.wxmp.core.util.PropertiesConfigUtil;
import com.wxmp.core.util.UploadUtil;
import com.wxmp.wxapi.process.MediaType;
import com.wxmp.wxapi.process.MpAccount;
import com.wxmp.wxapi.process.WxApiClient;
import com.wxmp.wxapi.process.WxMemoryCacheClient;
import com.wxmp.wxapi.vo.Material;
import com.wxmp.wxapi.vo.MaterialArticle;
import com.wxmp.wxapi.vo.MaterialItem;
import com.wxmp.wxcms.domain.Account;
import com.wxmp.wxcms.domain.MediaFiles;
import com.wxmp.wxcms.domain.MsgNews;
import com.wxmp.wxcms.domain.MsgText;
import com.wxmp.wxcms.mapper.AccountDao;
import com.wxmp.wxcms.service.MsgNewsService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author hermit
 * @version 2.0
 * @date 2018-04-17 10:54:58
 */
@Controller
@RequestMapping("/wxcms")
public class WxCmsCtrl extends BaseCtrl {

	@Resource
	AccountDao accountDao;
	
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private MsgNewsService msgNewsService;

	@RequestMapping(value = "/urltoken")
	@ResponseBody
	public AjaxResult urltoken() {
		List<Account> accounts = accountDao.listForPage(null);
		Account account = new Account();
		if (!CollectionUtils.isEmpty(accounts)) {
			for (Account acc : accounts) {
				if (acc.getAccount().equals(WxMemoryCacheClient.getAccount())) {
					account = acc;
					break;
				}
			}
		}
		List<String> msgCountList = new ArrayList<String>();
		for (int i = 1; i < 8; i++) {
			msgCountList.add(String.valueOf(i));
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("account", account);
		data.put("msgCountList", msgCountList);
		return AjaxResult.success(data);
	}

	@RequestMapping(value = "/getUrl")
	@ResponseBody
	public AjaxResult getUrl(Account account){
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/wxapi/" + account.getAccount() + "/message.html";
		
		if(account.getId() == null){//新增
			account.setUrl(url);
			account.setToken(UUID.randomUUID().toString().replace("-", ""));
			account.setCreatetime(new Date());
			accountDao.add(account);
		}else{//更新
			Account tmpAccount = accountDao.getById(account.getId());
			tmpAccount.setUrl(url);
			tmpAccount.setAccount(account.getAccount());
			tmpAccount.setAppid(account.getAppid());
			tmpAccount.setAppsecret(account.getAppsecret());
			tmpAccount.setMsgcount(account.getMsgcount());
			tmpAccount.setName(account.getName());
			accountDao.update(tmpAccount);
		}
		WxMemoryCacheClient.addMpAccount(account);
		return AjaxResult.success(account);
	}
	
	@RequestMapping(value = "/ckeditorImage")
	public void ckeditorImage(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="imgFile",required=false)MultipartFile file){
		String contextPath = SpringFreemarkerContextPathUtil.getBasePath(request);
		String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath;
		String realPath = request.getSession().getServletContext().getRealPath("/");
		
		//读取配置文上传件的路径
		if(PropertiesConfigUtil.getProperty("property/upload.properties","upload.path") != null){
			realPath = PropertiesConfigUtil.getProperty("property/upload.properties","upload.path").toString();
		}
		
		JSONObject obj = new JSONObject();
		if(file != null && file.getSize() > 0){
			String tmpPath = UploadUtil.doUpload(realPath,file);//上传文件，上传文件到 /res/upload/ 下
			obj.put("error", 0);
			obj.put("url", url + tmpPath);
		}
		try {
			response.getWriter().write(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//上传永久素材，这里以图文消息为例子
	@RequestMapping(value = "/toUploadMaterial")
	public  ModelAndView toUploadMaterial(String[] newIds){
		ModelAndView mv = new ModelAndView("wxcms/materialUpload");
		mv.addObject("cur_nav", "material");
		return mv;
	}
	
	//到生成二维码页面
	@RequestMapping(value = "/qrcode")
	public ModelAndView qrcode(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("wxcms/qrcode");
		mv.addObject("cur_nav", "qrcode");
		return mv;
	}
	
	//发送消息页面
	@RequestMapping(value = "/sendMsg")
	public ModelAndView sendMsg(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("wxcms/sendmsg");
		mv.addObject("cur_nav", "sendmsg");
		
		return mv;
	}
	
	//通过interceptor处理OAuth认证
	@RequestMapping(value = "/oauthInterceptor")
	public ModelAndView oauthInterceptor(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("wxcms/oauthInterceptor");
		mv.addObject("cur_nav", "oauthInterceptor");
		
		return mv;
	}
	
	//jssdk
	@RequestMapping(value = "/jssdk")
	public ModelAndView jssdk(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("wxcms/jssdk");
		mv.addObject("cur_nav", "jssdk");
		
		return mv;
	}
	
	//weui 微信网页开发样式库
	@RequestMapping(value = "/weui")
	public ModelAndView weui(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("wxcms/weui");
		mv.addObject("cur_nav", "weui");
		return mv;
	}
	
	/**
	 * 登录之后跳转到主页
	 * @param userId
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/main")
	public ModelAndView main(@RequestParam(required=false) String userId,HttpSession session){
		ModelAndView mv = new ModelAndView("wxcms/main");
		SysUser sysUser = sysUserService.getSysUserById(userId);
		session.setAttribute("sysUser", sysUser);
		return mv;
	}
	
	/**
	 * 多图文素材页面
	 * zhangming @20160524
	 */
	@RequestMapping(value = "/toMultiGraphic")
	public ModelAndView toMultiGraphic(MsgText entity,
			@RequestParam(required=false,defaultValue="1") Integer page,
            @RequestParam(required=false,defaultValue="3") Integer pageSize){
		ModelAndView mv = new ModelAndView("wxcms/multiGraphic");
		
		PageHelper.startPage(page, pageSize);
		List<MediaFiles> mediaList = this.msgNewsService.getMediaFileList();
		PageInfo mediaPage=new PageInfo(mediaList);
		
		List<MsgNews> newsList = this.msgNewsService.getMsgNewsList();
		
		mv.addObject("mediaList",mediaList);
		//分页对象
		mv.addObject("page",mediaPage);
		mv.addObject("newsList",newsList);
		mv.addObject("cur_nav", "news");
		return mv;
	}	
	
	//获取永久素材
	@RequestMapping(value = "/getMaterials")
	public AjaxResult syncMaterials(MaterialArticle materialArticle) throws BusinessException{
		List<MaterialArticle> materialList = new ArrayList<MaterialArticle>();
		MpAccount mpAccount = WxMemoryCacheClient.getMpAccount();//获取缓存中的唯一账号
		Material material = WxApiClient.syncBatchMaterial(MediaType.News, materialArticle.getPage(), materialArticle.getPageSize(),mpAccount);
		if(material != null){

			List<MaterialItem> itemList = material.getItems();
			if(itemList != null){
				for(MaterialItem item : itemList){
					MaterialArticle m = new MaterialArticle();
					if(item.getNewsItems() != null && item.getNewsItems().size() > 0){
						MaterialArticle ma = item.getNewsItems().get(0);//用第一个图文的简介、标题、作者、url
						m.setAuthor(ma.getAuthor());
						m.setTitle(ma.getTitle());
						m.setUrl(ma.getUrl());
					}
					materialList.add(m);
				}
			}
		}
		return getResult(materialArticle,materialList);
	}
	
	@RequestMapping(value="/saveFile")
	@ResponseBody
	public void saveFile(MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 String fileName = file.getOriginalFilename();  
		 String ext = FilenameUtils.getExtension(fileName);
		 System.out.println(System.currentTimeMillis());
		 fileName = System.currentTimeMillis()+new Random().nextInt(10000)+"."+ext;
		 
		 String filePath = request.getSession().getServletContext().getRealPath("/")+"upload\\"+fileName;  
		 File saveFile = new File(filePath);
		 
		 if(!saveFile.exists()){
			 saveFile.mkdirs();
		 }
		 file.transferTo(saveFile);
		 
		 
		 MpAccount mpAccount = WxMemoryCacheClient.getMpAccount();//获取缓存中的唯一账号
		 //添加永久图片
		 String materialType = MediaType.Image.toString();
	    
		 //返回mediaId
		 JSONObject imgResultObj = WxApiClient.addMaterial(filePath,materialType,mpAccount);
		 ImgResource img = new ImgResource();
		
		 //上传图片的id
		 String imgMediaId = "";
		 String imgUrl = "";
		 
		 JSONObject obj = new JSONObject();
		 if(imgResultObj != null && imgResultObj.containsKey("media_id")){
				imgMediaId = imgResultObj.getString("media_id");
				imgUrl = imgResultObj.getString("url");
				img.setName(fileName);
				img.setSize((int)file.getSize());
				img.setTrueName(fileName);
				img.setType(ext);
				img.setUrl("/upload/" + fileName);
				img.setHttpUrl(imgUrl);//http地址
				obj.put("url", fileName);
				obj.put("id", "111");
				obj.put("imgMediaId", imgMediaId);
		   }else{
				obj.put("url", fileName);
				obj.put("id", "111");
				obj.put("imgMediaId", null);
	       }

		   response.getWriter().print(obj);
	}

}