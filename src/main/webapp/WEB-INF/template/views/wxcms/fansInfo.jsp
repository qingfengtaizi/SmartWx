<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>粉丝管理</title>
<link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/URL_Token.css" rel="stylesheet">
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
                <li>
                   <h6><a href="<%=path%>/accountfans/paginationEntity">粉丝管理</a></h6>
                   <h6>></h6>
                   <h6>粉丝详细信息</h6>
                </li>
            </ul>
        </div>
       
       
        <div class="rightInfo">
        	<h2 class="infoTitle">粉丝详细信息</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
            	粉丝的详细信息 
            </p>
			
            <div class="fansMessInfo">
				<table>
                    <tbody>
                        <tr>
								<td >头像</td>
								<td>
									<img style="width:64px;height:64px;margin:5px;" src="${fans.headimgurl}"/></td>
					    </tr>
					    <tr>
								<td style="width:300px;">昵称</td>
								<td>${fans.nicknameStr}</td>
						</tr>
						<tr>
								<td >地址</td>
								<td>${fans.province}-${fans.city}</td>
						</tr>
						<tr>
								<td >openid</td>
								<td>${fans.openId}</td>
						</tr>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>
</body>
</html>
