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
<!-- 自定义 -->
<script src="<%=path%>/res/js/web/loginpwd.js"></script>
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
            	修改登录密码 
            </p>
			
            <div class="personalx">
               <div class="personal_center">
                        <input type="hidden" id="sysuserId" value="${sysUser.id}">
                        <input type="hidden" id="oldPwd" value="${sysUser.pwd}">
                        <ul class="per_nav">
                           <li>
                               <span class="per_span">
                                  <label for="pwd">旧密码：</label>
                               </span>
                              <input id="pwd" type="password" placeholder="请输入旧密码"/>
                              <span id="oldpwd_Tip" class="usercarID">*</span>
                           </li>
                           <li>
                                <span  class="per_span">
                                   <label for="newpwd">新密码：</label>
                                </span>
                                <input id="newpwd" type="password" placeholder="请输入新密码"/>
                                <span id="newpwd_Tip" class="usercarID">*</span>
                           </li>
                           <li>
                              <span  class="per_span">
                                <label for="newpwdagain">确认新密码：</label>
                              </span>
                              <input id="newpwdagain" type="password" placeholder="请确认新密码"/>
                              <span id="newpwdagain_Tip" class="usercarID">*</span>
                           </li>
                        </ul>
                         <div class="btn_div">
                            <a class="save" id="loginpwdBtn" href="javascript:void(0);">保存</a>
                         </div>   
                  </div>    
            </div>
        </div>
    </div>
</div>
</body>
</html>
