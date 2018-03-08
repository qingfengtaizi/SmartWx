<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>main</title>
<link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/top_left.css" rel="stylesheet">

<script src="<%=path%>/res/js/common/jquery.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>
</head>
	
<body>
<div class="top" >
	<jsp:include page="/WEB-INF/template/views/common/top.jsp"></jsp:include>
</div>
<div class="main" style="width:1100px;margin:70px  auto;">

    <div class="nav-top" style="width:100%;">
    	<jsp:include page="/WEB-INF/template/views/common/topNavDemo.jsp"></jsp:include> 
    </div>
    <div  class="main-left">
        <img  src="<%=path%>/res/images/biaoz.png" style="width:400px;"/>
    </div>
    <div class="main-right">
       <ul class="main_ul">
         <li class="font_h3" style="margin-left:-50px;font-size:18px;font-family:'Times New Roman', Times, serif;font-weight:800;"><font class="boldfont" >admin&nbsp;</font>您好欢迎登陆微信后台管理系统</li>
         <li class="font_h3" style="margin-top:50px;"><p class="radiuss"></p>用户权限：<font  style="color:#f90;">超级管理员</font></li>
         <li class="font_h3"><p class="radiuss"></p>当前IP地址：<font  style="color:#f90;">192.168.12.1</font></li>
         <li class="font_h3"><p class="radiuss"></p>上次登陆地址：<font  style="color:#f90;">山东济南市</font></li>
         <li class="font_h3"><p class="radiuss"></p>当前登陆时间：<font  style="color:#f90;">10:10</font></li>
         <li class="font_h3"><p class="radiuss"></p>上次登陆时间：<font  style="color:#f90;">10:10</font></li>
         <li class="font_h3"><p class="radiuss"></p>登陆次数：<font  style="color:#f90;">10</font></li>
         
       </ul>
    
    </div>
    
</div>
</body>
</html>
