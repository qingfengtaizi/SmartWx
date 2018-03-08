<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>WeUI</title>
<link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/URL_Token.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/top_left.css" rel="stylesheet">

<script src="<%=path%>/res/js/common/jquery.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>
<script src="<%=path%>/res/js/web/table.js"></script>
		
		
</head>
	
<body>

<div class="top">
	<jsp:include page="/WEB-INF/template/views/common/top.jsp"></jsp:include>
</div>
<div class="main">
    <jsp:include page="/WEB-INF/template/views/common/leftDemo.jsp"></jsp:include>
	  
    <div class="mainRight">
        <jsp:include page="/WEB-INF/template/views/common/topNavDemo.jsp"></jsp:include>
    	
         <div class="posInfo">
        	<ul>
            	<li><span class="posIcon"><img src="<%=path%>/res/images/pos_icon_03.png">当前位置：</span></li>
                <li><h6>首页</h6><h6>></h6><h6>WeUI</h6></li>
            </ul>
        </div>
       
       
        <div class="rightInfo">
        	<h2 class="infoTitle">WeUI</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
            		WeUI 是一套同微信原生视觉体验一致的基础样式库，由微信官方设计团队为微信内网页开发量身设计，可以令用户的使用感知更加统一。
						微信页面中的JS-SDK接口的使用。请关注“官方微信(weixinpy-com)”体验。
            </p>
			
            <div class="block-content-content">
					<div >
						<div style="margin:10px 0px;">
							<div><span>参考：wxweb/weui</span>
								<a href="<%=path%>/wxweb/weui" target="_blank">
								<span class="btn" style="text-align:center;">查看效果</span>
								</a>
							</div>
						</div>
			
</div>
</body>
</html>
