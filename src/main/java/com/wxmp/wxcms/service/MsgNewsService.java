package com.wxmp.wxcms.service;

import java.util.List;

import com.wxmp.core.page.Pagination;
import com.wxmp.wxcms.domain.MediaFiles;
import com.wxmp.wxcms.domain.MsgNews;
import com.wxmp.wxcms.domain.MsgNewsVO;


public interface MsgNewsService {

	public MsgNews getById(String id);

	public List<MsgNews> listForPage(MsgNews searchEntity);
	
	public List<MsgNewsVO> pageWebNewsList(MsgNews searchEntity,Pagination<MsgNews> page);

	public void add(MsgNews entity);

	public void update(MsgNews entity);

	public void delete(MsgNews entity);

	//根据用户发送的文本消息，随机获取 num 条文本消息
	public List<MsgNews> getRandomMsg(String inputcode,Integer num);

	public MsgNews getByBaseId(String baseid);
	
	public int updateMediaId(MsgNews entity);
	
	public int addSingleNews(MsgNews news,MediaFiles entity);
	
	public int addMediaFiles(MediaFiles entity);
	
	public int addMoreNews(MsgNews news);
	
	/**
	 * 查询多图文主表
	 * @return
	 */
    public List<MediaFiles> getMediaFileList();
    
    
	public List<MsgNews> getMsgNewsList();
	
	
	public void deleteNews(String mediaId);
	
	/**
	 * 更新单图文消息
	 * @param news
	 */
	public void updateSingleNews(MsgNews news);
	
	
	public List<MsgNews> getByMediaId(String mediaId);
}