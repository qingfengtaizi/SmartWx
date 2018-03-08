<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>

	<div class="topLeft">
	   <p><h3 class="topTitle">微信管理平台</h3></p>
	   </div>
	   <div class="topRight">
	     <span class="topSearch">
	          <input type="text" placeholder="Search"></span>
	     <ul class="topPer">
	          <li class="left"><img src="<%=path%>/res/images/topImg_05.png" style="height:35px;"></li>
	          <li class="mid"><h6>${sessionScope.sysUser.account}</h6></li>
	          <li class="right"><img src="<%=path%>/res/images/topPer_icon_08.png"></li>
	     </ul>
	     <ul class="top_li" >
	          <li><a  href="<%=path%>/sysuser/mybaseinfo?userId=${sessionScope.sysUser.id}" style="color:#EFDD57;">个人详情</a></li>
	          <li><a  href="<%=path%>/sysuser/loginpwd?userId=${sessionScope.sysUser.id}" style="color:#EFDD57;">密码修改</a></li>
	          <li><a  href="<%=path%>/login/logout" style="color:#EFDD57;">退出</a></li>
	     </ul>
	</div>
  <script type="text/javascript">
    $(function(){
    	$(".right").hover(function(){
    		
    		$(this).parent().next(".top_li").toggle()
    		
    	});
    	$(".top_li").hover(function(){
    		
    		$(this).toggle();
    	})
    	
    	
    });
  
  
  </script>