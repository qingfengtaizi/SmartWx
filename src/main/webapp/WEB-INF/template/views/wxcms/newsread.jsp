<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width,user-scalable=no" name="viewport">
<title>微信开发</title>
<link rel="stylesheet" type="text/css"  href="<%=path%>/res/css/common/style.css" />
<link rel="stylesheet" type="text/css"  href="<%=path%>/res/css/login/login.css" />
<script type="text/javascript" src="<%=path %>/res/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/res/js/login/login.js"></script>
</head>
<body style="margin: 0px;padding: 0px;">
		<div style="padding:0px 10px;">
			<div style="font-weight:bold;font-size:18px;margin-top:10px;">${news.title}</div>
		
			<div style="margin:20px 0px;">
				<img src="${news.picpath}" style="width:100%;border:none;">
			</div>
			
			<div>
				${news.description}
			</div>
		</div>
</body>
</html>
