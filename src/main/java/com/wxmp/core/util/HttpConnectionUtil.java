/*
 * FileName：HttpConnectionUtil.java 
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
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @Description	HTTP请求工具类
 * @ClassName	HttpConnectionUtil
 * @Date		2017年7月14日 下午2:09:55
 * @Author		hermit
 */
public class HttpConnectionUtil {

	private static final String HTTP = "http";
	private static final String HTTPS = "https";
	private static SSLConnectionSocketFactory sslsf = null;
	private static PoolingHttpClientConnectionManager cm = null;
	private static SSLContextBuilder builder = null;
	static {
		try {
			builder = new SSLContextBuilder();
			// 全部信任 不做身份鉴定
			builder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
					return true;
				}
			});
			sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
			Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
					.register(HTTP, new PlainConnectionSocketFactory())
					.register(HTTPS, sslsf)
					.build();
			cm = new PoolingHttpClientConnectionManager(registry);
			cm.setMaxTotal(200);//max connection
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @Title: post(接受key-value)
	 * @param @param url
	 * @param @param vMap
	 * @return 
	 * @throws
	 */
	public static String post(String url,Map<String, String> header, Map<String, String> vMap){

		String result = null;

		// post
		HttpPost post = new HttpPost(url);

		//设置post参数
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();//设置请求和传输超时时间

		post.setConfig(requestConfig);

		// 设置头信息
		if (null != header) {
			Iterator<Entry<String, String>> iterator = header.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				post.addHeader(entry.getKey(), entry.getValue());
			}
		}
		// post参数
		List<BasicNameValuePair> params = new ArrayList();
		if (vMap != null && vMap.entrySet().size() > 0) {
			Set<Entry<String, String>> vSet = vMap.entrySet();
			for (Entry<String, String> entry : vSet) {
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		// 设置post参数
		if (params.size() > 0){
			post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));
		}

		// client
		CloseableHttpClient client = getHttpClient();

		try {
			// 发送请求
			CloseableHttpResponse response = client.execute(post);
			// 返回结果
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * @Title: get
	 * @param @param url
	 * @param @param vMap
	 * @return 
	 */
    public static String get(String url, Map<String, String> header, Map<String, String> vMap) {
		String result = null;
		// get参数

		if (vMap != null && vMap.entrySet().size() > 0) {
			List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			Set<Entry<String, String>> vSet = vMap.entrySet();
			for (Entry<String, String> entry : vSet) {
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			String paramsStr = URLEncodedUtils.format(params, StandardCharsets.UTF_8);
            url += "?" + paramsStr;
		}

		// get
		HttpGet get = new HttpGet(url);
		// 设置头信息
		if (null != header) {
			Iterator<Entry<String, String>> iterator = header.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				get.addHeader(entry.getKey(), entry.getValue());
			}
		}
		//设置get参数
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();//设置请求和传输超时时间

		get.setConfig(requestConfig);
		// client
		CloseableHttpClient client = getHttpClient();

		try {
			// 发送请求
			CloseableHttpResponse response = client.execute(get);

			// 返回结果
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(),StandardCharsets.UTF_8);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * @Title: post(接受json字符串)
	 * @param @param url
	 * @param @param vMap
	 * @return
	 * @throws
	 */
	public static String post(String url,Map<String, String> header, String jsonStr){

		String result = null;

		// post
		HttpPost post = new HttpPost(url);

		//设置post参数
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();//设置请求和传输超时时间

		post.setConfig(requestConfig);

		// 设置头信息
		if (null != header) {
			Iterator<Entry<String, String>> iterator = header.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				post.addHeader(entry.getKey(), entry.getValue());
			}
		}
//		// post参数
		post.setEntity(new StringEntity(jsonStr,StandardCharsets.UTF_8));

		// client
		CloseableHttpClient client =  getHttpClient();

		try {
			// 发送请求
			CloseableHttpResponse response = client.execute(post);
			// 返回结果
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED || response.getStatusLine().getStatusCode() == HttpStatus.SC_OK || response.getStatusLine().getStatusCode() == HttpStatus.SC_FORBIDDEN) {
                result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	/**
	 * 执行POST方法，并将报文返回。此方法不携带头信息
	 */
	public static String post(String url, Map<String, String> vMap) {
        return post(url, null, vMap);
	}

	/**
	 * 执行get方法，并将报文返回。此方法不携带头信息
	 *
	 * @param url
	 *            请求地址
	 * @return 返回报文
	 */
	public static String get(String url) {
		return get(url, null, null);
	}

	/**
	 * 执行get方法，并将报文返回。此方法不携带头信息
	 *
	 * @param url
	 *            请求地址
	 * @param vMap
	 *            请求内容
	 * @return 返回报文
	 */
	public static String get(String url, Map<String, String> vMap) {
		return get(url, null, vMap);
	}

	/**
	 *获取client端
	 * @return
	 */
	public static CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(sslsf)
				.setConnectionManager(cm)
				.setConnectionManagerShared(true)
				.build();
		return httpClient;
	}
}
