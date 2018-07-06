/*
 * FileName：RSASignatureTools.java 
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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.wxmp.core.exception.UnexpectedException;
import org.apache.commons.codec.binary.Base64;

/**
 * RSA签名工具类
 * @author xiaosf
 * @date 2017年6月8日
 */
public class RSASignatureTools {


	/**
	 * 生成RSA密码对，保存到basePath下
	 * @param basePath  保存秘钥的路径
	 * @throws Exception
	 */
	public static void generateRSAKeys(String basePath,KeyStoreFileType keyType){
		
		File f=new File(basePath);
		if(!f.exists()){
			boolean b=f.mkdirs();
			if(!b){
				throw new UnexpectedException("不能创建目录："+basePath);
			}
		}
		
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象  
        KeyPairGenerator keyPairGen = null;  
        try {  
            keyPairGen = KeyPairGenerator.getInstance("RSA");  
        } catch (NoSuchAlgorithmException e) {  
           throw new UnexpectedException("无此算法");
        }  
        // 初始化密钥对生成器，密钥大小为96-1024位  
        keyPairGen.initialize(1024,new SecureRandom());  
        // 生成一个密钥对，保存在keyPair中  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
        // 得到私钥  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();  
        // 得到公钥  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        //生成秘钥
    	byte[] pubks=publicKey.getEncoded();
    	byte[] priKeys=privateKey.getEncoded();
    	if(keyType==KeyStoreFileType.BINARY){//直接以二进制方式存储秘钥
    		FileOutputStream pubfos=null;
    		FileOutputStream prifos=null;
    		try{
    			pubfos=new FileOutputStream(basePath+"/public.key");
    			pubfos.write(pubks, 0, pubks.length);
    			pubfos.flush();
    			
            	prifos=new FileOutputStream(basePath+"/private.key");
            	prifos.write(priKeys, 0, priKeys.length); 
            	prifos.flush();
    		}catch(Exception e){
    			
    		}finally{
    			if(pubfos!=null){
    				try {
						pubfos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    			
    			if(prifos!=null){
    				try {
						prifos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    		}
    		
    	}else if(keyType==KeyStoreFileType.BASE64){
    		BufferedWriter pubbw=null;
    		BufferedWriter pribw=null;
    		try{
             pubbw = new BufferedWriter(new FileWriter(basePath + "/public.key"));  
             pribw = new BufferedWriter(new FileWriter(basePath + "/private.key"));  
             pubbw.write(Base64.encodeBase64String(pubks));  
             pribw.write(Base64.encodeBase64String(priKeys));  
             pubbw.flush();  
             pribw.flush();  
    		}catch(Exception e){
    			
    		}finally{
    			if(pubbw!=null){
    				try {
						pubbw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    			if(pribw!=null){
    				try {
						pribw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}
    		}
    	}else{
    		throw new UnexpectedException("文件类型不正确");
    	}
        	
	}
	
	/**
	 * 根据秘钥内容生成公钥
	 * @param publicKey 公钥字节
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey generateRSAPubKey(byte[] publicKey){
		try {  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new UnexpectedException("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new UnexpectedException("公钥非法");  
        } catch (NullPointerException e) {  
            throw new UnexpectedException("公钥数据为空");  
        }  
	}
	
	/**
	 * 根据秘钥文件生成公钥
	 * @param keyStoreFile 公钥字节
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey generateRSAPubKey(String keyStoreFile,KeyStoreFileType type){
		try {  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(loadKeyByFile(keyStoreFile,type));  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new UnexpectedException("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new UnexpectedException("公钥非法");  
        } catch (NullPointerException e) {  
            throw new UnexpectedException("公钥数据为空");  
        }  
	}
	
	/**
	 * 根据私钥内容生成私钥
	 * @param privateKey 私钥字节
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey generateRSAPriKey(byte[] privateKey){  
        try {  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new UnexpectedException("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new UnexpectedException("私钥非法");  
        } catch (NullPointerException e) {  
            throw new UnexpectedException("私钥数据为空");  
        }  
    }  
	/**
	 * 根据私钥文件生成私钥
	 * @param privateKey 私钥字节
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey generateRSAPriKey(String keyStoreFile,KeyStoreFileType type) {  
        try {  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(loadKeyByFile(keyStoreFile,type));  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new UnexpectedException("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new UnexpectedException("私钥非法");  
        } catch (NullPointerException e) {  
            throw new UnexpectedException("私钥数据为空");  
        }  
    }  
	
	/**
	 * 加载二进制文件的key
	 * @param file 文件路径
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public static byte[] loadKeyByFile(String file,KeyStoreFileType type)  {  
        try {  
            FileInputStream fis = new FileInputStream(file);  
            byte[] bs=new byte[fis.available()];
            fis.read(bs);
            fis.close();
            if(type==KeyStoreFileType.BASE64){
            	bs=Base64.decodeBase64(bs);
            }
            return bs;  
        } catch (IOException e) {  
            throw new UnexpectedException("密钥数据流读取错误");  
        } catch (NullPointerException e) {  
            throw new UnexpectedException("密钥输入流为空");  
        }  
    }  
	
	/**
	 * 根据私钥生成数字签名
	 * @param msgBytes  要签名的消息的字节数组
	 * @param priKey  加密的私钥
	 * @return
	 */
	public static byte[] signature(byte[] msgBytes,PrivateKey priKey){
		try{
			Signature sig = Signature.getInstance("MD5WithRSA");//数字签名算法
			sig.initSign(priKey);// sig对象得到私钥
			// 签名对象得到原始数据
			sig.update(msgBytes);// sig对象得到原始数据(现实中用的是原始数据的摘要，摘要的是单向的，即摘要算法后无法解密)
			return sig.sign();// sig对象用私钥对原始数据进行签名，签名后得到签名signature
		}catch(Exception e){
			throw new UnexpectedException(e);
		}
	}
	
	
	
	/**
	 * 验证签名
	 * @param msgBytes  签名的消息对应的字节数组
	 * @param signature 签名后的字节数组
	 * @param pubKey  公钥
	 * @return
	 * @throws Exception
	 */
	public static boolean verifySignature(byte[] msgBytes,byte[] signature,PublicKey pubKey){
		Signature sig =null;
		try{
			sig = Signature.getInstance("MD5WithRSA");//数字签名算法，
			// 使用公钥验证
			sig.initVerify(pubKey);// sig对象得到公钥
			// 签名对象得到原始信息
			sig.update(msgBytes);// sig对象得到原始数据(现实中是摘要)
		}catch(Exception e){
			throw  new UnexpectedException(e);
		}
		try {
			return sig.verify(signature);
		} catch (SignatureException e) {
			return false;
		}
	}
	
	/**
	 * 测试方法，待删除
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String pubFile="d:/keys/pubk";
		String priFile="d:/keys/prik";
		String testStr="fjsldfjlsdjl;safdsfds";
		
		RSAPublicKey pubKey=generateRSAPubKey(loadKeyByFile(pubFile,KeyStoreFileType.BINARY));
		RSAPrivateKey priKey=generateRSAPriKey(loadKeyByFile(priFile,KeyStoreFileType.BINARY));
		
		byte[] sig=signature(testStr.getBytes(),priKey);
		System.out.println(Base64.encodeBase64String(sig));
		
		boolean r=verifySignature(testStr.getBytes(),sig,pubKey);
		System.out.println(r);
		
//		String s="11111111";
//		System.out.println(Base64.encodeBase64String(s.getBytes()));
		
		
	}
	
	/**
	 * 秘钥文件类型
	 * @author xiaosf
	 * @date 2017年6月16日
	 */
	public static enum KeyStoreFileType{
		BINARY,BASE64
	}

}
