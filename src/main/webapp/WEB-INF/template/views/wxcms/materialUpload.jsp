<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>永久素材管理</title>
<link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/URL_Token.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/top_left.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/materialMess.css" rel="stylesheet">

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
                <li><h6>首页</h6><h6>></h6><h6>文本消息</h6></li>
            </ul>
        </div>
       
       
        <div class="rightInfo">
        
          <h2 class="infoTitle">永久素材上传</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
            	以上传图文素材为例，其中包括将"图片链接"对应的图片上传到微信服务器
            </p>
            
           <div class="block-content" >
             <div class="content" style=" ">
				<div class="block-content-nav">
					<span style="color:#EDA776;">永久素材上传
					</span>
				</div>
				<div class="block-content-content">
					<form class="fm-form" action="<%=path%>/wxapi/doUploadMaterial" method="post">
						<ul>
							<li style="margin-top:30px;">
								<div class="bor"><label for="title">标题</label></div>
								<input style="width:30%;" id="title" name="title" type="text" value=""/>
							</li>
							<li style="margin-top:30px;">
								<div class="bor"><label for="picpath">图片链接 </label></div>
								<input style="width:30%;" id="picpath" name="picpath" type="text" value=""/>
							</li>
							<li style="margin-top:30px;">
								<div class="bor"><label for="author">作者 </label></div>
								<input style="width:30%;" id="author" name="author" type="text" value="微信派"/>
							</li>
							<li style="margin-top:30px;">
								<div class="bor"><label for="fromurl">原文链接 </label></div>
								<input style="width:30%;" id="fromurl" name="fromurl" type="text" value="www.weixinpy.com"/>
							</li>
							
						</ul>
						
						<div style="margin-top: 20px;margin-left:110px;">
							<input class="btn"  style="background:#1A9DA7;color:#fff;" type="submit" value="上 传"/>
						</div>
					</form>
				</div>
			  </div>	
			</div>
			
		</div>
        </div>	
</div>
</body>
</html>
