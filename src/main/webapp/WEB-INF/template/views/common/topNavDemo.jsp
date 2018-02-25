<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>

<ul class="topNav">
	<li><a href="<%=path%>/wxcms/main?userId=${sessionScope.sysUser.id}">首页</a></li>
	<li><a href="<%=path%>/wxcms/urltoken?userId=${sessionScope.sysUser.id}">微信接口管理</a></li>
  <!-- <li><a href="javascript:void(0)">商品</a></li>
	<li><a href="javascript:void(0)">闲置</a></li>
	<li><a href="javascript:void(0)">店铺</a></li>
	<li><a href="javascript:void(0)">会员</a></li>
	<li><a href="javascript:void(0)">交易</a></li>
	<li><a href="javascript:void(0)">网站</a></li>
	<li><a href="javascript:void(0)">运营</a></li>
	<li><a href="javascript:void(0)">工具</a></li> -->
</ul>

<!--  nav文字样式的修改-->

<script type="text/javascript">
$(function(){
	var m=$(document).attr("title");
	
		if(m=="main"){
		
			$(".topNav a").eq(0).css("color","#f90");
			$(".topNav a").eq(1).css("color","#fff");
		}else{
			
			$(".topNav a").eq(0).css("color","#fff");
			$(".topNav a").eq(1).css("color","#f90");
		
		
	}
	
	
})

</script>