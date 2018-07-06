/*
 * FileName：MediaTypeUtil.java 
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
package com.wxmp.core.util;

public class MediaTypeUtil {

	/**
	 * 图片后缀
	 */
	private static final String imgType = "jpg,bmp,tga,vst,pcd,pct,gif,ai,fpx,img,cal,wi,png,tiff,psd,eps,raw,pdf,png,pxr,mac,eps,ai,sct,pdf,pdp,dxf";

    /**
     * 视频后缀
     */
    private static final String videoType = "avi,mp4,wma,rmvb,rm,flash,3gp,flv";
    
    /**
     * 音频后缀
     */
    private static final String audioType = "mp3,wav,mid";



	/**
	 * 通过文件名判断是否为图片
	 * @param ObjName
	 * @return
	 */
	public static boolean isImg(String ObjName){
		if(ObjName == null){
			return false;
		}
		if(ObjName.indexOf(".") != -1){
			//全名
			String nString = ObjName.substring(ObjName.lastIndexOf(".")+1);
			int result = imgType.indexOf(nString);
			if(result == -1){
				return false;
			}else {
				return true;
			}
		}else {
			//扩展名
			int result = imgType.indexOf(ObjName);
			if(result == -1){
				return false;
			}else {
				return true;
			}
		}
	}

	/**
	 * 通过文件名判断是否为图视频
	 * @param ObjName
	 * @return
	 */
	public static boolean isVideo(String ObjName){
		if(ObjName == null){
			return false;
		}
		if(ObjName.indexOf(".") != -1){
			//全名
			String nString = ObjName.substring(ObjName.lastIndexOf(".")+1);
			int result = videoType.indexOf(nString);
			if(result == -1){
				return false;
			}else {
				return true;
			}
		}else {
			//扩展名
			int result = videoType.indexOf(ObjName);
			if(result == -1){
				return false;
			}else {
				return true;
			}
		}
	}

	/**
	 * 通过文件名判断是否为音频
	 * @param ObjName
	 * @return
	 */
	public static boolean isAudio(String ObjName){
		if(ObjName == null){
			return false;
		}
		if(ObjName.indexOf(".") != -1){
			//全名
			String nString = ObjName.substring(ObjName.lastIndexOf(".")+1);
			int result = audioType.indexOf(nString);
			if(result == -1){
				return false;
			}else {
				return true;
			}
		}else {
			//扩展名
			int result = audioType.indexOf(ObjName);
			if(result == -1){
				return false;
			}else {
				return true;
			}
		}
	}
}
