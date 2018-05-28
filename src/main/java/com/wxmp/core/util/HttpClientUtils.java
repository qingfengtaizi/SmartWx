/*
 * FileName：HttpClientUtils.java 
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * HttpClient 搬砖工具类
 * 
 *
 */
public class HttpClientUtils {

	private static Logger logger = LogManager.getLogger(HttpClientUtils.class);
    
	private HttpClientUtils() {
        throw new IllegalAccessError("工具类不能实例化");
    }

    // private static BasicHttpClientConnectionManager connectionManager = null;
    private static PoolingHttpClientConnectionManager connectionManager = null;

    // private static HttpClientBuilder httpClientBuilder=null;

    private static RequestConfig requestConfig = RequestConfig.custom()
    		.setSocketTimeout(5000)
    		.setConnectTimeout(5000)
            .setConnectionRequestTimeout(3000)
            .build();

    static {

        
          SSLContext sslcontext = SSLContexts.createSystemDefault();
          
          // Create a registry of custom connection socket factories for supported //
          //protocol schemes.
          
          
          Registry<ConnectionSocketFactory> socketFactoryRegistry =
          RegistryBuilder.<ConnectionSocketFactory>create() 
          .register("http", PlainConnectionSocketFactory.INSTANCE) 
          .register("https", new SSLConnectionSocketFactory(sslcontext)) .build();
         

        // 使用基本的Httpclient链接器
        // connectionManager=new
        // BasicHttpClientConnectionManager(socketFactoryRegistry);

         connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
//        connectionManager = new PoolingHttpClientConnectionManager();
         connectionManager.setMaxTotal(1000);
         connectionManager.setDefaultMaxPerRoute(200);// 每个路由最大的请求数量

        // httpClientBuilder =
        // HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig);
        // HttpHost localhost = new HttpHost("http://www.baidu.com",80);
        // connectionManager.setMaxPerRoute(new HttpRoute(localhost), 200);

    }

    public static CloseableHttpClient getHttpClient() {
        return getHttpClientBuilder().build();
    }

    public static CloseableHttpClient getHttpClient(SSLContext sslContext) {
        return getHttpClientBuilder(sslContext).build();
    }

    public static HttpClientBuilder getHttpClientBuilder() {
        return HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig);
        // .setConnectionManagerShared(true);
    }

    public static HttpClientBuilder getHttpClientBuilder(SSLContext sslContext) {
        if (sslContext != null) {
            return getHttpClientBuilder().setSSLContext(sslContext);
        } else {
            return getHttpClientBuilder();
        }

    }

    /**
     * post 请求
     * 
     * @param httpUrl
     *            请求地址
     * @param sslContext
     *            ssl证书信息
     * @return
     */
    public static String sendHttpPost(String httpUrl, SSLContext sslContext) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        return sendHttpPost(httpPost, sslContext);
    }

    /**
     * 发送 post请求
     * 
     * @param httpUrl
     *            地址
     */
    public static String sendHttpPost(String httpUrl) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        return sendHttpPost(httpPost, null);
    }

    /**
     * 发送 post请求
     * 
     * @param httpUrl
     *            地址
     * @param params
     *            参数(格式:key1=value1&key2=value2)
     */
    public static String sendHttpPost(String httpUrl, String params) {
        return sendHttpPost(httpUrl, params, null);
    }

    /**
     * 发送 post请求
     * 
     * @param httpUrl
     *            地址
     * @param params
     *            参数(格式:key1=value1&key2=value2)
     * @param sslContext
     *            ssl证书信息
     */
    public static String sendHttpPost(String httpUrl, String params, SSLContext sslContext) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return sendHttpPost(httpPost, sslContext);
    }

    /**
     * 发送 post请求
     * 
     * @param httpUrl
     *            地址
     * @param maps
     *            参数
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps) {
        return sendHttpPost(httpUrl, maps, null);
    }

    /**
     * 发送 post请求
     * 
     * @param httpUrl
     *            地址
     * @param maps
     *            参数
     * @param sslContext
     *            ssl证书信息
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps, SSLContext sslContext) {
        HttpPost httpPost = wrapHttpPost(httpUrl, maps);
        return sendHttpPost(httpPost, null);
    }

    /**
     * 封装获取HttpPost方法
     * 
     * @param httpUrl
     * @param maps
     * @return
     */
    public static HttpPost wrapHttpPost(String httpUrl, Map<String, String> maps) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Map.Entry<String, String> m : maps.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(m.getKey(), m.getValue()));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return httpPost;
    }

    /**
     * 发送 post请求（带文件）
     *
     * @param httpUrl
     *            地址
     * @param file
     *            附件,名称和File对应
     * @param maps
     *            参数
     */
    public static String sendHttpPost(String httpUrl, File file, Map<String, String> maps) {
        return sendHttpPost(httpUrl,  ImmutableMap.of( "files", file), maps, null);
    }

    /**
     * 发送 post请求（带文件）,默认 files 名称数组.
     * 
     * @param httpUrl
     *            地址
     * @param fileLists
     *            附件
     * @param maps
     *            参数
     */
    public static String sendHttpPost(String httpUrl, List<File> fileLists, Map<String, String> maps) {
        return sendHttpPost(httpUrl, fileLists, maps, null);
    }

    /**
     * 发送 post请求（带文件）
     * 
     * @param httpUrl
     *            地址
     * @param fileMap
     *            附件,名称和File对应
     * @param maps
     *            参数
     */
    public static String sendHttpPost(String httpUrl, Map<String, File> fileMap, Map<String, String> maps) {
        return sendHttpPost(httpUrl, fileMap, maps, null);
    }

    /**
     * 发送 post请求（带文件）,默认 files 名称数组.
     * 
     * @param httpUrl
     *            地址
     * @param fileLists
     *            附件
     * @param maps
     *            参数
     * @param sslContext
     *            ssl证书信息
     */
    public static String sendHttpPost(String httpUrl, List<File> fileLists, Map<String, String> maps,
            SSLContext sslContext) {

        Map<String, File> fileMap = new HashMap<>();

        if (fileLists==null||fileLists.isEmpty()) {
            for (File file : fileLists) {
                fileMap.put("files", file);
            }
        }

        return sendHttpPost(httpUrl, fileMap, maps, sslContext);
    }

    /**
     * 发送 post请求（带文件）
     * 
     * @param httpUrl
     *            地址
     * @param fileMap
     *            附件,名称和File对应
     * @param maps
     *            参数
     * @param sslContext
     *            ssl证书信息
     */
    public static String sendHttpPost(String httpUrl, Map<String, File> fileMap, Map<String, String> maps,
            SSLContext sslContext) {
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        if (null != maps) {
            for (Map.Entry<String, String> m : maps.entrySet()) {
                meBuilder.addPart(m.getKey(), new StringBody(m.getValue(), ContentType.TEXT_PLAIN));
            }
        }
        if (null != fileMap) {
            for (Map.Entry<String, File> m : fileMap.entrySet()) {
                FileBody fileBody = new FileBody(m.getValue());
                meBuilder.addPart(m.getKey(), fileBody);
            }
        }

        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        return sendHttpPost(httpPost, sslContext);
    }

    /**
     * 发送Post请求
     * 
     * @param httpPost
     * @return
     */
    public static String sendHttpPost(HttpPost httpPost) {
        return sendHttpPost(httpPost, null);
    }

    /**
     * 发送Post请求
     * 
     * @param httpPost
     * @param sslConext
     *            ssl证书信息
     * @return
     */
    public static String sendHttpPost(HttpPost httpPost, SSLContext sslConext) {
        CloseableHttpClient httpClient = getHttpClient(sslConext);
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {

            try {
                // 关闭连接,释放资源
                if (entity != null) {
                    EntityUtils.consumeQuietly(entity); // 会自动释放连接
                }
                if (response != null) {
                    response.close();
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        }
        return responseContent;
    }

    /**
     * 发送 get请求
     * 
     * @param httpUrl
     */
    public static String sendHttpGet(String httpUrl) {
        return sendHttpGet(httpUrl, null);
    }

    /**
     * 发送 get请求
     * 
     * @param httpUrl
     * @param sslConext
     *            ssl证书信息
     */
    public static String sendHttpGet(String httpUrl, SSLContext sslConext) {
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        return sendHttpGet(httpGet, sslConext);
    }

    /**
     * 发送Get请求
     * 
     * @param httpGet
     * @return
     */
    public static String sendHttpGet(HttpGet httpGet) {
        return sendHttpGet(httpGet, null);
    }

    /**
     * 发送Get请求
     * 
     * @param httpGet
     * @param sslConext
     *            ssl证书信息
     * @return
     */
    public static String sendHttpGet(HttpGet httpGet, SSLContext sslConext) {
        CloseableHttpClient httpClient = getHttpClient(sslConext);
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {

            try {
                // 关闭连接,释放资源
                if (entity != null) {
                    EntityUtils.consumeQuietly(entity); // 会自动释放连接
                }
                if (response != null) {
                    response.close();
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        }
        return responseContent;
    }

    /**
     * 发送 get请求
     * 
     * @param httpUrl
     *            请求路径
     * @param headers
     *            请求头参数
     * @return
     */
    public static String sendHttpHeaderGet(String httpUrl, Map<String, String> headers) {
        HttpGet httpGet = new HttpGet(httpUrl);// 创建get请求
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            httpGet.setHeader(key, value);
        }
        return sendHttpGet(httpGet, null);
    }

    /**
     * Get 下载文件
     * 
     * @param httpUrl
     * @param file
     * @return
     */
    public static File sendHttpGetFile(String httpUrl, File file) {

        if (file == null) {
            return null;
        }

        HttpGet httpGet = new HttpGet(httpUrl);

        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            // 执行请求
            response = httpClient.execute(httpGet);

            entity = response.getEntity();
            inputStream = entity.getContent();
            fileOutputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                fileOutputStream.write(buf, 0, len);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {

            try {

                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }

                if (inputStream != null) {
                    inputStream.close();
                }

                // 关闭连接,释放资源
                if (entity != null) {
                    EntityUtils.consumeQuietly(entity); // 会自动释放连接
                }
                if (response != null) {
                    response.close();
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        }
        return file;
    }
    
    
   /**
    * Post 下载文件
    * @param httpUrl
    * @param maps
    * @param file
    * @return
    */
    public static File sendHttpPostFile(String httpUrl,Map<String, String> maps, File file) {

        if (file == null) {
            return null;
        }

        HttpPost httpPost=wrapHttpPost(httpUrl, maps);

        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            // 执行请求
            response = httpClient.execute(httpPost);

            entity = response.getEntity();
            inputStream = entity.getContent();
            fileOutputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                fileOutputStream.write(buf, 0, len);
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {

            try {

                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }

                if (inputStream != null) {
                    inputStream.close();
                }

                // 关闭连接,释放资源
                if (entity != null) {
                    EntityUtils.consumeQuietly(entity); // 会自动释放连接
                }
                if (response != null) {
                    response.close();
                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }

        }
        return file;
    }
    
   //-------------------------URLHttps------------------------------------------ 
    private final static String storDir=System.getProperty("java.io.tmpdir") ;
    
    private static SSLSocketFactory getFactory() throws NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException{
        TrustManager[] tm = { new WeiXinX509TrustManager() };
//        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tm, new SecureRandom());
        return sslContext.getSocketFactory();
	}
//    /**
//     * 普通请求
//     * @param path
//     * @param method
//     * @param body
//     * @return
//     */
//    public static String httpsRequestToString(String path, String method, String body) {
//        if(StringUtils.isBlank(path)||StringUtils.isBlank(method)){
//            return null;
//        }
//        String response = null;
//        InputStream inputStream = null;
//        InputStreamReader inputStreamReader = null;
//        BufferedReader bufferedReader = null;
//        HttpsURLConnection conn = null;
//        OutputStream outputStream=null;
//        try {
//            URL url = new URL(path);
//            conn = (HttpsURLConnection) url.openConnection();
//            //主机信任
//            conn.setHostnameVerifier(new ReHostnameVerifier());
//            conn.setSSLSocketFactory(getFactory());
//
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.setUseCaches(false);
//            conn.setRequestMethod(method);
//            // 设置请求头信息
// 			conn.setRequestProperty("Connection", "Keep-Alive");
// 			conn.setRequestProperty("Charset", "UTF-8");
// 			// 设置边界
// 		    String BOUNDARY = "----------" + System.currentTimeMillis();
// 		    conn.setRequestProperty("Content-Type", "application/json; boundary="+ BOUNDARY);
// 		    // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
// 		    conn.connect();
// 		    if (null != body) {
//                outputStream = conn.getOutputStream();
//                // 注意编码格式
//                outputStream.write(body.getBytes("UTF-8"));
//                outputStream.flush();
//            }
//
//            if(conn.getResponseCode() == conn.HTTP_OK){
//
//            	inputStream = conn.getInputStream();
//            	inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
//            	bufferedReader = new BufferedReader(inputStreamReader);
//            	String str = null;
//            	StringBuffer buffer = new StringBuffer();
//            	while ((str = bufferedReader.readLine()) != null) {
//            		buffer.append(str);
//            	}
//            	response = buffer.toString();
//            }
//        } catch (IOException e) {
//        	logger.error(e.getMessage(), e);
//     	   //这里可以抛出异常，比说自定义的微信异常
//        } catch (GeneralSecurityException e) {
//        	logger.error(e.getMessage(), e);
//     	   //这里可以抛出异常，比说自定义的微信异常
//        }finally{
//            if(conn!=null){
//                conn.disconnect();
//            }
//            try {
//         	   if(outputStream!=null){
//         		   outputStream.close();
//         	   }
// 	           	if(bufferedReader!=null){
// 	           		bufferedReader.close();
// 	           	}
// 	           	if(inputStreamReader!=null){
// 	           		inputStreamReader.close();
// 	           	}
// 	           	if(inputStream!=null){
// 	           		inputStream.close();
// 	           	}
//
//            } catch (IOException e) {
//            	logger.error(e.getMessage(), e);
//            }
//        }
//        return response;
//    }
//    /**
//     * 文件上传（速度快）
//     * @param path
//     * @param method
//     * @param file
//     * @return
//     */
//    public static String httpsRequestToUpload(String path, String method,File file){
//       	if(StringUtils.isBlank(path)||StringUtils.isBlank(method)||!file.exists()){
//               return null;
//           }
//           String response = null;
//           InputStream inputStream = null;
//           InputStreamReader inputStreamReader = null;
//           BufferedReader bufferedReader = null;
//           HttpsURLConnection conn = null;
//           OutputStream out=null;
//           DataInputStream in =null;
//           try {
//
//               URL url = new URL(path);
//               conn = (HttpsURLConnection) url.openConnection();
//               //主机信任
//               conn.setHostnameVerifier(new ReHostnameVerifier());
//               conn.setSSLSocketFactory(getFactory());
//
//               conn.setDoOutput(true);
//               conn.setDoInput(true);
//               conn.setUseCaches(false);
//               conn.setRequestMethod(method);
//
//               //设置连接主机超时（单位：毫秒）
//               conn.setConnectTimeout(30000);
//               //设置从主机读取数据超时（单位：毫秒）
//               conn.setReadTimeout(30000);
//
//               // 设置请求头信息
//    			conn.setRequestProperty("Connection", "Keep-Alive");
//    			conn.setRequestProperty("Cache-Control", "no-cache");
//    			conn.setRequestProperty("Charset", "UTF-8");
//    			// 设置边界
//    		    String BOUNDARY = "----------" + System.currentTimeMillis();
//    		    conn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);
//    		    // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
//    		    conn.connect();
//    		    // 请求正文信息
//    	   	    // 第一部分：
//    	   	    StringBuilder sb = new StringBuilder();
//    	   	    sb.append("--"); // 必须多两道线
//    	   	    sb.append(BOUNDARY);
//    	   	    sb.append("\r\n");
//    	   	    sb.append("Content-Disposition: form-data;name=\"media\";filename=\""+ file.getName() + "\"\r\n");
//    	   	    sb.append("Content-Type:application/octet-stream\r\n\r\n");
//    		     byte[] head = sb.toString().getBytes("UTF-8");
//    		     // 获得输出流
//    		     out = new DataOutputStream(conn.getOutputStream());
//    		     // 输出表头
//    		     out.write(head);
//
//    		     // 文件写入流开始=》
//    		     // 把文件已流文件的方式 推入到url中
//    		     in = new DataInputStream(new FileInputStream(file));
//    		     int bytes = 0;
//    		     byte[] bufferOut = new byte[1024];
//    		     while ((bytes = in.read(bufferOut)) != -1) {
//    		    	 out.write(bufferOut, 0, bytes);
//    		     }
//    		     // 结尾部分
//    		     byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
//    		     out.write(foot);
//    		     out.flush();
//    		     // 文件写入流结束《=
//
//    		     if(conn.getResponseCode() == conn.HTTP_OK){
//    		    	 inputStream = conn.getInputStream();
//    		    	 inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
//    		    	 bufferedReader = new BufferedReader(inputStreamReader);
//    		    	 String str = null;
//    		    	 StringBuffer buffer = new StringBuffer();
//    		    	 while ((str = bufferedReader.readLine()) != null) {
//    		    		 buffer.append(str);
//    		    	 }
//    		    	 response = buffer.toString();
//    		     }
//           } catch (IOException e) {
//        	   logger.error(e.getMessage(), e);
//        	   //这里可以抛出异常，比说自定义的微信异常
//           } catch (GeneralSecurityException e) {
//        	   logger.error(e.getMessage(), e);
//        	   //这里可以抛出异常，比说自定义的微信异常
//           }finally{
//               if(conn!=null){
//                   conn.disconnect();
//               }
//               try {
//            	   if(out!=null){
//            		   out.close();
//            	   }
//            	   if(in!=null){
//            		   in.close();
//            	   }
//    	           	if(bufferedReader!=null){
//    	           		bufferedReader.close();
//    	           	}
//    	           	if(inputStreamReader!=null){
//    	           		inputStreamReader.close();
//    	           	}
//    	           	if(inputStream!=null){
//    	           		inputStream.close();
//    	           	}
//               } catch (IOException e) {
//               }
//           }
//           return response;
//       }
//    /**
//     *  文件上传（速度快）
//     * @param path
//     * @param method
//     * @param file
//     * @param params
//     * @return
//     */
//    public static String httpsRequestToUpload(String path, String method,File file,Map<String,String> params){
//
// 	   if(StringUtils.isBlank(path)||StringUtils.isBlank(method)||!file.exists()){
// 	           return null;
// 	       }
//        String response = null;
//        InputStream inputStream = null;
//        InputStreamReader inputStreamReader = null;
//        BufferedReader bufferedReader = null;
//        HttpsURLConnection conn = null;
//        OutputStream out =null;
//        DataInputStream in =null;
//        try {
//
//            URL url = new URL(path);
//            conn = (HttpsURLConnection) url.openConnection();
//            //主机信任
//            conn.setHostnameVerifier(new ReHostnameVerifier());
//            conn.setSSLSocketFactory(getFactory());
//
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.setUseCaches(false);
//            conn.setRequestMethod(method);
//
//            //设置连接主机超时（单位：毫秒）
//            conn.setConnectTimeout(30000);
//            //设置从主机读取数据超时（单位：毫秒）
//            conn.setReadTimeout(30000);
//
//            // 设置请求头信息
// 			conn.setRequestProperty("Connection", "Keep-Alive");
// 			conn.setRequestProperty("Cache-Control", "no-cache");
// 			conn.setRequestProperty("Charset", "UTF-8");
// 			// 设置边界
// 		    String BOUNDARY = "----------" + System.currentTimeMillis();
// 		    conn.setRequestProperty("Content-Type", "multipart/form-data; charset=UTF-8 boundary="+ BOUNDARY);
// 		    // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
// 		    conn.connect();
// 		    // 请求正文信息
// 	   	    // 第一部分：
// 	   	    StringBuilder sb = new StringBuilder();
// 	   	    sb.append("--"); // 必须多两道线
// 	   	    sb.append(BOUNDARY);
// 	   	    sb.append("\r\n");
// 	   	    sb.append("Content-Disposition: form-data;name=\"media\";filename=\""+ file.getName() + "\"\r\n");
// 	   	    sb.append("Content-Type:application/octet-stream\r\n\r\n");
// 		     byte[] head = sb.toString().getBytes("UTF-8");
// 		     // 获得输出流
// 		     out = new DataOutputStream(conn.getOutputStream());
// 		     // 输出表头
// 		     out.write(head);
// 		     // 文件写入流开始=》
// 		     // 把文件已流文件的方式 推入到url中
// 		     in = new DataInputStream(new FileInputStream(file));
// 		     int bytes = 0;
// 		     byte[] bufferOut = new byte[1024];
// 		     while ((bytes = in.read(bufferOut)) != -1) {
// 		    	 out.write(bufferOut, 0, bytes);
// 		     }
// 		     // 文件写入流结束《=
// 		     if(params!=null){
// 		    	//添加param参数
// 	 		     for (Map.Entry<String, String> entry : params.entrySet()) {
// 	 	    	   String key = entry.getKey().toString();
// 	 	    	   String value = entry.getValue().toString();
// 	 	    	   sb = new StringBuilder();
// 	 	    	   sb.append("--"); // 必须多两道线
// 	 	    	   sb.append(BOUNDARY);
// 	 	    	   sb.append("\r\n");
// 	 	    	   sb.append("Content-Disposition: form-data;name=\""+key+"\";\r\n\r\n");
// 	 	    	   sb.append(value);
// 	 	    	   byte[] paramb = sb.toString().getBytes("UTF-8");
// 	 	    	   out.write(paramb);
// 	 		     }
// 		     }
//
// 		     // 结尾部分
// 		     byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n\r\n").getBytes();// 定义最后数据分隔线
// 		     out.write(foot);
// 		     out.flush();
// 		     if(conn.getResponseCode() == conn.HTTP_OK){
// 		    	 inputStream = conn.getInputStream();
// 		    	 inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
// 		    	 bufferedReader = new BufferedReader(inputStreamReader);
// 		    	 String str = null;
// 		    	 StringBuffer buffer = new StringBuffer();
// 		    	 while ((str = bufferedReader.readLine()) != null) {
// 		    		 buffer.append(str);
// 		    	 }
// 		    	 response = buffer.toString();
// 		     }
//        } catch (IOException e) {
//        	logger.error(e.getMessage(), e);
//     	   //这里可以抛出异常，比说自定义的微信异常
//        } catch (GeneralSecurityException e) {
//        	logger.error(e.getMessage(), e);
//     	   //这里可以抛出异常，比说自定义的微信异常
//        }finally{
//
//            try {
//         	   if(out!=null){
//         		   out.close();
//         	   }
//         	   if(in!=null){
//         		   in.close();
//         	   }
// 	           	if(bufferedReader!=null){
// 	           		bufferedReader.close();
// 	           	}
// 	           	if(inputStreamReader!=null){
// 	           		inputStreamReader.close();
// 	           	}
// 	           	if(inputStream!=null){
// 	           		inputStream.close();
// 	           	}
//            } catch (IOException e) {
//            }
//            if(conn!=null){
//                conn.disconnect();
//            }
//        }
//        return response;
//    }
//    /**
//     * 文件下载 -使用object返回是为了判断返回数据类型
//     * @param path
//     * @param method
//     * @param param
//     * @param storageDir
//     * @return
//     */
//    public static Object httpsRequestToDownload(String path, String method,String param,String storageDir){
//	   	if(StringUtils.isBlank(path)||StringUtils.isBlank(method)){
//	           return null;
//	       }
//   		File file = null;
//   		OutputStream outputStream=null;
//       BufferedInputStream inputStream = null;
//       InputStream ips = null;
//       InputStreamReader inputStreamReader = null;
//       BufferedReader bufferedReader = null;
//       HttpsURLConnection conn = null;
//       OutputStream out =null;
//       try {
//
//           URL url = new URL(path);
//           conn = (HttpsURLConnection) url.openConnection();
//           //主机信任
//           conn.setHostnameVerifier(new ReHostnameVerifier());
//           conn.setSSLSocketFactory(getFactory());
//
//           conn.setDoOutput(true);
//           conn.setDoInput(true);
//           conn.setUseCaches(false);
//           conn.setRequestMethod(method);
//           //设置连接主机超时（单位：毫秒）
//           conn.setConnectTimeout(30000);
//           //设置从主机读取数据超时（单位：毫秒）
//           conn.setReadTimeout(30000);
//
//           // 设置请求头信息
//			conn.setRequestProperty("Connection", "Keep-Alive");
//			conn.setRequestProperty("Cache-Control", "no-cache");
//			conn.setRequestProperty("Charset", "UTF-8");
//			// 设置边界
//		    String BOUNDARY = "----------" + System.currentTimeMillis();
//		    conn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);
//		    // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
//		    conn.connect();
//		    if (StringUtils.isNotBlank(param)) {
//                outputStream = conn.getOutputStream();
//                outputStream.write(param.getBytes("UTF-8"));
//                outputStream.flush();
//            }
//
//		    logger.info("Content-Length: " + conn.getContentLength());
//		    logger.info("Headers: "+conn.getHeaderFields().toString());
//		    // 文件大小
//		    int fileLength = conn.getContentLength();
//
//		    //获取传输数据类型Content-Type
//		    String outype=conn.getContentType();
//
//		    if(StringUtils.isBlank(outype)||(outype.indexOf("text/plain")==-1&&outype.indexOf("application/json")==-1)){
//
//		    	String disposition=conn.getHeaderField("Content-disposition");
//
//		    	// 文件名
//		    	String fileName = forFileName(disposition);
//
//		    	//文件路径
//		    	String filePath = storageDir + File.separatorChar + fileName;
//
//		    	if (null == storageDir) {
//		    	Calendar now = Calendar.getInstance();
//		    	int year = now.get(Calendar.YEAR);
//		    	int month = now.get(Calendar.MONTH + 1);
//		    	int day = now.get(Calendar.DAY_OF_MONTH);
//		    	filePath = storDir + File.separatorChar
//		    			+ year + File.separatorChar
//		    			+ month + File.separatorChar
//		    			+ day + File.separatorChar
//		    			+ fileName;
//		    	}
//
//		    	file = new File(filePath);
//		    	if (!file.getParentFile().exists()) {
//		    		file.getParentFile().mkdirs();
//		    	}
//		    	if(conn.getResponseCode() == conn.HTTP_OK){
//		    		inputStream = new BufferedInputStream(conn.getInputStream());
//		    		out = new FileOutputStream(file);
//		    		int size = 0;
//		    		int len = 0;
//		    		byte[] buf = new byte[1024];
//		    		while ((size = inputStream.read(buf)) != -1) {
//		    			len += size;
//		    			out.write(buf, 0, size);
//		    			// 打印下载百分比
//		    			System.err.println("下载了-------> "
//							    			+ len * 100 / fileLength
//							    			+"%\n");
//		    		}
//		    		out.flush();
//		    	}
//		    }else{
//			     if(conn.getResponseCode() == conn.HTTP_OK){
//			    	 ips = conn.getInputStream();
//			    	 inputStreamReader = new InputStreamReader(ips, "UTF-8");
//			    	 bufferedReader = new BufferedReader(inputStreamReader);
//			    	 String str = null;
//			    	 StringBuffer buffer = new StringBuffer();
//			    	 while ((str = bufferedReader.readLine()) != null) {
//			    		 buffer.append(str);
//			    	 }
//			    	 //如果下载文件失败，就要把错误信息抛出去throw
//			    	 System.err.println("upload err => "+buffer.toString());
//			    	 return buffer.toString();
//			     }
//		    }
//       } catch (IOException e) {
//    	   logger.error(e.getMessage(), e);
//    	   //这里可以抛出异常，比说自定义的微信异常
//       } catch (GeneralSecurityException e) {
//    	   logger.error(e.getMessage(), e);
//    	   //这里可以抛出异常，比说自定义的微信异常
//       }finally{
//
//           try {
//        	   if(outputStream!=null){
//        		   outputStream.close();
//        	   }
//        	   if(out!=null){
//        		   out.close();
//        	   }
//	           	if(inputStream!=null){
//	           		inputStream.close();
//	           	}
//
//	           	if(bufferedReader!=null){
//	           		bufferedReader.close();
//	           	}
//	           	if(inputStreamReader!=null){
//	           		inputStreamReader.close();
//	           	}
//	           	if(ips!=null){
//	           		ips.close();
//	           	}
//           } catch (IOException e) {
//        	   logger.error(e.getMessage(), e);
//           }
//           if(conn!=null){
//               conn.disconnect();
//           }
//       }
//   	return file;
//   }
    /**
     * 获取文件名
     * @param disposition
     * @return
     */
    public static String forFileName(String disposition){
    	Pattern p = Pattern.compile(".*filename=\"(.*)\"");
	    Matcher m = p.matcher(disposition);
	    if(m.matches()){
	      return m.group(1);
	    }
	    return null;
    }
}
class WeiXinX509TrustManager implements X509TrustManager {

	// 检查客户端证书
	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// TODO Auto-generated method stub

	}
	// 检查服务器端证书
	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		// TODO Auto-generated method stub

	}
	// 返回受信任的X509证书数组
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}

}
 class ReHostnameVerifier implements HostnameVerifier {

	/**
	 * 
	* <p>Title: verify</p> 
	* <p>Description: 设置不验证主机</p> 
	* @param hostname 主机名
	* @param session 到主机的连接上使用的 SSLSession
	* @return 
	* @see javax.net.ssl.HostnameVerifier#verify(java.lang.String, javax.net.ssl.SSLSession)
	 */
	@Override
	public boolean verify(String hostname, SSLSession session) {
		// TODO Auto-generated method stub
		System.err.println("hostname=> "+hostname);
		System.err.println("session=> "+session.getProtocol());
		
		return true;
	}

}
