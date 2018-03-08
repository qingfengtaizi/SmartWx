<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎登陆</title>
<link rel="stylesheet" type="text/css"  href="<%=path%>/res/css/common/style.css" />
<link rel="stylesheet" type="text/css"  href="<%=path%>/res/css/web/login.css" />
<script type="text/javascript" src="<%=path %>/res/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/res/js/web/login.js"></script>
</head>
<body class="login">
    <div class="login-bg"></div>
	<div class="login_m">
		<div class="login_logo"><img src="<%=path%>/res/images/logo.png" /></div>
		<div class="login_boder">
			<div class="login_padding">
				 <h2>用户名</h2>
				<label>
					<input type="text" id="username" class="txt_input txt_input2" />
				</label>
				<h2>密码</h2>
				<label>
					<input type="password" name="pwdId" id="userpwd" class="txt_input"/>
				</label>
				<div class="login_sub">
					<label>
						<input type="button" class="sub_button" id="loginId" value="登录" style="opacity: 0.7;"/>
					</label>
				</div>
			</div>
		</div><!--login_boder end-->
	</div><!--login_m end-->
	<br />
	<br />
</body>
</html>