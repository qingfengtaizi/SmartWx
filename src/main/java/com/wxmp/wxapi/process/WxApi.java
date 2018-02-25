package com.wxmp.wxapi.process;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.wxmp.core.util.wx.SecurityUtil;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 微信 API、微信基本接口
 * 
 */

public class WxApi {

	//token 接口
	private static final String TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

	//创建菜单
	private static final String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

	//创建个性化菜单
	private static final String MENU_ADDCONDITIONAL = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=%s";

	//删除菜单
	private static final String MENU_DELETE = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s";

	//获取账号粉丝信息
	private static final String GET_FANS_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

	//获取账号粉丝列表
	private static final String GET_FANS_LIST = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s";

	//获取批量素材
	private static final String GET_BATCH_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=%s";

	//上传多媒体资料接口-临时
	private static final String UPLOAD_MEDIA = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=%s&type=%s";

	//上传永久素材：图文-临时
	private static final String UPLOAD_NEWS = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=%s";

	//群发接口
	private static final String MASS_SEND = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=%s";

	//网页授权OAuth2.0获取code
	private static final String GET_OAUTH_CODE = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=%s&scope=%s&state=%s#wechat_redirect";

	//网页授权OAuth2.0获取token
	private static final String GET_OAUTH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

	//网页授权OAuth2.0获取用户信息
	private static final String GET_OAUTH_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

	//生成二维码
	private static final String CREATE_QRCODE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";

	//根据ticket获取二维码图片
	private static final String SHOW_QRCODE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s";

	//js ticket
	private static final String GET_JSAPI_TICKET="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

	//发送客服消息
	private static final String SEND_CUSTOM_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";

	//模板消息接口
	private static final String SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

	//微信服务器ip
	private static final String CALLBACKIP = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=%s";

	//统一下单订购接口
	private static final String PAY_UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	//上传永久图片素材
	private static final String UPLOAD_MATERIAL_IMG = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=%s";

	//新增其他类型永久素材
	private static final String ADD_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=%s&type==%s";

	//新增永久图文素材
	private static final String ADD_NEWS_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=%s";

	//根据media_id来获取永久素材
	private static final String GET_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=%s";


	//根据media_id来删除永久图文素材
	private static final String DELETE_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=%s";

	//修改永久图文url
	private static final String UPDATE_NEWS_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=%s";

	//获取token接口
	public static String getTokenUrl(String appId,String appSecret){
		return String.format(TOKEN, appId, appSecret);
	}

	//获取上传Media接口
	public static String getUploadMediaUrl(String token,String type){
		return String.format(UPLOAD_MEDIA, token, type);
	}

	//获取菜单创建接口
	public static String getMenuCreateUrl(String token){
		return String.format(MENU_CREATE, token);
	}

	//获取个性化菜单创建接口
	public static String getMenuAddconditionalUrl(String token){
		return String.format(MENU_ADDCONDITIONAL, token);
	}

	//获取菜单删除接口
	public static String getMenuDeleteUrl(String token){
		return String.format(MENU_DELETE, token);
	}

	//获取粉丝信息接口
	public static String getFansInfoUrl(String token,String openid){
		return String.format(GET_FANS_INFO, token, openid);
	}

	//获取粉丝列表接口
	public static String getFansListUrl(String token,String nextOpenId){
		if(nextOpenId == null){
			return String.format(GET_FANS_LIST, token);
		}else{
			return String.format(GET_FANS_LIST + "&next_openid=%s", token, nextOpenId);
		}
	}

	//获取素材列表接口
	public static String getBatchMaterialUrl(String token){
		return String.format(GET_BATCH_MATERIAL, token);
	}

	//获取上传图文消息接口
	public static String getUploadNewsUrl(String token){
		return String.format(UPLOAD_NEWS, token);
	}

	//群发接口
	public static String getMassSendUrl(String token){
		return String.format(MASS_SEND, token);
	}

	//网页授权OAuth2.0获取code
	public static String getOAuthCodeUrl(String appId ,String redirectUrl ,String scope ,String state){
		return String.format(GET_OAUTH_CODE, appId, urlEnodeUTF8(redirectUrl), "code", scope, state);
	}

	//网页授权OAuth2.0获取token
	public static String getOAuthTokenUrl(String appId ,String appSecret ,String code ){
		return String.format(GET_OAUTH_TOKEN, appId, appSecret, code);
	}

	//网页授权OAuth2.0获取用户信息
	public static String getOAuthUserinfoUrl(String token ,String openid){
		return String.format(GET_OAUTH_USERINFO, token, openid);
	}

	//获取创建二维码接口url
	public static String getCreateQrcodeUrl(String token){
		return String.format(CREATE_QRCODE, token);
	}

	//获取显示二维码接口
	public static String getShowQrcodeUrl(String ticket){
		return String.format(SHOW_QRCODE, ticket);
	}

	//获取js ticket url
	public static String getJsApiTicketUrl(String token){
		return String.format(GET_JSAPI_TICKET, token);
	}

	//获取发送客服消息 url
	public static String getSendCustomMessageUrl(String token){
		return String.format(SEND_CUSTOM_MESSAGE, token);
	}

	//获取发送模板消息 url
	public static String getSendTemplateMessageUrl(String token){
		return String.format(SEND_TEMPLATE_MESSAGE, token);
	}

	//获取永久素材
	public static String getMaterial(String token){
		return String.format(GET_MATERIAL, token);
	}

	//删除永久图文素材
	public static String getDelMaterialURL(String token){
		return String.format(DELETE_MATERIAL, token);
	}

	//获取统一下单接口地址
	public static String getUnifiedOrderUrl(){
		return String.format(PAY_UNIFIEDORDER);
	}

	//获取微信服务器ip地址（验证码token是否过期）
	public static String getCallbackIpUrl(String token){
		return String.format(CALLBACKIP,token);
	}
	/**
	 * 获取创建临时二维码post data
	 * @param expireSecodes 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
	 * @param scene 临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000)
	 * @return
	 */
	public static String getQrcodeJson(Integer expireSecodes, Integer scene){
		String postStr = "{\"expire_seconds\":%d,\"action_name\":\"QR_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":%d}}";
		return String.format(postStr, expireSecodes, scene);
	}
	/**
	 * 获取创建临时二维码post data
	 * @param scene 临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000)
	 * @return
	 */
	public static String getQrcodeLimitJson(Integer scene){
		String postStr = "{\"action_name\":\"QR_LIMIT_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":%d}}";
		return String.format(postStr, scene);
	}
	//获取永久二维码
	public static String getQrcodeLimitJson(String sceneStr){
		String postStr = "{\"action_name\":\"QR_LIMIT_STR_SCENE\",\"action_info\":{\"scene\":{\"scene_str\":%s}}";
		return String.format(postStr, sceneStr);
	}

	//获取接口访问凭证
	public static AccessToken getAccessToken(String appId, String appSecret) {
		AccessToken token = null;
		String tockenUrl = WxApi.getTokenUrl(appId, appSecret);
		JSONObject jsonObject = httpsRequest(tockenUrl, HttpMethod.GET, null);
		if (null != jsonObject && !jsonObject.containsKey("errcode")) {
			try {
				token = new AccessToken();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				token = null;//获取token失败
			}
		}else if(null != jsonObject){
			token = new AccessToken();
			token.setErrcode(jsonObject.getInt("errcode"));
		}
		return token;
	}
	//获取微信服务器ip
	public static boolean getCallbackIp(String token) {
		String tockenUrl = WxApi.getCallbackIpUrl(token);
		JSONObject jsonObject = httpsRequest(tockenUrl, HttpMethod.GET, null);
		if (null != jsonObject && !jsonObject.containsKey("errcode")) {
			return true;
		}
		return false;
	}

	//获取js ticket
	public static JSTicket getJSTicket(String token){
		JSTicket jsTicket = null;
		String jsTicketUrl = WxApi.getJsApiTicketUrl(token);
		JSONObject jsonObject = httpsRequest(jsTicketUrl, HttpMethod.GET, null);
		if (null != jsonObject && jsonObject.containsKey("errcode") && jsonObject.getInt("errcode") == 0) {
			try {
				jsTicket = new JSTicket();
				jsTicket.setTicket(jsonObject.getString("ticket"));
				jsTicket.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				jsTicket = null;//获取token失败
			}
		}else if(null != jsonObject){
			jsTicket = new JSTicket();
			jsTicket.setErrcode(jsonObject.getInt("errcode"));
		}
		return jsTicket;
	}

	//获取OAuth2.0 Token
	public static OAuthAccessToken getOAuthAccessToken(String appId, String appSecret,String code) {
		OAuthAccessToken token = null;
		String tockenUrl = getOAuthTokenUrl(appId, appSecret, code);
		JSONObject jsonObject = httpsRequest(tockenUrl, HttpMethod.GET, null);
		if (null != jsonObject && !jsonObject.containsKey("errcode")) {
			try {
				token = new OAuthAccessToken();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
				token.setOpenid(jsonObject.getString("openid"));
				token.setScope(jsonObject.getString("scope"));
			} catch (JSONException e) {
				token = null;//获取token失败
			}
		}else if(null != jsonObject){
			token = new OAuthAccessToken();
			token.setErrcode(jsonObject.getInt("errcode"));
		}
		return token;
	}

	/**
	 * 上传多媒体文件
	 * 返回media_id
	 */
	public static String uploadMedia(String accessToken, String mediaType, String mediaUri) {
		String uploadMediaUrl = String.format(UPLOAD_MEDIA, accessToken, mediaType);
		String boundary = "----------" + System.currentTimeMillis();// 设置边界  
		try {
			URL uploadUrl = new URL(uploadMediaUrl);
			HttpURLConnection uploadConn = (HttpURLConnection) uploadUrl.openConnection();
			uploadConn.setDoOutput(true);
			uploadConn.setDoInput(true);
			uploadConn.setRequestMethod("POST");
			// 设置请求头Content-Type
			uploadConn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);
			// 获取媒体文件上传的输出流（往微信服务器写数据）
			OutputStream outputStream = uploadConn.getOutputStream();

			URL mediaUrl = new URL(mediaUri);
			HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl.openConnection();
			meidaConn.setDoOutput(true);
			meidaConn.setRequestMethod("GET");

			// 从请求头中获取内容类型
			String contentType = meidaConn.getHeaderField("Content-Type");
			// 根据内容类型判断文件扩展名
			String fileExt = ".jpg";
			// 请求体开始
			outputStream.write(("--" + boundary + "\r\n").getBytes());
			outputStream.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"file1%s\"\r\n",fileExt).getBytes());
			outputStream.write(String.format("Content-Type: %s\r\n\r\n",contentType).getBytes());

			// 获取媒体文件的输入流（读取文件）
			BufferedInputStream bis = new BufferedInputStream(meidaConn.getInputStream());
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				outputStream.write(buf, 0, size);
			}
			// 请求体结束
			outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
			outputStream.close();
			bis.close();
			meidaConn.disconnect();

			// 获取媒体文件上传的输入流（从微信服务器读数据）
			InputStream inputStream = uploadConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			StringBuffer buffer = new StringBuffer();
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			uploadConn.disconnect();
			// 使用JSON-lib解析返回结果
			JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
			if(jsonObject.containsKey("media_id"))
				return jsonObject.getString("media_id");
			return null;
		} catch (Exception e) {
			String error = String.format("上传媒体文件失败：%s", e);
			System.out.println(error);
		}
		return null;
	}

	//发送请求
	public static JSONObject httpsRequest(String requestUrl, String requestMethod) {
		return httpsRequest(requestUrl,requestMethod,null);
	}

	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			TrustManager[] tm = { new JEEWeiXinX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(requestMethod);
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}


	public static String httpsRequestByXml(String requestUrl, String requestMethod, String outputStr) {
		String retStrXml="";
		try {
			TrustManager[] tm = { new JEEWeiXinX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(requestMethod);
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			retStrXml=buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retStrXml;
	}

	public static byte[] httpsRequestByte(String requestUrl, String requestMethod) {
		return httpsRequestByte(requestUrl,requestMethod,null);
	}

	public static byte[] httpsRequestByte(String requestUrl, String requestMethod, String outputStr) {
		try {
			TrustManager[] tm = { new JEEWeiXinX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(requestMethod);
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			InputStream inputStream = conn.getInputStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
		    byte[] buffer = new byte[4096];
		    int n = 0;
		    while (-1 != (n = inputStream.read(buffer))) {
		        output.write(buffer, 0, n);
		    }
		    return output.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String urlEnodeUTF8(String str){
        String result = str;
        try {
            result = URLEncoder.encode(str,"UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



	/**
	 * 构造微信JSSDK支付参数，返回到页面
	 */
	public static Map<String, String> getWSJSPayPara(String openId, String appId, String appsecret,
			String partnerkey, String mch_id,
			String body,String outTradeNo,
			String payMoney,String notify_url,
			String trade_type, String str_timestamp, String str_nonceStr) {
		//支付金额
		float sessionmoney = Float.parseFloat(payMoney);
		payMoney = String.format("%.2f", sessionmoney);
		payMoney = payMoney.replace(".", "");

		System.out.println("微信端充值金额：=======" + payMoney);
		String nonce_str=str_nonceStr;  //签名用的noncestr和timestamp必须与wx.config中的nonceStr和timestamp相同。
		// 订单生成的机器 IP
		String spbill_create_ip =  "123.57.243.49";

		System.out.println("ip地址：============="  + spbill_create_ip);

		long currentTime = System.currentTimeMillis() +0 * 60 * 1000;
		String strCurrentTime=com.wxmp.core.util.wx.TenpayUtil2.getWXTime(currentTime);
		long currentTimeLay30m = currentTime + 30 * 60 * 1000;//30分钟后 【要求至少5分钟过期】
		String strCurrentTimeLay30m=com.wxmp.core.util.wx.TenpayUtil2.getWXTime(currentTimeLay30m);
		// 订 单 生 成 时 间 非必输
		String time_start =strCurrentTime;
		// 订单失效时间 非必输
		String time_expire = strCurrentTimeLay30m;

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appId);
		packageParams.put("mch_id", mch_id);
		packageParams.put("device_info", "WEB");//PC网页或公众号内支付请传"WEB"
		packageParams.put("body", body);
     	packageParams.put("openid", openId); //jssdk模式时这个必填
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", outTradeNo);
		packageParams.put("total_fee", payMoney);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);
		packageParams.put("trade_type", trade_type);

		com.wxmp.core.util.wx.RequestHandler reqHandler = new com.wxmp.core.util.wx.RequestHandler(null, null);
		reqHandler.init(appId, appsecret, partnerkey);

		//##生成加密签名，此签名用于统一订单接口
		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>"
						   + "<openid>" + openId + "</openid>"
					       + "<appid>" + appId + "</appid>"
					       + "<mch_id>"	+ mch_id + "</mch_id>"
					       + "<device_info>"+ "WEB" + "</device_info>"
					       + "<nonce_str>" + nonce_str+ "</nonce_str>"
						   + "<body><![CDATA[" + body + "]]></body>"
						   + "<out_trade_no>"	+ outTradeNo	+ "</out_trade_no>"
						   +"<total_fee>"+ payMoney+ "</total_fee>"
						   +"<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
						   + "<notify_url>" + notify_url + "</notify_url>"
						   + "<trade_type>" + trade_type + "</trade_type>"
						   + "<sign>" + sign + "</sign>"
			        +"</xml>";

		System.out.println("手机端外部订单号：out_trade_no" +  outTradeNo);
		System.out.println("======================================：");
		System.out.println("手机端统一订单接口传参："+xml);

		Map<String, String> dataMap = new HashMap<String,String>();
		String createOrderURL = getUnifiedOrderUrl();
		String prepay_id = "";
		String errMsg="";
		String timestamp = String.valueOf(System.currentTimeMillis()/1000);
		String nonceStr = SecurityUtil.getRandomString(8);
		 //#######jssdk config配置时已设置，从前台传过来######
		timestamp =str_timestamp;
		nonceStr=str_nonceStr;//nonce_str:下统一订单时的随机字符串，此时这三个随机变量完全相等
		String paySign ="";

		try {
			String retXmlStr = httpsRequestByXml(createOrderURL, HttpMethod.POST, xml);
			System.out.println("手机端统一订单接口结果:"+retXmlStr);
			if (null != retXmlStr && retXmlStr.contains("return_code") ) {
				Map map = com.wxmp.core.util.wx.TenpayUtil2.doXMLParseByDom4j(retXmlStr);
				String return_code = (String) map.get("return_code");
				if("SUCCESS".equals(return_code)){
					String result_code = (String) map.get("result_code");
					if("SUCCESS".equals(result_code)){
							prepay_id = (String) map.get("prepay_id");
							errMsg="0";
					}else{
						prepay_id="";
						errMsg= (String) map.get("err_code_des");
					}
				}else{
					prepay_id="";
					errMsg= (String) map.get("return_msg");
				}

				//###生成支付签名,此签名 给 微信支付的调用使用（前台页面使用）
				SortedMap<String, String> packageParams_paySign = new TreeMap<String, String>();
				packageParams_paySign.put("appId",appId);
				packageParams_paySign.put("timeStamp",timestamp);//后台生成签名时是大写的timeStamp
				packageParams_paySign.put("nonceStr",nonceStr);//签名用的noncestr和timestamp必须与wx.config中的nonceStr和timestamp相同。
				packageParams_paySign.put("package", "prepay_id="+prepay_id);
				packageParams_paySign.put("signType", "MD5");
				com.wxmp.core.util.wx.RequestHandler reqHandler_paySign = new com.wxmp.core.util.wx.RequestHandler(null, null);
				reqHandler_paySign.init(appId, appsecret, partnerkey);
				paySign = reqHandler_paySign.createSign(packageParams_paySign);

				/**
				 * 最后别忘记一定要设置：微信支付授权目录设置
				 * 微信支付授权目录设置
					 注意这里所指的是目录，所以一定要以左斜杠“/”结尾，不是设置支付的url网址
					 例如设置的是：http://www.newfms.com/order/pay/
					那么真正支付url是
					http://www.newfms.com/order/pay/
					http://www.newfms.com/order/pay/1
					如果是这样的支付url就会报错：http://www.newfms.com/order/pay
				 */

			}else{
				System.out.println("统一支付接口出错");
				prepay_id="";
				errMsg= "统一支付接口出错";
			}
		} catch (Exception e1) {
			System.out.println("系统异常\n"+e1.getMessage());
			prepay_id="";
			errMsg= "系统异常\n"+e1.getMessage();
		}//try-catch end

		//###返回给 微信支付的调用使用（前台页面使用）
		dataMap.put("appid",appId);
		dataMap.put("timestamp",timestamp);//前台页面调用jssdk支付时是小写的timestamp
		dataMap.put("nonceStr",nonceStr);
		dataMap.put("package", "prepay_id="+prepay_id);
		dataMap.put("signType", "MD5");
		dataMap.put("paySign",paySign);
		dataMap.put("errMsg",errMsg);

		return dataMap;
	}

	//获取新增图文素材url
	public static String getNewsMaterialUrl(String token){
		return String.format(ADD_NEWS_MATERIAL, token);
	}

	//获取修改图文素材url
	public static String getUpdateNewsMaterialUrl(String token){
		return String.format(UPDATE_NEWS_MATERIAL, token);
	}

	//上传永久图片素材
	public static String getMaterialImgUrl(String token){
		return String.format(UPLOAD_MATERIAL_IMG, token);
	}

	//获取新增素材url
	public static String getMaterialUrl(String token ,String type){
		return String.format(ADD_MATERIAL, token, type);
	}

    /**
     * 新增图文永久素材
     * @param materialUri
     * @param filePath
     * @return
     * @throws Exception
     */
    public static JSONObject addMaterial(String materialUri, String filePath) throws Exception {
        String result = null;
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            try {
				throw new IOException("文件不存在");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        /**
        * 第一部分
        */
        URL urlObj = new URL(materialUri);
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ BOUNDARY);
        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filename=\""+ file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
        out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
	        // 定义BufferedReader输入流来读取URL的响应
	        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String line = null;
	        while ((line = reader.readLine()) != null) {
	        //System.out.println(line);
	        buffer.append(line);
	        }
	        if(result==null){
	           result = buffer.toString();
	        }
	        JSONObject jsonObject = JSONObject.fromObject(result.toString());

			if(jsonObject != null){
				return jsonObject;
			}
        } catch (IOException e) {
	        System.out.println("发送POST请求出现异常！" + e);
	        e.printStackTrace();
	        throw new IOException("数据读取异常");
        } finally {
	        if(reader!=null){
	            reader.close();
	        }
        }

        return null;
    }


//	/**
//	 * 构造微信扫码支付
//	 */
//	public static Map<String, String> getNativePayData(String outTradeNo,
//			String finalmoney) {
//		String appId = WxPayConfig.APP_ID;
//		String appsecret = WxPayConfig.APP_SECRET;
//		String partnerKey = WxPayConfig.PARTNER_KEY;
//		String mchId = WxPayConfig.MCH_ID;
//		String body = "pcRecharge";
//		String tradeType = WxPayConfig.PC_TRADE_TYPE;
//		String nonceStr = SecurityUtil.getRandomString(8);
//		String notifyURL = WxPayConfig.NOTIFY_URL;
//		String spbillCreateIp =  "123.57.243.49";// 订单生成的机器 IP
////		 //金额转化为分为单位
//
////		//支付金额
//		//金额转化为分为单位
//		float sessionmoney = Float.parseFloat(finalmoney);
//		finalmoney = String.format("%.2f", sessionmoney);
//		finalmoney = finalmoney.replace(".", "");
//
//		System.out.println("pc微信端扫码充值金额：=======" + finalmoney);
//
//		long currentTime = System.currentTimeMillis() +0 * 60 * 1000;
//		String strCurrentTime=com.wxmp.core.util.wx.TenpayUtil2.getWXTime(currentTime);
//		long currentTimeLay30m = currentTime + 30 * 60 * 1000;//30分钟后 【要求至少5分钟过期】
//		String strCurrentTimeLay30m=com.wxmp.core.util.wx.TenpayUtil2.getWXTime(currentTimeLay30m);
//		// 订 单 生 成 时 间 非必输
//		String time_start =strCurrentTime;
//		// 订单失效时间 非必输
//		String time_expire = strCurrentTimeLay30m;
//
//		SortedMap<String, String> packageParams = new TreeMap<String, String>();
//		packageParams.put("appid", appId);
//		packageParams.put("mch_id", mchId);
//		packageParams.put("device_info", "WEB");//PC网页或公众号内支付请传"WEB"
//		packageParams.put("body", body);
//		packageParams.put("nonce_str", nonceStr);
//		packageParams.put("out_trade_no", outTradeNo);
//		packageParams.put("total_fee", finalmoney);
//		packageParams.put("spbill_create_ip", spbillCreateIp);
//		packageParams.put("notify_url", notifyURL);
//		packageParams.put("trade_type", tradeType);
//
//		com.wxmp.core.util.wx.RequestHandler reqHandler = new com.wxmp.core.util.wx.RequestHandler(null, null);
//		reqHandler.init(appId, appsecret, partnerKey);
//
//		//##生成加密签名，此签名用于统一订单接口
//		String sign = reqHandler.createSign(packageParams);
//		String xml = "<xml>"
//					       + "<appid>" + appId + "</appid>"
//					       + "<mch_id>"	+ mchId + "</mch_id>"
//					       + "<device_info>"+ "WEB" + "</device_info>"
//					       + "<nonce_str>" + nonceStr+ "</nonce_str>"
//						   + "<body><![CDATA[" + body + "]]></body>"
//						   + "<out_trade_no>"+outTradeNo+"</out_trade_no>"
//						   + "<total_fee>"+finalmoney+"</total_fee>"
//						   + "<spbill_create_ip>"+spbillCreateIp+ "</spbill_create_ip>"
//						   + "<notify_url>"+notifyURL+"</notify_url>"
//						   + "<trade_type>"+tradeType+ "</trade_type>"
//						   + "<sign>" + sign + "</sign>"
//			        +"</xml>";
//		System.out.println("pc端====外部订单号：out_trade_no" +  outTradeNo);
//		System.out.println("======================================：");
//		System.out.println("pc端====统一订单接口传参："+xml);
//
//		Map<String, String> dataMap = new HashMap<String,String>();
//		String createOrderURL = getUnifiedOrderUrl();
//		String errMsg="";
//		String urlCode = "";
//		String out_trade_no = "";
//		try {
//			String retXmlStr = httpsRequestByXml(createOrderURL, HttpMethod.POST, xml);
//			System.out.println("pc端====统一订单接口结果:"+retXmlStr);
//			if (null != retXmlStr && retXmlStr.contains("return_code") ) {
//				Map map = com.wxmp.core.util.wx.TenpayUtil2.doXMLParseByDom4j(retXmlStr);
//				String return_code = (String) map.get("return_code");
//				if("SUCCESS".equals(return_code)){
//					String result_code = (String) map.get("result_code");
//					if("SUCCESS".equals(result_code)){
//						    urlCode = (String) map.get("code_url");
//							errMsg="0";
//							out_trade_no = (String)packageParams.get("out_trade_no");  //商户订单
//					}else{
//						errMsg= (String) map.get("err_code_des");
//						out_trade_no = (String)packageParams.get("out_trade_no");  //商户订单
//					}
//				}else{
//					errMsg= (String) map.get("return_msg");
//					out_trade_no = (String)packageParams.get("out_trade_no");  //商户订单
//				}
//			}else{
//				System.out.println("统一支付接口出错");
//				errMsg= "统一支付接口出错";
//			}
//		} catch (Exception e1) {
//			System.out.println("系统异常\n"+e1.getMessage());
//			errMsg= "系统异常\n"+e1.getMessage();
//		}
//		dataMap.put("urlCode",urlCode);
//		dataMap.put("errMsg",errMsg);
//		dataMap.put("outTradeNo",out_trade_no);
//		return dataMap;
//	}
}



class JEEWeiXinX509TrustManager implements X509TrustManager {
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}