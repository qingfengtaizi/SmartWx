/*
 * FileName：MDCoder.java 
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
package com.wxmp.core.util.security.coder;

import com.wxmp.core.util.security.Hex;
import com.wxmp.core.util.security.SecurityCoder;

import java.security.MessageDigest;


/**
 * MD加密组件
 * 
 * @author ShenHuaJie
 * @version 1.0
 * @since 1.0
 */
public abstract class MDCoder extends SecurityCoder {

	/**
	 * MD2加密
	 * 
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeMD2(byte[] data) throws Exception {
		// 初始化MessageDigest
		MessageDigest md = MessageDigest.getInstance("MD2");
		// 执行消息摘要
		return md.digest(data);
	}

	/**
	 * MD4加密
	 * 
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeMD4(byte[] data) throws Exception {
		// 初始化MessageDigest
		MessageDigest md = MessageDigest.getInstance("MD4");
		// 执行消息摘要
		return md.digest(data);
	}

	/**
	 * MD5加密
	 * 
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeMD5(byte[] data) throws Exception {
		// 初始化MessageDigest
		MessageDigest md = MessageDigest.getInstance("MD5");
		// 执行消息摘要
		return md.digest(data);
	}

	/**
	 * Tiger加密
	 * 
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeTiger(byte[] data) throws Exception {
		// 初始化MessageDigest
		MessageDigest md = MessageDigest.getInstance("Tiger");
		// 执行消息摘要
		return md.digest(data);
	}

	/**
	 * TigerHex加密
	 * 
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static String encodeTigerHex(byte[] data) throws Exception {
		// 执行消息摘要
		byte[] b = encodeTiger(data);
		// 做十六进制编码处理
		return new String(Hex.encode(b));
	}

	/**
	 * Whirlpool加密
	 * 
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeWhirlpool(byte[] data) throws Exception {
		// 初始化MessageDigest
		MessageDigest md = MessageDigest.getInstance("Whirlpool");
		// 执行消息摘要
		return md.digest(data);
	}

	/**
	 * WhirlpoolHex加密
	 * 
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static String encodeWhirlpoolHex(byte[] data) throws Exception {
		// 执行消息摘要
		byte[] b = encodeWhirlpool(data);
		// 做十六进制编码处理
		return new String(Hex.encode(b));
	}

	/**
	 * GOST3411加密
	 * 
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static byte[] encodeGOST3411(byte[] data) throws Exception {
		// 初始化MessageDigest
		MessageDigest md = MessageDigest.getInstance("GOST3411");
		// 执行消息摘要
		return md.digest(data);
	}

	/**
	 * GOST3411Hex加密
	 * 
	 * @param data 待加密数据
	 * @return byte[] 消息摘要
	 * @throws Exception
	 */
	public static String encodeGOST3411Hex(byte[] data) throws Exception {
		// 执行消息摘要
		byte[] b = encodeGOST3411(data);
		// 做十六进制编码处理
		return new String(Hex.encode(b));
	}
}
