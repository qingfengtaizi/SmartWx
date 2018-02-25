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
$(document).ready(function(){

	  
	  var cks = $(":checkbox:gt(0)"); 
      var fk = $(":checkbox:first").click(function(){
          cks.prop("checked", $(this).prop("checked"));
      });
      cks.click(function(){
          if(!$(this).prop("checked")){
              fk.prop("checked",false);
          }else{
              if(cks.filter(":not(:checked)").length == 0){
                  fk.prop("checked",true);
              }
          }
      });
});
</script>	
</head>
	
<body>

<div class="mcontent" style="width:530px;">
            <div style="text-align:left; width:530px;">
				<input type="hidden" id="id_msgtype" name="msgtype" value="news"/>
				<span id="id_news_span" class="btnc" style="width:150px;height:30px;text-align:center;">粉丝列表</span>
			</div>
			<div style="text-align:left;margin-top:20px;width:530px;">
				<div id="id_fans">
				    <h2 style="font-size:14px;font-weight:700">至少选择2个粉丝</h2>
					<div>
						<table class="fm-table">
						<thead>
							<tr style="height: 45px;">
							    <td style="width: 100px;">
							    <input type="checkbox" name="checkAll"/>
							                   全选
							    </td>
								<td style="width: 100px;">昵称</td>
								<td style="width: 100px;">性别</td>
								<td>省/市</td>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="row" items="${pagination.items}" varStatus="status">
						           <tr style="height:40px;background-color:#f9f9f9;">
						           		<td><input type="checkbox" value="${row.openId}" name="checkname"/></td>
										<td>${row.nicknameStr}</td>
										<td> 
											<c:choose>
											   <c:when test="${row.gender == 1}">
											                 男
											   </c:when>
											   <c:otherwise>
											                 女 
											   </c:otherwise>
										    </c:choose>
									   </td>
									   <td> 
											${row.province}-${row.city}
									   </td>
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
