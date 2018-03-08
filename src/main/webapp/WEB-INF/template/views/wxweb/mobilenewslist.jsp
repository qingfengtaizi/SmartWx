<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width,user-scalable=no" name="viewport">
<link rel="stylesheet" type="text/css"  href="<%=path%>/res/css/common/style.css" />
<link rel="stylesheet" type="text/css"  href="<%=path%>/res/css/web/login.css" />
<script type="text/javascript" src="<%=path %>/res/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/res/js/web/login.js"></script>
<style type="text/css">
.msg-item{
	text-align:center;
	padding:5px;
	margin:10px 5px;
	background-color: #fff;
	border-radius:5px;
	min-height: 80px;
}
.msg-item a{
	text-decoration: none;
}
.msg-item .nav-a:hover{
	text-decoration: underline;
}
.msg-item img{
	border-radius:5px;
	border: none;
}
.msg-content{
	text-align:left;
	font-size:16px;
	line-height:20px;
	min-height: 50px;
}
.msg-content div{
	font-size:16px;
}
</style>
</head>
<body style="margin: 0px;padding:10px; background-color: #eeeeee;">
		<section style="width:100%;" >
			<c:forEach var="row" items="${pageList}" varStatus="status">
				<div class="msg-item">
					<div class="msg-content">
						<div style="margin-top:2px;text-align:left;font-size:12px;color:#6f6f6f;">
						      ${row.createTimeStr}
						</div>
						<c:forEach var="row" items="${row.msgNewsList}" varStatus="status">
							<c:choose>
							   <c:when test="${not empty news.fromurl}">
							      <a href="${news.fromurl}" target="_blank"></a>
							   </c:when>
							   <c:otherwise>
							      <a href="${news.url}" target="_blank"></a>
							   </c:otherwise>
							</c:choose>
							<div style="padding:5px 0px;border-top:1px solid #f0f0f0;height: 40px;">
							    <c:if test="${news.picpath != '' and news.picpath != null}">
							        <img src="${news.picpath}" style="width:40px;height:40px;border-radius:0px;float:left;">
							    </c:if>
								<div style="margin-left:5px;font-size: 14px;float: left;width:250px;color:#000">${news.title}</div>
								<div class="clearfloat"></div>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:forEach>
			
		</section>
</body>
</html>
