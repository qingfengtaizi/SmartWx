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
                        <ul class="per_nav">
                           <li>
                               <span class="per_span">
                                  <label for="name">姓名：</label>
                               </span>
                              <input id="name" type="text" disabled="disabled" value="${sysUser.trueName}"/>
                           </li>
                           <li>
                                <span  class="per_span">
                                   <label for="account">账号：</label>
                                </span>
                                <input id="account" type="text" disabled="disabled"  
                                    value="${sysUser.account}"/>
                           </li>
                           <li>
                              <span  class="per_span">
                                <label for="phone">手机号码：</label>
                              </span>
                              <input id="phone" type="text"  disabled="disabled" value="${sysUser.phone}" />
                           </li>
                        </ul>
                         <div class="btn_div">
                            <a class="save" href="javascript:void(0);">修改</a>
                            <a class="back"  href="history:go(-1);" >返回</a>
                         </div>   
                  </div>    
            </div>
        </div>
    </div>
</div>
</body>
</html>
