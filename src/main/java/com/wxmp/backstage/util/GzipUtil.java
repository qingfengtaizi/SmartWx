/**
 * 
 */
package com.wxmp.backstage.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.springframework.util.Assert;

/**
 * 功能：进行字符串Gzip压缩及解压缩
 * @author xiongliang
 *
 * mobile enterprise application platform
 * Version 0.1
 */
public class GzipUtil {
	
	/**
	 * 压缩字符串
	 * @param str
	 * @param charest
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] compress(String str,String charset) throws IOException, UnsupportedEncodingException {
		Assert.notNull(str, " null compress error ");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		try{			
			gzip.write(str.getBytes(charset));
			gzip.close();
			return  out.toByteArray();
		}catch(Exception e){
			throw new IOException(e);
		}finally{
			FileUtil.close(gzip);
			FileUtil.close(out);
		}
	}
	
	/**
	 * 解压缩字符串
	 * @param str
	 * @param charest
	 * @return
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	public static byte[] uncompress(byte[] _byte)throws IOException, UnsupportedEncodingException,FileNotFoundException {
		Assert.notNull(_byte, " null uncompress error ");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(_byte);
		GZIPInputStream gunzip = new GZIPInputStream(in);
		try{
			byte[] buffer = new byte[256];
			int n;
			while ((n = gunzip.read(buffer)) >= 0) {
				out.write(buffer, 0, n);
			}
			return out.toByteArray();
		}catch(Exception e){
			throw new IOException(e);
		}finally{
			FileUtil.close(out);
			FileUtil.close(gunzip);
			FileUtil.close(in);
		}
	}

}
