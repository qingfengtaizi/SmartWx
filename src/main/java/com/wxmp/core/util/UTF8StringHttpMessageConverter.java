/*
 * FileName：UTF8StringHttpMessageConverter.java 
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.util.StreamUtils;

/**
 * 用于springmvc @ResponseBody 转码
 */
public class UTF8StringHttpMessageConverter extends AbstractHttpMessageConverter<String> {
	
	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	private final Charset defaultCharset;
	private final List<Charset> availableCharsets;
	private boolean writeAcceptCharset;

	public UTF8StringHttpMessageConverter() {
		this(DEFAULT_CHARSET);
	}

	public UTF8StringHttpMessageConverter(Charset defaultCharset) {
		super(new MediaType[] { new MediaType("text", "plain", defaultCharset),MediaType.ALL });
		this.writeAcceptCharset = true;
		this.defaultCharset = defaultCharset;
		this.availableCharsets = new ArrayList(Charset.availableCharsets().values());
	}
	
	public void setWriteAcceptCharset(boolean writeAcceptCharset) {
		this.writeAcceptCharset = writeAcceptCharset;
	}

	public boolean supports(Class<?> clazz) {
		return String.class.equals(clazz);
	}

	protected String readInternal(Class<? extends String> clazz,HttpInputMessage inputMessage) throws IOException {
		Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
		return StreamUtils.copyToString(inputMessage.getBody(), charset);
	}

	protected Long getContentLength(String s, MediaType contentType) {
		Charset charset = getContentTypeCharset(contentType);
		try {
			return Long.valueOf(s.getBytes(charset.name()).length);
		} catch (UnsupportedEncodingException ex) {
			throw new IllegalStateException(ex);
		}
	}

	protected void writeInternal(String s, HttpOutputMessage outputMessage)throws IOException {
		if (this.writeAcceptCharset) {
			outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
		}
		Charset charset = getContentTypeCharset(outputMessage.getHeaders().getContentType());
		StreamUtils.copy(s, charset, outputMessage.getBody());
	}

	protected List<Charset> getAcceptedCharsets() {
		return this.availableCharsets;
	}

	private Charset getContentTypeCharset(MediaType contentType) {
		if ((contentType != null) && (contentType.getCharSet() != null)) {
			return contentType.getCharSet();
		}
		return this.defaultCharset;
	}

}
