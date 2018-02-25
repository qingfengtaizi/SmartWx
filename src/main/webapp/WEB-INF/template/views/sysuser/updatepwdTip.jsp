<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心</title>
<link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/top_left.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/fansMess.css" rel="stylesheet">

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
                <li><h6>个人中心</h6></li>
            </ul>
        </div>
                <div class="rightInfo">
        	<h2 class="infoTitle">个人中心</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
            	个人信息详情  
            </p>
			
            <div class="personalx">
               <div class="personal_center">
                       <!--密码修改成功后的显示-->
	                   <div  class="succe">
	                      <img  src="<%=path%>/res/images/over.png" style="width:100px;"/>
	                      <span class="PW">密码修改成功&nbsp;!</span>
	                      <div class="btn_div">
	                            <a class="save"  href="javascript:void(0);"  >确定</a>
	                      </div> 
	                   </div>
                  </div>    
            </div>
        </div>
    </div>
</div>
</body>
</html>
