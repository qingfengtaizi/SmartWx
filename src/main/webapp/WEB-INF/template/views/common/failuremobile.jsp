<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" charset="utf-8">
<title>微信开发</title>
<link rel="stylesheet" type="text/css"  href="<%=path%>/res/css/common/style.css" />
<link rel="stylesheet" type="text/css"  href="<%=path%>/res/css/web/login.css" />
<script type="text/javascript" src="<%=path %>/res/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/res/js/web/login.js"></script>

</head>
	
<body class="bg">
		<!-- top开始  -->
		<div class="top">
			<div class="top-bar">
				<a href="#">
					<div class="logo"></div>
				</a>
				<div style="float: left;margin-left:10px;height:60px;line-height:90px;">
					<span style="color:#0085c2;">微信开发</span>
				</div>
				<div style="float:right;">
					<a target="_blank" href="http://www.weixinpy.com">
					<div class="shiyong"></div>
					</a>
				</div>
				<div class="clearfloat"></div>
			</div>
		</div>
		<!-- top结束 -->
		<div class="content">
			
			<div style="margin-top:50px;">
				<span style="font-size:16px;">${failureMsg}</span>
			</div>
			
			<div class="clearfloat"></div>
		</div>
		<!-- footer结束 -->
</body>
</html>
