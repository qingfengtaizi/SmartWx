<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<div class="block-nav">
	 <div style="height:10px;"></div>
		<ul class="nav-ul" style="border-bottom: none;margin-top:50px;">
			<li class="cur-nav">
				<span class="title">开发者公众账号接口</span>
			</li>
			<li>
			    <a href="<%=path%>/wxcms/urltoken">
				<span>URL 和  Token</span></a>
			</li>
			<li >
			    <a href="<%=path%>/msgtext/list">
				 <span>文本消息</span>
			    </a>
			</li>
			<li>
			   <a href="<%=path%>/msgnews/list">
				<span>图文消息</span>
			   </a>
			</li>
			<li>
			   <a href="<%=path%>/wxcms/accountMenuGroup/paginationEntity">
				<span>菜单管理</span>
			   </a>
			</li>
			<li >
			   <a href="<%=path%>/accountfans/paginationEntity">
				<span>粉丝管理</span></a>
			</li>
			<li >
			   <a href="<%=path%>/wxapi/syncMaterials">
				<span>永久素材管理</span></a>
			</li>
			<li >
			  <a href="<%=path%>/wxcms/qrcode">
				<span>生成参数二维码</span></a>
			</li>
			<li>
			  <a href="<%=path%>/wxcms/oauthInterceptor">
				<span>OAuth认证</span></a>
			</li>
			<li>
			  <a href="<%=path%>/wxcms/sendMsg">
				<span>发送消息</span></a>
			</li>
			<li>
			  <a href="<%=path%>/wxcms/jssdk">
				<span>JS-SDK接口</span></a>
			</li>
			<li>
			  <a href="<%=path%>/wxcms/weui">
				<span>WeUI</span>
			  </a>
			</li>
		</ul>
</div>