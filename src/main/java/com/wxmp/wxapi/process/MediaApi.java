/*
 * FileName：MediaApi.java 
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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.wxmp.core.exception.WxError;
import com.wxmp.core.exception.WxErrorException;
import com.wxmp.core.util.HttpClientUtils;

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
	
	//获取临时素材  GET
	public static final String GET_MEDIA="https://api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
	
	//删除永久素材   POST
	public static final String DEL_MATERIAL="https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=%s";
	
	//素材文件后缀
	public static Map<String,String> type_fix= new HashMap<>();
	public static Map<String,String> media_fix= new HashMap<>();
	//素材文件大小
	public static Map<String,Long> type_length= new HashMap<>();
	static{
		type_fix.put("image","bmp|png|jpeg|jpg|gif");
		type_fix.put("voice","mp3|wma|wav|amr");
		type_fix.put("video","mp4");
		type_fix.put("thumb","jpg");
		media_fix.put("image","png|jpeg|jpg|gif");
		media_fix.put("voice","mp3|amr");
		media_fix.put("video","mp4");
		media_fix.put("thumb","jpg");
		type_length.put("image",new Long(2*1024*1024));
		type_length.put("voice",new Long(2*1024*1024));
		type_length.put("video",new Long(10*1024*1024));
		type_length.put("thumb",new Long(64*1024));
	}
	/**
	 *  永久素材添加-不包含图文
	 * @param accessToken 微信token
	 * @param type 素材类型（image/voice/video/thumb）
	 * @param fileUrl 文件的绝对路径
	 * @param params 视频数据
	 * @return
	 * @throws WxErrorException
	 */
	public static JSONObject addMateria(String accessToken,String type,String fileUrl,Map<String,String> params) throws WxErrorException{
		
		File file =new File(fileUrl);
		if(!file.exists()){
			throw new WxErrorException(WxError.newBuilder().setErrorCode(-2).setErrorMsg("文件不存在").build());
		}
		String fileName=file.getName();
		//获取后缀名
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		long length=file.length();
		//此处做判断是为了尽可能的减少对微信API的调用次数
		if(type_fix.get(type).indexOf(suffix)==-1){
			throw new WxErrorException(WxError.newBuilder().setErrorCode(40005).setErrorMsg("不合法的文件类型").build());
		}
		if(length>type_length.get(type)){
			throw new WxErrorException(WxError.newBuilder().setErrorCode(40006).setErrorMsg("不合法的文件大小").build());
		}
		String url=String.format(ADD_MATERIA, accessToken,type);
		String result = HttpClientUtils.httpsRequestToUpload(url, "POST", file, params);
		WxError wxError = WxError.fromJson(result);
		if(wxError.getErrorCode()!=0){
			throw new WxErrorException(wxError);
		}
		
		return JSONObject.parseObject(result);
	}
	/**
	 *  临时素材添加
	 * @param accessToken 微信token
	 * @param type 素材类型（image/voice/video/thumb）
	 * @param fileUrl 文件的绝对路径
	 * @return
	 * @throws WxErrorException
	 */
	public static JSONObject addMedia(String accessToken,String type,String fileUrl) throws WxErrorException{
		
		File file =new File(fileUrl);
		if(!file.exists()){
			throw new WxErrorException(WxError.newBuilder().setErrorCode(-2).setErrorMsg("文件不存在").build());
		}
		String fileName=file.getName();
		//获取后缀名
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		//文件大小，单位：b
		long length=file.length();
		//此处做判断是为了尽可能的减少对微信API的调用次数
		if(media_fix.get(type).indexOf(suffix)==-1){
			throw new WxErrorException(WxError.newBuilder().setErrorCode(40005).setErrorMsg("不合法的文件类型").build());
		}
		if(length>type_length.get(type)){
			throw new WxErrorException(WxError.newBuilder().setErrorCode(40006).setErrorMsg("不合法的文件大小").build());
		}
		String url=String.format(ADD_MEDIA, accessToken,type);
		String result = HttpClientUtils.httpsRequestToUpload(url, "POST", file);
		WxError wxError = WxError.fromJson(result);
		if(wxError.getErrorCode()!=0){
			throw new WxErrorException(wxError);
		}
		
		return JSONObject.parseObject(result);
	}
	/**
	 *  永久素材下载-包含图片、语音、缩略图
	 * @param accessToken 微信token
	 * @param mediaId 多媒体素材ID
	 * @param storageDir 文件夹目录 例如D://down
	 * @return
	 * @throws WxErrorException
	 */
	public static File downlodMateria(String accessToken,String mediaId,String storageDir)throws WxErrorException{
		String url=String.format(GET_MATERIA, accessToken);
		JSONObject json=new JSONObject();
		json.put("media_id", mediaId);
		Object obj = HttpClientUtils.httpsRequestToDownload(url, "POST", json.toJSONString(), storageDir);
		if(obj instanceof String){
			WxError wxError = WxError.fromJson((String)obj);
			throw new WxErrorException(wxError);
		}
		if(null==obj){
			throw new WxErrorException(WxError.newBuilder().setErrorCode(-3).setErrorMsg("下载出错").build());
		}
		
		return (File)obj;
	}
	/**
	 * 永久视频素材信息获取-获取url后自行处理
	 * @param accessToken 微信token
	 * @param mediaId 视频素材ID
	 * @return
	 * @throws WxErrorException
	 */
	public static JSONObject getMateriaVideo(String accessToken,String mediaId)throws WxErrorException{
		String url=String.format(GET_MATERIA, accessToken);
		JSONObject json=new JSONObject();
		json.put("media_id", mediaId);
		String result = HttpClientUtils.httpsRequestToString(url, "POST", json.toJSONString());
		WxError wxError = WxError.fromJson(result);
		if(wxError.getErrorCode()!=0){
			throw new WxErrorException(wxError);
		}
		
		return JSONObject.parseObject(result);
	}
	/**
	 * 临时素材下载-包含图片、语音、视频、缩略图
	 * @param accessToken 微信token
	 * @param mediaId 临时素材Id
	 * @param storageDir 要下载到的目录 例如D://temp
	 * @return
	 * @throws WxErrorException
	 */
	public static File downlodMedia(String accessToken,String mediaId,String storageDir)throws WxErrorException{
		String url=String.format(GET_MEDIA, accessToken,mediaId);
		Object obj = HttpClientUtils.httpsRequestToDownload(url, "GET", null, storageDir);
		if(obj instanceof String){
			WxError wxError = WxError.fromJson((String)obj);
			throw new WxErrorException(wxError);
		}
		if(null==obj){
			throw new WxErrorException(WxError.newBuilder().setErrorCode(-3).setErrorMsg("下载出错").build());
		}
		
		return (File)obj;
	}
	/**
	 * 删除永久素材
	 * @param accessToken 微信token
	 * @param mediaId 素材Id
	 * @return
	 * @throws WxErrorException
	 */
	public static Boolean delMaterial(String accessToken,String mediaId)throws WxErrorException{
		String url=String.format(DEL_MATERIAL, accessToken);
		JSONObject json=new JSONObject();
		json.put("media_id", mediaId);
		String result = HttpClientUtils.httpsRequestToString(url, "POST", json.toJSONString());
		WxError wxError = WxError.fromJson(result);
		if(wxError.getErrorCode()!=0){
			throw new WxErrorException(wxError);
		}
		return true;
	}
}
