<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width,user-scalable=no" name="viewport">
<title>${news.title}</title>
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
</style>
</head>
	
<body style="margin: 0px;padding: 0px;background-color:#e7e8eb;">
		<div style="margin:0 auto;background-color:#fff;">
			<div style="padding: 0px 20px;">
				<div style="height:10px;"></div>
			
				<div style="font-weight:bold;font-size:20px;border-bottom:1px solid #e7e8eb;">
				我的openid和信息，请确保您已经关注了优驾易点通公众号（yjydt0531）：
				</div>
				
				<div style="margin:10px 0px;">
					我的openid : ${openid}
				</div>
				
				<div>
					<c:if test="${fans != '' and fans != null}">
					<div>我的信息：</div>
					<div style="margin-top:5px;"><img src="${fans.headimgurl}" width="80px;"/></div>
					<div style="margin-top:5px;">${fans.nicknameStr}</div>
					<div style="margin-top:5px;">${fans.province}-${fans.city}</div>
					</c:if>
				</div>
			</div>
		</div>
</body>
</html>
