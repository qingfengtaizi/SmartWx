/*
 * FileName：CharsetUtil.java 
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

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * 功能：
 * @author xiongliang
 *
 * mobile enterprise application platform
 * Version 0.1
 */
public class CharsetUtil {

	/**
	 * 获得JDK默认编码
	 * @return
	 */
	public static String getDefaultCharset(){
		return Charset.defaultCharset().toString();
	}
	/**
	 * 获得远程URL文件的编码格式
	 * @param url
	 * @return
	 */
	public static String getReomoteURLFileEncode(URL url) {
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		java.nio.charset.Charset charset = null;
		try {
			charset = detector.detectCodepage(url);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (charset != null) {
			return charset.name();
		} else {
			return ConvertUtil.UTF_8;
		}
	}

	/**
	 * 获得文件流的编码格式
	 * @param is
	 * @return
	 */
	public static String getInputStreamEncode(InputStream is) {
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		java.nio.charset.Charset charset = null;
		try {
			charset = detector.detectCodepage(is, 0);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (charset != null) {
			return charset.name();
		} else {
			return ConvertUtil.UTF_8;
		}
	} 

	/**
	 * 获得本地文件的编码格式
	 * @param file
	 * @return
	 */
	public static String getLocalteFileEncode(File file) {
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		java.nio.charset.Charset charset = null;
		try {
			charset = detector.detectCodepage(file.toURI().toURL());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (charset != null) {
			return charset.name();
		} else {
			return ConvertUtil.UTF_8;
		}
	}
	
	/**
	 * 获得字符串的编码格式
	 * @param str
	 * @return
	 */
	public static String getStringEncode(String str) {
		return getEncode(str.getBytes());
	} 

	/**
	 * 获得字符串的编码格式
	 * @param _byte
	 * @return
	 */
	public static String getEncode(byte[] _byte) {
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		java.nio.charset.Charset charset = null;
		InputStream myIn=new ByteArrayInputStream(_byte);
		try {
			charset = detector.detectCodepage(myIn,8);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (charset != null) {
			return charset.name();
		} else {
			return ConvertUtil.UTF_8;
		}
	}
	
}
