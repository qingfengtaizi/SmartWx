<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>OAuth认证</title>
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
                <li><h6>首页</h6><h6>></h6><h6>通过拦截器处理OAuth认证</h6></li>
            </ul>
        </div>
       
       
        <div class="rightInfo">
        	<h2 class="infoTitle">JS-SDK</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
            		在微信中如果需要通过页面获取粉丝信息，可以通过拦截器实现OAuth的认证。 以通过OAuth接口获取openid为例。
请关注“官方微信(weixinpy-com)”体验。
            </p>
			
            <div class="block-content-content">
					<div>拦截器参考类：WxOAuth2Interceptor.java
拦截器在spring配置文件中设置为只拦截：/wxapi/oauthOpenid.html 链接。参考：WxApiCtrl.oauthOpenid方法</div>
			</div>
		</div>			
	</div>		
</div>
</body>
</html>
