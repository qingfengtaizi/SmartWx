<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单管理</title>
<link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/URL_Token.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/top_left.css" rel="stylesheet">
<link href="<%=path%>/res/css/common/jquery-ui.css" rel="stylesheet">



<script src="<%=path%>/res/js/common/jquery.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>
<script src="<%=path%>/res/js/web/table.js"></script>

<script type="text/javascript" src="<%=path %>/res/js/plugins/kindeditor/kindeditor-min.js"></script>

		
<script type="text/javascript">
	function showMsg(type){
		if(type == '1'){//news
			$('#id_msgtype').val('news');
			$('#id_news_msg').css('display','block');
			$('#id_text_msg').css('display','none');
			
			$('#id_news_span').attr('class','btnc');
			$('#id_text_span').attr('class','gray-btnc');
		}else{
			$('#id_msgtype').val('text');
			$('#id_news_msg').css('display','none');
			$('#id_text_msg').css('display','block');
			
			$('#id_text_span').attr('class','btnc');
			$('#id_news_span').attr('class','gray-btnc');
		}
	}
	
	

</script>
</head>
	
<body>

<div class="mcontent" style="width:530px;">
            <div style="text-align:left; width:530px;">
				<input type="hidden" id="id_msgtype" name="msgtype" value="news"/>
				<span id="id_news_span" class="btnc" style="width:150px;height:30px;text-align:center;" onclick="showMsg('1')" >图文消息</span>
				<span id="id_text_span" class="gray-btnc" style="width:150px;height:30px;text-align:center;" onclick="showMsg('2')" >文本消息</span>
			</div>
			
			<div style="text-align:left;margin-top:20px;width:530px;">
				<div id="id_news_msg">
					<div>
						<table class="fm-table">
						<thead>
							<tr style="height: 45px;">
								<td style="width: 100px;">图文消息</td>
								<td style="width: 100px;">ID</td>
								<td>标题</td>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="row" items="${newsList}" varStatus="status">
						           <tr style="height:40px;background-color:#f9f9f9;">
						           		<td><input type="checkbox" value="${row.baseId}" name="checkname"/></td>
										<td>${row.baseId}</td>
										<td>${row.title}</td>
							      </tr>
						</c:forEach>
						</tbody>
					</table>
					</div>
				</div>
				<div id="id_text_msg" style="display:none;">
					<h2 style="font-size:14px;font-weight:700">请选择 文本消息</h2>
					<div>
						<table class="fm-table">
							<thead>
								<tr style="height: 45px;">
									<td style="width: 100px;">文本消息</td>
									<td style="width: 100px;">ID</td>
									<td>描述</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="row" items="${textList}" varStatus="status">
								           <tr style="height:40px;background-color:#f9f9f9;">
								           		<td><input type="radio" value="${row.baseId}" name="radioname"/></td>
												<td>${row.baseId}</td>
												<td>${row.content}</td>
									      </tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
</div>
</body>

</html>
