<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微信开发</title>
<link rel="stylesheet" type="text/css"  href="<%=path%>/res/css/common/style.css" />
<link rel="stylesheet" type="text/css"  href="<%=path%>/res/css/login/login.css" />
<script type="text/javascript" src="<%=path %>/res/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/res/js/web/login.js"></script>
</head>
<body style="background-color:#fdfdfd;">
		<div class="top" style="background-color:#dfdfdf;height:0px;">
			<div style="height:10px;"></div>
			<div class="top-bar" style="height:70px;line-height:70px;">
			</div>
		</div>
		
		<div class="top2" >
			
		</div>
		<div class="clearfloat"></div>
		
		<div class="content2" style="border:none;margin-top:20px;background-color:#fdfdfd;">
			<div style="float: left;width:300px;">
				<h2 style="font-size:20px;font-weight:bold;margin-bottom:10px;">接口功能</h2>
				<ul class="action-ul">
					<li>1、URL、Tocken 认证接口</li>
					<li>2、消息接收接口</li>
					<li>3、文本消息回复接口</li>
					<li>4、单条、多条图文消息回复接口</li>
					<li>5、订阅消息设置</li>
					<li>6、文本消息维护</li>
					<li>7、图文消息维护</li>
					<li>8、图文消息在微信中展现页面</li>
					<li>9、公众账号菜单维护</li>
					<li>10、创建微信公众账号菜单组、菜单</li>
					<li>11、用户关注公众账号时获取用户信息</li>
					<li>12、批量同步用户信息</li>
					<li>13、上传多媒体文件</li>
					<li>14、上传图文消息</li>
					<li>15、群发图文消息</li>
					<li>16、批量获取用户信息</li>
					<li>17、OAuth2.0接口</li>
					<li>18、永久素材获取接口</li>
					<li>19、发送客服消息</li>
					<li>20、发送模板消息</li>
					<li>21、生成永久二维码</li>
					<li>22、生成临时二维码</li>
					<li>23、增加了账号缓存、accessToken缓存</li>
					<li>24、缓存openid模拟http有状态session</li> 
					<li>25、通过interceptor拦截器实现OAuth认证，并缓存openid</li>
					<li>26、JS-Ticket的缓存</li>
					<li>27、JS-SDK 实例</li>
					<li>28、自定义个性化菜单接口</li>
				</ul>
			</div>
			<div class="split"></div>
			<div style="float: left;width:300px;">
				<h2 style="font-size:20px;font-weight:bold;margin-bottom:10px;">demo.</h2>
				<div>
					<div style="">
						<a href="<%=path%>/wxcms/urltoken">	进入.... </a>	
					</div>
				</div>
			</div>
			
			<div class="split"></div>

			<div class="clearfloat"></div>
		</div>
		<div style="height:60px;"></div>
	    <!-- footer -->
	    <div class="footer" style="height:250px;">
			<div class="footer-bar" style="text-align:left;height:250px;">
				<div>
					<div class="tuijian-block" style="width:450px;">
						<div></div>
					</div>
					<div class="tuijian-block"  style="margin-left:50px;">
						<div></div>
					</div>
					<div class="tuijian-block">
						<img src="<%=path%>/res/images/wxmp.png" />
					</div>
				</div>
				<div class="clearfloat"></div>
				<div style="height:100px;">
					<span></span>
					<span style="margin-left:50px;" ></span>
				</div>
				<div class="clearfloat"></div>
			</div>
		</div>
</body>
</html>