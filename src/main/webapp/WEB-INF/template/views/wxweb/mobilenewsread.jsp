<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width,user-scalable=no" name="viewport">
<title>${news.title}</title>
<link rel="stylesheet" type="text/css"  href="<%=path%>/res/css/common/style.css" />
<link rel="stylesheet" type="text/css"  href="<%=path%>/res/css/web/login.css" />
<script type="text/javascript" src="<%=path %>/res/js/common/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/res/js/web/login.js"></script>
<style type="text/css">
	#id_description p{
		font-size: 16px;
		letter-spacing:2px;
		line-height:22px;
		font-family: "Microsoft YaHei" ! important;
		color: #3e3e3e;
	}
	#id_description img{
		margin-top: 2px;
		border-radius:5px; 
	}
	.msg-item{
		text-align:left;
		margin:10px 5px;
		background-color: #eeeeee;
	}
</style>
</head>
	
<body style="margin: 0px;padding:10px; background-color: #eeeeee;">
		<section style="width:100%;background-color: #eeeeee;">
			<div class="msg-item">
				<div style="text-align:left;font-size:18px;font-weight:bold;">
					${news.title}
				</div>
				<div style="margin-top:8px;text-align:left;font-size:14px;color:#6f6f6f;margin-right:10px;">
					<span>${news.createtime?string("yyyy-MM-dd")}</span>
					<span style="margin-left:5px;color:#607fa6;font-size:12px;">jeeweixin</span>
				</div>
				
				<div style="margin:15px 0px;">
					<c:if test="${news.showpic != null and news.picpath != null}">
						<img src="${news.picpath}" style="width:100%;">
					</c:if>
				</div>
				
				<div id="id_description" style="margin-top:10px;">
					<div>${news.description}</div>
				</div>
				
				<div style="height:30px;"></div>
			</div>
		</section>
		<script type="text/javascript">
			$(document).ready(function(){
				var imgs = $("#id_description").find("img");
				for(var i =0 ; i<imgs.length; i++){
					var src = $(imgs[i]).attr('src');
					if(src.indexOf("kindeditor/plugins/emoticons") == -1 && src.indexOf(".gif") == -1){
						$(imgs[i]).attr('style','');
						$(imgs[i]).css('width','100%');
						$(imgs[i]).attr('width','100%');
						$(imgs[i]).css('margin-top','2px;');
					}
				}
				try{
					wxbridge('${news.picpath}','${news.url}','${news.title}','${news.title}','');
				}catch(e){}
			});
		</script>
</body>
</html>
