/*
 * FileName：UploadUtil.java 
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

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.stream.FileImageOutputStream;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
/**
 * 附件上传
 * 
 */
public class UploadUtil {
	
	public static String doUpload(String realPath ,MultipartFile file){
		return doUpload(realPath,"/res/upload/",file);
	}
	
	public static String doUpload(String realPath ,String modulePath ,MultipartFile file){
		String fileName = file.getOriginalFilename();  
		if(!StringUtils.isEmpty(fileName)){
			String ext = fileName.substring(fileName.indexOf("."));
			fileName = Calendar.getInstance().getTimeInMillis() + ext;
			
			File targetFile = new File(realPath + modulePath + fileName);  
	        if(!targetFile.exists()){
	            targetFile.mkdirs();
	        }  
	        //保存
	        try {
	            file.transferTo(targetFile);  
	        } catch (Exception e) {
	            e.printStackTrace();  
	        } 
	        return "/res/upload/" + fileName;
		}
		return null;
	}
	
	public static String byteToImg(String realPath ,byte[] bytes){
		if(bytes != null && bytes.length > 0){
			String imagePath = "/res/upload/" + UUID.randomUUID().toString() + ".jpg";
			FileImageOutputStream imageOutput;
			try {
				File file = new File(realPath + imagePath);
				file.createNewFile();
				imageOutput = new FileImageOutputStream(file);
				imageOutput.write(bytes, 0, bytes.length);  
			 	imageOutput.close(); 
				return imagePath;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} 
		}
		return null;
	}
	
}
