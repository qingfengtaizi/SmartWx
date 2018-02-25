package com.wxmp.backstage.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import com.wxmp.backstage.common.Configuration;

/**
 * 功能：转换编码
 * @author xiongliang
 *
 * mobile enterprise application platform
 * Version 0.1
 */
public class ConvertUtil {
	
	private static final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	/**	
	 * 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块	
	 * */
	public static final String US_ASCII = "US-ASCII";
	/**	
	 * ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1	
	 * */
	public static final String ISO_8859_1 = "ISO-8859-1";
	/**	
	 * 8 位 UCS 转换格式	
	 * */
	public static final String UTF_8 = "UTF-8";
	/**	
	 * 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序	
	 * */
	public static final String UTF_16BE = "UTF-16BE";
	/**	
	 * 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序	
	 * */
	public static final String UTF_16LE = "UTF-16LE";
	/**	
	 * 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识	
	 * */
	public static final String UTF_16 = "UTF-16";
	/**	
	 * 中文超大字符集	
	 * */
	public static final String GBK = "GBK";
	/** 
	 * 繁体中文 
	 * */
	public static final String BIG_5 = "BIG5";
	
	/**
	 * 字符串编码转换的实现方法
	 * @param strIn
	 * @param sourceCharset
	 * @param targetCharset
	 * @return
	 */
	public static String convertCharset(String strIn, String sourceCharset, String targetCharset){
		if(ValidateUtil.isNull(strIn))
			return strIn;
		String strOut = null;
		try{
			 byte[] c = strIn.getBytes(sourceCharset);
			 strOut = new String(c, targetCharset);
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return strOut;
	}
	
	/**
	 * 获得UTF8编码的字符串
	 * @param gbkStr
	 * @return
	 */
	public static String convertGbkToUft8(String gbkStr) {
		try {
			return new String(convertGbkToUtf8Bytes(gbkStr), UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new InternalError();
		}
	}
	/**
	 * GBK转UTF-8编码
	 * @param text
	 * @return
	 */
	/*public static String convertGbkToUft8(String text){
		String utf = null;
		try {
			String iso = new String(text.getBytes(UTF_8),ISO_8859_1);
			utf = new String(iso.getBytes(ISO_8859_1),UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return utf;
	}*/
	
	/**
	 * 获得UTF8编码的字节数组
	 * @param gbkStr
	 * @return
	 */
	public static byte[] convertGbkToUtf8Bytes(String gbkStr) {
		int n = gbkStr.length();
		byte[] utfBytes = new byte[3 * n];
		int k = 0;
		for (int i = 0; i < n; i++) {
			int m = gbkStr.charAt(i);
			if (m < 128 && m >= 0) {
				utfBytes[k++] = (byte) m;
				continue;
			}
			utfBytes[k++] = (byte) (0xe0 | (m >> 12));
			utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
			utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
		}
		if (k < utfBytes.length) {
			byte[] tmp = new byte[k];
			System.arraycopy(utfBytes, 0, tmp, 0, k);
			return tmp;
		}
		return utfBytes;
	}
	
	/**
	 * 输入流转字符串
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is){
		BufferedReader reader =  new  BufferedReader( new  InputStreamReader(is));    
		StringBuilder sb =  new  StringBuilder();    
		String line =  null ;    
		try{
			while  ((line = reader.readLine()) !=  null ) {    
				sb.append(line +  Configuration.LINE_SEPARATOR );    
			}    
		}  catch  (IOException e) {    
			e.printStackTrace();    
		}  finally  {    
			try {  
				is.close();    
			}catch(IOException e) {
				e.printStackTrace();    
			}
		}
		return  sb.toString();    
	}
	
	/**
	 * 半角转换成全角
	 * @param input
	 * @return
	 */
	public static String convertSBC(String input){
		// 半角转全角
		char[] c = input.toCharArray();    
		for (int i = 0; i < c.length; i++) {    
			if (c[i] == 32) {    
				c[i] = (char) 12288;    
				continue;    
			}    
			if (c[i] < 127)    
				c[i] = (char) (c[i] + 65248);    
		}    
		return new String(c);
	}
	
	/**
	 * 全角转换成半角
	 * @param input
	 * @return
	 */
	public static String convertDBC(String input){
		// 全角转 半角
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {    
			if (c[i] == 12288) {    
				c[i] = (char) 32;
				continue;    
			}    
			if (c[i] > 65280 && c[i] < 65375)    
				c[i] = (char) (c[i] - 65248);    
		}    
		return new String(c);
	}
	
	/**
	 * Hex编码.
	 */
	public static String convertEncodeHex(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码.
	 */
	public static byte[] convertDecodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Base64编码.
	 */
	public static String convertEncodeBase64(byte[] input) {
		return Base64.encodeBase64String(input);
	}

	/**
	 * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
	 */
	public static String convertEncodeUrlSafeBase64(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}
	
	/**
	 * Base64解码.
	 */
	public static byte[] convertDecodeBase64(String input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * Base62编码。
	 */
	public static String convertEncodeBase62(byte[] input) {
		char[] chars = new char[input.length];
		for (int i = 0; i < input.length; i++) {
			chars[i] = BASE62[((input[i] & 0xFF) % BASE62.length)];
		}
		return new String(chars);
	}
	
}
