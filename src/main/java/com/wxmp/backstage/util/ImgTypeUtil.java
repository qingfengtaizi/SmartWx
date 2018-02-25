package com.wxmp.backstage.util;

public class ImgTypeUtil {
	private static final String typeString = "jpg,bmp,tga,vst,pcd,pct,gif,ai,fpx,img,cal,wi,png,tiff,psd,eps,raw,pdf,png,pxr,mac,eps,ai,sct,pdf,pdp,dxf";
	
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
			int result = typeString.indexOf(nString);
			if(result == -1){
				return false;
			}else {
				return true;
			}
		}else {
			//扩展名
			int result = typeString.indexOf(ObjName);
			if(result == -1){
				return false;
			}else {
				return true;
			}
		}
	}
}
