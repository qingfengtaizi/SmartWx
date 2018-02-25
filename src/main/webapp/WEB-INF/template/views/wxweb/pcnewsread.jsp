<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
		<div style="padding:0px 20px;width:740px;margin:0 auto;background-color:#fff;min-height:500px;">
			<div style="height:10px;"></div>
			
			<div style="font-weight:bold;font-size:20px;border-bottom:1px solid #e7e8eb;height:50px;line-height:50px;">
			     ${news.title}
			</div>
		
			<div style="margin-top:10px;font-size:13px;">
				<span style="color:#676767;">
				 <fmt:formatDate value="${news.createtime}" pattern="yyyy-MM-dd" />
				</span>
				<span style="margin-left:10px;color:#676767;">${news.author}</span>
				<span style="margin-left:10px;color:#607fa6;">jeeweixin</span>
			</div>
		
			<div style="margin:15px 0px;">
				<c:if test="${news.showpic != null and news.showpic == 1}">
				<img src="${news.picpath}" style="width:100%;border:none;">
				</c:if>
			</div>
			
			<div id="id_description">
				${news.description}
			</div>
			
			<div style="height:60px;"></div>
		</div>
</body>	
</html>
