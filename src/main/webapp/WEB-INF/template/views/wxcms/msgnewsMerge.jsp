<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图文消息</title>
<link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/URL_Token.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/top_left.css" rel="stylesheet">

<script src="<%=path%>/res/js/common/jquery.js"></script>
<script type="text/javascript" src="<%=path%>/res/js/plugins/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="<%=path %>/res/js/plugins/kindeditor/zh_CN.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>
<script src="<%=path%>/res/js/web/table.js"></script>
		
<script type="text/javascript" >
	var editor = simpleKindeditor("description");
	
	function simpleKindeditor(name){
		var editor = null;
		KindEditor.ready(function(K) {
			_uploadJson = '<%=path %>/wxcms/ckeditorImage';
			editor = K.create('textarea[name="'+name+'"]', {
				resizeType:1,
				minWidth:300,
				allowPreviewEmoticons:false,
				allowImageUpload : true,
				uploadJson:_uploadJson,
				items : ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				         'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				         'insertunorderedlist','|','image', 'link','source']
			});
		});
		return editor;
	};
</script>

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
                <li><h6>首页</h6><h6>></h6><h6>图文消息</h6></li>
            </ul>
        </div>
        <div class="rightInfo">
        	<h2 class="infoTitle">图文消息</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
            	图文消息管理
            </p>

           <div class="block-content" >
              <div class="content" style=" ">
				<div class="block-content-nav">
					图文消息<span class="title"> 添加/修改</span>
				</div>
				
				<div class="block-content-description">
					<span>
						如果 <span style="color:#EDA776;">关键字</span> 为 <span style="color:#EDA776;">subscribe</span> ，那么此消息为订阅消息 
					</span>
				</div>
				<div class="block-content-content">
					<form class="fm-form" action="<%=path%>/msgnews/doMerge" method="post"  enctype="multipart/form-data">
						<input type="hidden" name="id" value="${entity.id}"/>
						<input type="hidden" name="baseId" value="${entity.baseId}"/>
                        <ul>
							<li style="margin-top:30px;">
								<div class="bor"><label for="gjianz">关键字 </label></div>
								<input style="width:30%;" name="inputcode" id="gjianz" type="text" value="${baseEntity.inputcode}"/>
							</li>
							<li style="margin-top:30px;">
								<div class="bor" ><label for="writer">作者 </label></div>
								<input id="writer" style="width:30%;" name="author" type="text" value="${entity.author}"/>
							</li>
							<li style="margin-top:30px;">
								<div class="bor" ><label for="biaot">标题 </label></div>
								<input  id="biaot" style="width:30%;" name="title" type="text" style="width:452px;" value="${entity.title}"/>
							</li>

							<li style="margin-top:30px;">
								<div class="bor"><label for="c_main">简介 </label></div>
								<textarea id="c_main" name="brief" rows="5" cols="60" style="width:30%;">${entity.brief}</textarea>
								<span class="helptext">长度 &lt; 100 字</span>
							</li>

							<li style="margin-top:30px;">
								<div class="bor"><label>封面图片 </label></div>
								<input type="file" style=" " name="imageFile"/>
								<label style="width:100%;margin-left:110px; "> 
								   <c:choose>
									   <c:when test="${entity.showpic != '' and entity.showpic == 1}">
									        <input name="showpic"  checked="checked"  value="1" type="checkbox" 
								                     style="vertical-align:middle;margin-right:5px;"/>
								            <span>显示图片</span>
									   </c:when>
									   <c:otherwise>
									       <input name="showpic" value="0" type="checkbox" 
								                  style="vertical-align:middle;margin-right:5px;"/>
								           <span>显示图片</span>
									   </c:otherwise>
									</c:choose>
								</label>
								<c:if test="${entity.picpath !='' and entity.picpath !=null}">
								   <img src="${entity.picpath}" alt="" 
								      style="border:none;max-width:300px;height:100px;display:block;margin-left:110px;"/>
								</c:if>
								
							</li>

							<li style="margin-top:30px;">
								<div class="bor"><label>内容 </label></div>
								<span style="color:#ccc;"> 如果填写了下方的 <span style="color:#EDA776;">原文链接</span> ，内容可以不填写，微信中点击消息，自动跳转到原文链接</span>
							</li>
							<li style=" ">
								<textarea name="description" style="width:452px;height:300px;visibility:hidden;">${entity.description}</textarea>
							</li>
							<li  style="margin-top:20px;">
								<div class="bor"><label>原文链接 </label></div>
								<input type="text" name="fromurl" value="${entity.fromurl}" style="width:30%;"/>
							</li>
						</ul>
						
						<div style="margin-top: 20px;margin-left:110px;">
							<input class="btn" style="background:#1A9DA7;color:#fff;" type="submit" value="保 存"/>
						</div>
					</form>
				</div>
			</div>
		 </div>
	</div>
</body>
</html>
