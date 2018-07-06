/*
 * FileName：GzipUtil.java 
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
