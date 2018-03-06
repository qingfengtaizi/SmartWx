package com.wxmp.wxcms.service.impl;

import com.wxmp.core.page.Pagination;
import com.wxmp.wxapi.process.MsgType;
import com.wxmp.wxcms.domain.MediaFiles;
import com.wxmp.wxcms.domain.MsgBase;
import com.wxmp.wxcms.domain.MsgNews;
import com.wxmp.wxcms.domain.MsgNewsVO;
import com.wxmp.wxcms.mapper.MediaFilesDao;
import com.wxmp.wxcms.mapper.MsgBaseDao;
import com.wxmp.wxcms.mapper.MsgNewsDao;
import com.wxmp.wxcms.service.MsgNewsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : hermit
 */
@Service
public class MsgNewsServiceImpl implements MsgNewsService{

	@Resource
	private MsgBaseDao baseDao;

	@Resource
	private MsgNewsDao entityDao;

	@Resource
	private MediaFilesDao mediaFilesDao;

	public MsgNews getById(String id){
		return entityDao.getById(id);
	}

	public List<MsgNews> listForPage(MsgNews searchEntity){
		return entityDao.listForPage(searchEntity);
	}
	
	public List<MsgNewsVO> pageWebNewsList(MsgNews searchEntity,Pagination<MsgNews> page){
		List<MsgNews> list = entityDao.pageWebNewsList(searchEntity,page);
		List<MsgNewsVO> pageList = new ArrayList<MsgNewsVO>();
		for(MsgNews msg : list){
			if(pageList.size() == 0){
				MsgNewsVO vo = new MsgNewsVO();
				vo.setCreateTimeStr(msg.getCreateTimeStr());
				vo.getMsgNewsList().add(msg);
				pageList.add(vo);
			}else{
				MsgNewsVO tmpMsgNewsVO = pageList.get(pageList.size() - 1);
				if(tmpMsgNewsVO.getCreateTimeStr().equals(msg.getCreateTimeStr())){
					tmpMsgNewsVO.getMsgNewsList().add(msg);
				}else{
					MsgNewsVO vo = new MsgNewsVO();
					vo.setCreateTimeStr(msg.getCreateTimeStr());
					vo.getMsgNewsList().add(msg);
					pageList.add(vo);
				}
			}
		}
		return pageList;
	}

	
	public void add(MsgNews entity){
		
		MsgBase base = new MsgBase();
		base.setInputcode(entity.getInputcode());
		base.setCreatetime(new Date());
		base.setMsgtype(MsgType.News.toString());
		baseDao.add(base);
		
		entity.setBaseId(base.getId());
		entityDao.add(entity);
		
		if(StringUtils.isEmpty(entity.getFromurl())){
			entity.setUrl(entity.getUrl()+"?id="+entity.getId());
		}else{
			entity.setUrl("");
		}
		
		entityDao.updateUrl(entity);
	}

	public void update(MsgNews entity){
		MsgBase base = baseDao.getById(entity.getBaseId().toString());
		base.setInputcode(entity.getInputcode());
		baseDao.updateInputcode(base);
		
		if(StringUtils.isEmpty(entity.getFromurl())){
			entity.setUrl(entity.getUrl()+"?id="+entity.getId());
		}else{
			entity.setUrl("");
		}
		
		entityDao.update(entity);
	}

	public void delete(MsgNews entity){
		MsgBase base = new MsgBase();
		base.setId(entity.getBaseId());
		entityDao.delete(entity);
		baseDao.delete(entity);
	}

	public List<MsgNews> getRandomMsg(String inputCode,Integer num){
		return entityDao.getRandomMsgByContent(inputCode,num);
	}
	
	public MsgNews getByBaseId(String baseid){
		return entityDao.getByBaseId(baseid);
	}

	/* (non-Javadoc)
	 * @see com.wxmp.wxcms.service.MsgNewsService#updateMediaId(com.wxmp.wxcms.domain.MsgNews)
	 */
	@Override
	public int updateMediaId(MsgNews entity) {
		int n = 0 ;
		try {
			entityDao.updateMediaId(entity);
			n = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}


	@Override
	public int addSingleNews(MsgNews news,MediaFiles entity) {
		int n=0;
	    int m = 0;
	    try {
	    	//保存图文信息
	    	this.entityDao.addNews(news);
	    	n = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    if(n > 0){
	    	 
		    try {
		    	mediaFilesDao.add(entity);
		    	m = 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    
	    if(n >0 && m >0){
	    	return 1;
	    }else{
	    	return 0;
	    }
	    
	}
	
	
	
	@Override
	public int addMediaFiles(MediaFiles entity) {
		int n=0;
	    
		    try {
		    	mediaFilesDao.add(entity);
		    	n = 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
	
	    
	    if(n >0){
	    	return 1;
	    }else{
	    	return 0;
	    }
	    
	}

	/* (non-Javadoc)
	 * @see com.wxmp.wxcms.service.MsgNewsService#getMediaFileList()
	 */
	@Override
	public List<MediaFiles> getMediaFileList() {
		return this.mediaFilesDao.getMediaFileList();
	}

	/* (non-Javadoc)
	 * @see com.wxmp.wxcms.service.MsgNewsService#getMsgNewsList()
	 */
	@Override
	public List<MsgNews> getMsgNewsList() {
		return this.entityDao.getMsgNewsList();
	}

	/* (non-Javadoc)
	 * @see com.wxmp.wxcms.service.MsgNewsService#addMoreNews(com.wxmp.wxcms.domain.MsgNews)
	 */
	@Override
	public int addMoreNews(MsgNews news) {
		int n=0;
	   
	    try {
	    	//保存图文信息
	    	this.entityDao.addNews(news);
	    	n = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return n;
	}

	/* (non-Javadoc)
	 * @see com.wxmp.wxcms.service.MsgNewsService#deleteNews(java.lang.String)
	 */
	@Override
	public void deleteNews(String mediaId) {
		this.mediaFilesDao.deleteByMediaId(mediaId);
		this.entityDao.deleteByMediaId(mediaId);
	}

	/* (non-Javadoc)
	 * @see com.wxmp.wxcms.service.MsgNewsService#updateSingleNews(com.wxmp.wxcms.domain.MsgNews)
	 */
	@Override
	public void updateSingleNews(MsgNews news) {
		this.entityDao.updateNews(news);
	}

	/* (non-Javadoc)
	 * @see com.wxmp.wxcms.service.MsgNewsService#getByMediaId(java.lang.String)
	 */
	@Override
	public List<MsgNews> getByMediaId(String mediaId) {
		// TODO Auto-generated method stub
		return this.entityDao.getByMediaId(mediaId);
	}
	
}
