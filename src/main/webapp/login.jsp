<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微信后台管理系统</title>
<link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/login.css" rel="stylesheet">


<script src="<%=path%>/res/js/common/jquery.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/contral.js"></script>
<script src="<%=path%>/res/js/web/login.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>

<!-- jquery-weiwu -->
<link rel="stylesheet" href="<%=path %>/res/js/plugins/jqueryweiui/css/weui.min.css">
<link rel="stylesheet" href="<%=path %>/res/js/plugins/jqueryweiui/css/jquery-weui.css">
<script type="text/javascript" src="<%=path %>/res/js/plugins/jqueryweiui/js/jquery-weui.js"></script>
</head>
<body>
<div id="login-page">
	<img id="login-bg" src="<%=path%>/res/images/login_bg.jpg">
	<div id="login-main" class="box-shadow box-radius">
    	<img src="<%=path%>/res/images/logo.png" class="img-responsive">
        
        <div class="login-input">
            <form class="bs-example bs-example-form" role="form">
                  <div class="input-group">
                  	 <h2>登录名</h2>
                     <input type="text" id="accountInput" class="form-control box-radius" >
                  </div>
                  <div class="input-group">
                  	 <h2>密码</h2>
                     <input type="password" id="pwdInput" class="form-control box-radius">
                  </div>
            
            </form>
            <input class="login-btn" type="button" value="登录">        
        </div>
        <h1 id="forget-pass" style="display:none;"><a href="#" onclick="zhaohpw()" >忘记密码？</a></h1>
    </div>
    
   <!-- 密码找回-->
    <div  class="backpw"  style="display:none;"></div>
    <div class="bg_center" style="display:none;">
        <div class="back_div">
            <h3>密码找回</h3>
                     <div class="account"  style="display:block">
                        <ul class="back_nav">
                           <li>
                             <span class="back_span">
                                <label for="oldpassword">输入账号：</label>
                             </span>
                             <input id="oldpassword" type="text"  onblur="checkaccound(this.value)"/>
                             <span id="username" class="username">*</span>
                           </li>
                          
                           <li><span  class="back_span">
                               <label for="passtype">密码找回方式：</label></span>
                               <select id="passtype"   style="height:30px;"   onblur="selectfunc()">
                                   <option value="0">手机找回</option>    
                                   <option value="1">邮箱找回</option>     
                                <select>
                           </li>
                           <li style="display:block" id="telldisplay">
                              <span  class="back_span"><label for="cht">输入手机号：</label></span>
                              <input id="cht"    style="width:260px" type="text" onblur="checkt(this.value)"/>
                              <p class="getyz" >获取验证码</p>
                              <span id="postpw" class="userphone" style="margin-left:190px;display:none"></span>
                           
                           </li>
                           <li style="display:none" id="maildisplay">
                              <span  class="back_span"><label for="chmail">输入邮箱：</label></span>
                              <input id="chmail"    style="width:260px" type="text" onblur="checkm(this.value)"/>
                              <p class="getyz" >获取验证码</p>
                               <span id="maillpw" class="userphone" style="margin-left:190px;display:none"></span>
                           
                           </li>
                           <li>
                               <span  class="back_span"><label for="yanzheng">验证码：</label></span>
                               <input id="yanzheng"  style="width:100px;" type="text" onblur="yam(this.value)" />
                               <span id="yanumber" class="userphone" style="margin-left:190px;display:none"></span>
                           </li>
                           <div class="btn_div">
                                <a class="save "  href="#" style="width:80px;margin-left:50px;"  onclick="next()">下一步</a>
                             <!--   <a class="back " type="button" href="#" >返回</a>-->
                           </div> 
                         </ul>
                      </div> 
                        
                        <!--密码找回成功-->  
                      <div class="pw_account" style="display:none">    
                         <div class="tis">
                           <span> <img  src="<%=path%>/res/images/over.png" style="width:80px;"></span>
                           <span style="font-size:15px;"><font style="color:#f90;font-weight:600;">操作成功</font> 密码已经发送至您的手机或者邮箱请注意查收</span>
                         
                         </div>
                         <div class="btn_div">
                            <a class="save "  href="login.html" style="margin-left:70px;margin-top:20px;" >关闭</a>
                            <!--<a class="back " type="button" href="#" >返回</a>-->
                         </div>   
  
                     </div>
             </div>    
    </div>
</div>
</body>
</html>