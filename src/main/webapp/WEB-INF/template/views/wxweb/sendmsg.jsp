<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width,user-scalable=no" name="viewport">
<title>测试：给我自己发送消息</title>
<link rel="stylesheet" type="text/css"  href="<%=path%>/res/css/common/style.css" />
<link rel="stylesheet" type="text/css"  href="<%=path%>/res/css/web/login.css" />
<script type="text/javascript" src="<%=path %>/res/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/res/js/web/login.js"></script>
<style type="text/css">
#id_description p{
	font-size: 16px;
	letter-spacing:2px;
	line-height:22px;
	font-family: "Microsoft YaHei" ! important;
	color: #3e3e3e;
}
#id_description img{
	margin-top: 2px;
	border-radius:5px; 
}
.btn{
	display:inline-block;
	cursor:pointer;
	background-color:#44b549;
	border: 1px solid #44b549; 
	border-radius:3px;
	width:90px;
	height: 30px;
	line-height: 28px;
	margin-left: 10px;
	color:#fff;
	margin-top: 5px;
}
</style>
</head>
<body style="margin: 0px;padding: 0px;background-color:#e7e8eb;">
		<div style="margin:0 auto;background-color:#fff;">
			<div style="padding: 0px 20px;">
				<div style="height:10px;"></div>
			
				<div style="font-weight:bold;font-size:20px;border-bottom:1px solid #e7e8eb;">
				测试给我自己发送消息,请确保您已经关注了微信派官方账号（weixinpy-com）
				</div>
				
				<div style="margin-top:20px;">
					1.给我发送客服消息：
					<form action="<%=path %>/wxapi/sendCustomTextMsg" method="post">
						<input name="openid" type="hidden" value="${openid}"/><!-- 我的openid，是通过OAuth接口获取的 -->
						<input type="submit" value="点击发送" class="btn"/>
					</form>
				</div>
				
				<div style="margin-top:20px;">
					2.给我发送模板消息：
					<form action="<%=path %>/wxapi/sendTemplateMessage" method="post" >
						<input name="openid" type="hidden" value="${openid}"/><!-- 我的openid，是通过OAuth接口获取的 -->
						<input type="submit" value="点击发送" class="btn"/>
					</form>
				</div>
			</div>
		</div>
</body>
</html>
