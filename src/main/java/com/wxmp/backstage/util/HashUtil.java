package com.wxmp.backstage.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.Validate;

import com.wxmp.backstage.common.Configuration;

/**
 * 功能：数据加密解密工具类
 * 支持HMAC-SHA1消息签名 及 AES/Blowfish对称加密的工具类
 * 支持Hex与Base64两种编码方式
 * 支持MD5 SHA 不可逆加密
 * @author xiongliang
 *
 * mobile enterprise application platform
 * Version 0.1
 */
public class HashUtil {
	
	public static final String SHA1 = "SHA-1";
	public static final String MD5 = "MD5";
	
	private static final String AES = "AES";
	private static final String AES_CBC = "AES/CBC/PKCS5Padding";
	private static final String HMACSHA1 = "HmacSHA1";
	private static final int DEFAULT_HMACSHA1_KEYSIZE = 160; //RFC2401
	private static final int DEFAULT_AES_KEYSIZE = 128;
	private static final int DEFAULT_IVSIZE = 16;

	private static SecureRandom random = new SecureRandom();
	/**
	 * SHA加密
	 * @param input
	 * @return
	 */
	public static String SHAHashing(String input){
		String output = "";
		MessageDigest md;		
        try {
            md = MessageDigest.getInstance("SHA");
            byte[] original = input.getBytes(Configuration.getEncoding());
            byte[] bytes = md.digest(original);
            for (int i = 0; i<bytes.length; i++) {
            	output += Integer.toHexString((bytes[i] & 0xff) + 0x100).substring(1);
            }            
        }catch(Exception e){
            e.printStackTrace();
        }
        return output;
	}
	/**
	 * MD5加密
	 * @param input
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String MD5Hashing(String input){
		String output = "";
		MessageDigest md;		
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] original = input.getBytes(Configuration.getEncoding());
            byte[] bytes = md.digest(original);
            for (int i = 0; i<bytes.length; i++) {
            	output += Integer.toHexString((bytes[i] & 0xff) + 0x100).substring(1);
            }            
        }catch(Exception e){
            e.printStackTrace();
        }
        return output;
	}
	/**
	 * 加密[Blowfish对称加密解密] 密钥是配置中的site.secret
	 * @param paramString
	 * @return
	 */
	public static String encrypt(String key,String paramString){
		if(ValidateUtil.isNull(paramString))return paramString;
		try {
			Cipher localCipher = Cipher.getInstance("Blowfish");
			localCipher.init(1, new SecretKeySpec(key.getBytes(), "Blowfish"));
			byte[] arrayOfByte = localCipher.doFinal(paramString.getBytes(Configuration.getEncoding()));
	        return ConvertUtil.convertEncodeBase64(arrayOfByte);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return paramString;
	}
	/**
	 * 解密[Blowfish对称加密解密] 密钥是配置中的site.secret
	 * @param paramString
	 * @return
	 */
	public static String decrypt(String key,String paramString){
		if(ValidateUtil.isNull(paramString))return paramString;
		try {
			Cipher localCipher = Cipher.getInstance("Blowfish");
			localCipher.init(2, new SecretKeySpec(key.getBytes(), "Blowfish"));
	        byte[] arrayOfByte = localCipher.doFinal(ConvertUtil.convertDecodeBase64(paramString));
	        return new String(arrayOfByte, Configuration.getEncoding());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return paramString;
	}

	//-- HMAC-SHA1 funciton --//
	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * 
	 * @param input 原始输入字符数组
	 * @param key HMAC-SHA1密钥
	 */
	public static byte[] hmacSha1(byte[] input, byte[] key) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, HMACSHA1);
			Mac mac = Mac.getInstance(HMACSHA1);
			mac.init(secretKey);
			return mac.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw ExcptUtil.unchecked(e);
		}
	}

	/**
	 * 校验HMAC-SHA1签名是否正确.
	 * 
	 * @param expected 已存在的签名
	 * @param input 原始输入字符串
	 * @param key 密钥
	 */
	public static boolean isMacValid(byte[] expected, byte[] input, byte[] key) {
		byte[] actual = hmacSha1(input, key);
		return Arrays.equals(expected, actual);
	}

	/**
	 * 生成HMAC-SHA1密钥,返回字节数组,长度为160位(20字节).
	 * HMAC-SHA1算法对密钥无特殊要求, RFC2401建议最少长度为160位(20字节).
	 */
	public static byte[] generateHmacSha1Key() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(HMACSHA1);
			keyGenerator.init(DEFAULT_HMACSHA1_KEYSIZE);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw ExcptUtil.unchecked(e);
		}
	}

	//-- AES funciton --//
	/**
	 * 使用AES加密原始字符串.
	 * 
	 * @param input 原始输入字符数组
	 * @param key 符合AES要求的密钥
	 */
	public static byte[] aesEncrypt(byte[] input, byte[] key) {
		return aes(input, key, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 使用AES加密原始字符串.
	 * 
	 * @param input 原始输入字符数组
	 * @param key 符合AES要求的密钥
	 * @param iv 初始向量 通过generateIV获得
	 */
	public static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv) {
		return aes(input, key, iv, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 使用AES解密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 * @param key 符合AES要求的密钥
	 */
	public static String aesDecrypt(byte[] input, byte[] key) {
		byte[] decryptResult = aes(input, key, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 使用AES解密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 * @param key 符合AES要求的密钥
	 * @param iv 初始向量 通过generateIV获得
	 */
	public static String aesDecrypt(byte[] input, byte[] key, byte[] iv) {
		byte[] decryptResult = aes(input, key, iv, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * @param input 原始字节数组
	 * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 
	 * @param key 符合AES要求的密钥
	 * @param mode Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] aes(byte[] input, byte[] key, int mode) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, AES);
			Cipher cipher = Cipher.getInstance(AES);
			cipher.init(mode, secretKey);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw ExcptUtil.unchecked(e);
		}
	}

	/**
	 * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 
	 * @param input 原始字节数组(需要加密的内容)
	 * @param key 符合AES要求的密钥
	 * @param iv 初始向量 通过generateIV获得
	 * @param mode Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, AES);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(AES_CBC);
			cipher.init(mode, secretKey, ivSpec);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw ExcptUtil.unchecked(e);
		}
	}

	/**
	 * 生成AES密钥,返回字节数组, 默认长度为128位(16字节).
	 */
	public static byte[] generateAesKey() {
		return generateAesKey(DEFAULT_AES_KEYSIZE);
	}

	/**
	 * 生成AES密钥,可选长度为128,192,256位.
	 */
	public static byte[] generateAesKey(int keysize) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
			keyGenerator.init(keysize);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw ExcptUtil.unchecked(e);
		}
	}

	/**
	 * 生成随机向量,默认大小为cipher.getBlockSize(), 16字节.
	 */
	public static byte[] generateIV() {
		byte[] bytes = new byte[DEFAULT_IVSIZE];
		random.nextBytes(bytes);
		return bytes;
	}
	
	/**
	 * 对输入字符串进行sha1散列.
	 */
	public static byte[] sha1(byte[] input) {
		return digest(input, SHA1, null, 1);
	}

	/**
	 * SHA加盐算法
	 * @param input
	 * @param salt
	 * @return
	 */
	public static byte[] sha1(byte[] input, byte[] salt) {
		return digest(input, SHA1, salt, 1);
	}

	/**
	 * SHA加盐多次循环算法
	 * @param input
	 * @param salt
	 * @param iterations
	 * @return
	 */
	public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
		return digest(input, SHA1, salt, iterations);
	}

	/**
	 * 对字符串进行散列, 支持md5与sha1算法.
	 * @param input
	 * @param algorithm
	 * @param salt
	 * @param iterations
	 * @return
	 */
	private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			if (salt != null) {
				digest.update(salt);
			}

			byte[] result = digest.digest(input);

			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return result;
		} catch (GeneralSecurityException e) {
			throw ExcptUtil.unchecked(e);
		}
	}

	/**
	 * 生成随机的Byte[]作为salt.
	 * 
	 * @param numBytes byte数组的大小
	 */
	public static byte[] generateSalt(int numBytes) {
		Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);

		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}

	/**
	 * 对文件进行消息摘要签名
	 * @param input InputStream输入流
	 * @param algorithm 加密算法 HashUtil.SHA1 HashUtil.MD5
	 * @return
	 * @throws IOException
	 */
	public static byte[] digest(InputStream input, String algorithm) throws IOException {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			int bufferLength = 8 * 1024;
			byte[] buffer = new byte[bufferLength];
			int read = input.read(buffer, 0, bufferLength);

			while (read > -1) {
				messageDigest.update(buffer, 0, read);
				read = input.read(buffer, 0, bufferLength);
			}

			return messageDigest.digest();
		} catch (GeneralSecurityException e) {
			throw ExcptUtil.unchecked(e);
		}
	}
}
