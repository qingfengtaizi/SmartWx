package com.wxmp.wxcms.domain;

/** 
 * @title : 多图文主表
 * @description : 
 * @projectname : wxmp
 * @classname : MediaFiles
 * @version 1.0
 * @author : hermit
 * @createtime : 2016年6月14日 下午1:11:48 
*/
public class MediaFiles {
	private int id;
   	private String mediaType;
   	private String title;
   	private String introduction;
   	private String logicClass;
   	private String mediaId;
   	private String uploadUrl;
   	private String rmk;
   	private long createTime;
   	private long updateTime;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the mediaType
	 */
	public String getMediaType() {
		return mediaType;
	}
	/**
	 * @param mediaType the mediaType to set
	 */
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the introduction
	 */
	public String getIntroduction() {
		return introduction;
	}
	/**
	 * @param introduction the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	/**
	 * @return the logicClass
	 */
	public String getLogicClass() {
		return logicClass;
	}
	/**
	 * @param logicClass the logicClass to set
	 */
	public void setLogicClass(String logicClass) {
		this.logicClass = logicClass;
	}
	/**
	 * @return the uploadUrl
	 */
	public String getUploadUrl() {
		return uploadUrl;
	}
	/**
	 * @param uploadUrl the uploadUrl to set
	 */
	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}
	/**
	 * @return the rmk
	 */
	public String getRmk() {
		return rmk;
	}
	/**
	 * @param rmk the rmk to set
	 */
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	/**
	 * @return the createTime
	 */
	public long getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return the updateTime
	 */
	public long getUpdateTime() {
		return updateTime;
	}
	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * @return the mediaId
	 */
	public String getMediaId() {
		return mediaId;
	}
	/**
	 * @param mediaId the mediaId to set
	 */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	
}
