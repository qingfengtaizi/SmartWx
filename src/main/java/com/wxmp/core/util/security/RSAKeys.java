/*
 * FileName：RSAKeys.java 
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
package com.wxmp.core.util.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.wxmp.core.exception.BusinessException;
import org.apache.log4j.Logger;
import com.wxmp.core.util.security.RSASignatureTools.KeyStoreFileType;
/**
 * RSA秘钥文件缓存类
 * @author xiaosf
 * @date 2017年6月13日
 */
public class RSAKeys {
	/**
	 * 日志对象
	 */
	private static Logger logger=Logger.getLogger(RSAKeys.class);
	/**
	 * 公钥
	 */
	private static Map<String,PublicKey> pubKey=new HashMap<String,PublicKey>();
	
	//初始化公钥
	static{
		fresh();
	}
	
	/**
	 * 根据系统id获取公钥
	 * @param sysId 系统id
	 * @return 公钥
	 * @throws Exception 找不到对应的公钥抛出此异常
	 */
	public static PublicKey getPubKeyBySys(String sysId) throws Exception{
		PublicKey key=pubKey.get(sysId);
		if(key==null){
			throw new Exception("系统["+sysId+"]没有对应的公钥配置");
		}
		return key;
	}
	
	/**
	 * 刷新
	 */
	private static void fresh(){

		InputStream is=RSAKeys.class.getClassLoader().getResourceAsStream("property/keys.properties");
		try {
			Properties prop=new Properties();
			prop.load(is);
			is.close();
			String keyFile=null;
			String[] key=null;
			KeyStoreFileType keyFileType= KeyStoreFileType.BINARY;
			for(Entry<Object, Object> entry:prop.entrySet()){
				if(pubKey.get(entry.getKey())!=null){
					continue;
				}
				keyFile=(String)entry.getValue();
				key=keyFile.split(":::");
				if(key.length>1&&"base64".equalsIgnoreCase(key[1])){
					keyFileType=KeyStoreFileType.BASE64;
				}
				pubKey.put((String)entry.getKey(), RSASignatureTools.generateRSAPubKey(key[0], keyFileType));
			}
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				logger.error("解析秘钥配置文件错误，请确认classpath是否有文件property/keys.properties并且数据格式如key=file:type", e);
			}
		}
	
	}
	
	/**
	 * 添加新的公钥，如果已经存在则更新
	 * @param sysid  系统标识
	 * @param keyFile  秘钥文件
	 * @param keyType  秘钥存储类型
	 * @throws BusinessException
	 */
	public static void newKey(String sysid,String keyFile,KeyStoreFileType keyType) throws BusinessException{
		try {
			pubKey.put(sysid, RSASignatureTools.generateRSAPubKey(keyFile, keyType));
		} catch (Exception e) {
			throw new BusinessException("添加公钥失败："+sysid+"-"+keyFile+"-"+keyType,e);
		}
	}
	
}
