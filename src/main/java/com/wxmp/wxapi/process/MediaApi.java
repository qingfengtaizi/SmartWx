package com.wxmp.wxapi.process;

/**
 * 素材接口实现（只包括图片、语音、视频）
 * 
 * 图片（image）: 2M，支持bmp/png/jpeg/jpg/gif格式
 * 语音（voice）：2M，播放长度不超过60s，mp3/wma/wav/amr格式
 * 视频（video）：10MB，支持MP4格式
 * 缩略图（thumb）：64KB，支持JPG格式
 * 
 * @author nigulading
 *
 */
public class MediaApi {

	//永久素材添加 (image/voice/video/thumb) POST
	public static final String ADD_MATERIA="https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=%s&type=%s";
	
	//临时素材添加 (image/voice/video/thumb) POST
	public static final String ADD_MEDIA="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";
	
	//获取永久素材   POST
	public static final String GET_MATERIA="https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=%s";
	
	//删除永久素材   POST
	public static final String DEL_MATERIAL="https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=%s";
	
	
	
}
