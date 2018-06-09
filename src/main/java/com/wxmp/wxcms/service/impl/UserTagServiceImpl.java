package com.wxmp.wxcms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wxmp.core.exception.WxErrorException;
import com.wxmp.wxapi.process.HttpMethod;
import com.wxmp.wxapi.process.MpAccount;
import com.wxmp.wxapi.process.WxApi;
import com.wxmp.wxapi.process.WxApiClient;
import com.wxmp.wxcms.domain.UserTag;
import com.wxmp.wxcms.mapper.UserTagDao;
import com.wxmp.wxcms.service.UserTagService;

@Service
public class UserTagServiceImpl implements UserTagService {

	@Resource
	private UserTagDao userTagDao;
	
	private Logger logger=Logger.getLogger(UserTagServiceImpl.class);
	
 	
	//同步粉丝列表
	@SuppressWarnings("unchecked")
	public boolean syncUserTagList(MpAccount mpAccount){
	 		String url=null;
			try {
				url = WxApi.getUserTagList(WxApiClient.getAccessToken(mpAccount));
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
			logger.info("同步用户标签参消息如下:"+url);
			JSONObject jsonObject = WxApi.httpsRequest(url, HttpMethod.GET, null);
			logger.info("同步用户标签消息如下:"+jsonObject.toString());
			if(jsonObject.containsKey("errcode")){
				return false;
			}
	    	JSONArray arr = jsonObject.getJSONArray("tags");//获取jsonArray对象
	    	String js=JSONObject.toJSONString(arr);//将array数组转换成字符串
	    	List<UserTag> userTagList=JSONObject.parseArray(js, UserTag.class);//把字符串转换成集合
	    	//判断是否已经同步
	    	UserTag userTag = userTagList.stream().max(  (u,u2) -> ( u.getId() - u2.getId() )  ).get();
	    	Integer maxIdInDb = getMaxId() == null ?  0 : getMaxId();//第一次同步，数据库没有数据返回null
	    	if(userTag.getId() == maxIdInDb) {
	    		//说明已经同步
	    		return true;
	    	}else if( userTag.getId() > maxIdInDb ){
	    		//如果微信服务器新增用户标签，同步新增标签，新增标签的ID比本地库的ID大
	    		userTagList = userTagList.stream().filter( u -> u.getId() > maxIdInDb ).collect(Collectors.toList());
	    		userTagDao.addList(userTagList);	    		
	    		return true;
	    	}
	    	return true;
		}
	
	@Override
	public UserTag getById(Integer id) {
		return userTagDao.getById(id);
	}

	@Override
	public List<UserTag> listForPage(UserTag searchEntity) {
		return userTagDao.getUserTagListByPage(searchEntity);
	}

	@Override
	public void add(UserTag entity) {
		userTagDao.add(entity);
	}

	@Override
	public void update(UserTag entity) {
		userTagDao.update(entity);

	}

	@Override
	public void delete(UserTag entity) {
		userTagDao.delete(entity);
	}


	@Override
	public Integer deleteBatchIds(String[] ids) {
		return userTagDao.deleteBatchIds(ids);
	}


	@Override
	public Integer getMaxId() {
		return userTagDao.getMaxId();
	}

}
