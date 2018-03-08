package com.wxmp.wxapi.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.wxmp.wxapi.vo.Article;
import com.wxmp.wxapi.vo.MsgRequest;
import com.wxmp.wxapi.vo.MsgResponseNews;
import com.wxmp.wxapi.vo.MsgResponseText;
import com.wxmp.wxcms.domain.MsgNews;
import com.wxmp.wxcms.domain.MsgText;

/**
 * 消息builder工具类
 */
public class WxMessageBuilder {
	
	//客服文本消息
	public static String prepareCustomText(String openid,String content){
		JSONObject jsObj = new JSONObject();
		jsObj.put("touser", openid);
		jsObj.put("msgtype", MsgType.Text.name());
		JSONObject textObj = new JSONObject();
		textObj.put("content", content);
		jsObj.put("text", textObj);
		return jsObj.toString();
	}
	
	
	//获取 MsgResponseText 对象
	public static MsgResponseText getMsgResponseText(MsgRequest msgRequest,MsgText msgText){
		if(msgText != null){
			MsgResponseText reponseText = new MsgResponseText();
			reponseText.setToUserName(msgRequest.getFromUserName());
			reponseText.setFromUserName(msgRequest.getToUserName());
			reponseText.setMsgType(MsgType.Text.toString());
			reponseText.setCreateTime(new Date().getTime());
			reponseText.setContent(msgText.getContent());
			return reponseText;
		}else{
			return null;
		}
	}
	
	//获取 MsgResponseNews 对象
	public static MsgResponseNews getMsgResponseNews(MsgRequest msgRequest,List<MsgNews> msgNews){
		if(msgNews != null && msgNews.size() > 0){
			MsgResponseNews responseNews = new MsgResponseNews();
			responseNews.setToUserName(msgRequest.getFromUserName());
			responseNews.setFromUserName(msgRequest.getToUserName());
			responseNews.setMsgType(MsgType.News.toString());
			responseNews.setCreateTime(new Date().getTime());
			responseNews.setArticleCount(msgNews.size());
			List<Article> articles = new ArrayList<Article>(msgNews.size());
			for(MsgNews n : msgNews){
				Article a = new Article();
				a.setTitle(n.getTitle());
				a.setPicUrl(n.getPicpath());
				if(StringUtils.isEmpty(n.getFromurl())){
					a.setUrl(n.getUrl());
				}else{
					a.setUrl(n.getFromurl());
				}
				a.setDescription(n.getBrief());
				articles.add(a);
			}
			responseNews.setArticles(articles);
			return responseNews;
		}else{
			return null;
		}
	}
	
	//客服图文消息
	public static String prepareCustomNews(String openid,MsgNews msgNews){
		JSONObject jsObj = new JSONObject();
		jsObj.put("touser", openid);
		jsObj.put("msgtype", MsgType.News.toString().toLowerCase());
		JSONObject newsObj = new JSONObject();
		JSONObject articles = new JSONObject();
		JSONArray articleArray = new JSONArray();
		newsObj.put("title", msgNews.getTitle());
		newsObj.put("description", msgNews.getDescription());
		newsObj.put("url", msgNews.getUrl());
		newsObj.put("picurl", msgNews.getPicpath());
		articleArray.add(newsObj);
		articles.put("articles", articleArray);
		jsObj.put("news", articles);
		return jsObj.toString();
	}
	public static void main(String[] args) {
		JSONObject jsObj = new JSONObject();
		jsObj.put("touser", 1);
		jsObj.put("msgtype", 2);
		JSONObject articles = new JSONObject();
		JSONArray articleArray = new JSONArray();
		JSONObject newsObj = new JSONObject();
		newsObj.put("title", 3);
		newsObj.put("description", 4);
		articleArray.add(newsObj);
		articles.put("articles", articleArray);
		jsObj.put("news", articles);
		System.out.println(jsObj.toString());
	}
}
