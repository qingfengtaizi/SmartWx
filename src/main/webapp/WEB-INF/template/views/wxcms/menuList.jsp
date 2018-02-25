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

<script src="<%=path%>/res/js/common/jquery.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>
<script src="<%=path%>/res/js/web/table.js"></script>
		
	<script type="text/javascript">
		function doAdd(){
			var gid = $('#id_gid').val();
			if(gid != null && gid != ''){
				$('#id_menu_group_form').attr('action','<%=path%>/accountmenu/toMerge');
				$('#id_menu_group_form').submit();
			}else{
				alert('请先保存菜单组名称');
			}
		}
		function doSaveGroupMenu(){
			var name = $('#id_name').val();
			if(name!=null && name != ''){
				$('#id_menu_group_form').attr('action','<%=path%>/wxcms/accountMenuGroup/doMerge');
				$('#id_menu_group_form').submit();
			}else{
				alert('菜单组名称不能为空');
			}
		}
		
		//ajax方式保存菜单组名称
		function doSaveMenu(){
			
			var name = $('#id_name').val();
			if(name!=null && name != ''){
				//创建单图文对象
				var menuObj = new Object();
				menuObj.id = $("#id_gid").val();
				menuObj.name = name;
				menuObj.gid = $("#gid").val();
				menuObj.enable = $("#enable").val();
				//同步访问
				var result = "";
				$.ajax({
					url:path + '/wxcms/accountMenuGroup/doSaveMenu',
				    type:'POST',
				    async: false,
				    dataType:'json',
				    data:menuObj,
				    success:function(msg){
				    	result = msg;
				    },
				    error:function(msg){
				    	alert("保存异常");
				    }
				});

			    if(result == "1"){
			       alert("添加成功");
				   window.location.href = path+"/wxcms/accountMenuGroup/paginationEntity";
				}else if(result == "2"){
			       alert("更新成功");
			       window.location.href = path+"/wxcms/accountMenuGroup/paginationEntity";
			    }else{
			       alert("保存失败");
			    }
				
				
			}else{
				alert('菜单组名称不能为空');
			}
			
		}
		
		
		function doDelete(id){
			if(confirm("确定删除?")){
				window.location.href='<%=path%>/accountmenu/delete?id='+id+'&gid=${groupEntity.id}';
			}
		}
		
		//返回到原来页面
		function doBack(){
			window.location.href='<%=path%>/wxcms/accountMenuGroup/paginationEntity';
		}
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
                <li>
                    <h6>
                     <a href="<%=path%>/wxcms/accountMenuGroup/paginationEntity">菜单管理</a>
                   </h6>
                   <h6>></h6>
                   <h6>菜单组列表</h6>
               </li>
            </ul>
        </div>
        <div class="rightInfo">
        	<h2 class="infoTitle">菜单管理</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
            	菜单管理
            </p>

           <div class="block-content" >
				
				<div class="block-content-description">
					<span>
						请先填写 <span style="color:#EDA776;">菜单组名称</span> 并保存 ，<span style="color:#EDA776;">再添加菜单</span>
					</span>
				</div>
				<div class="block-content-content">
				   <form id="id_menu_group_form" action="<%=path%>/accountmenu/toMerge" method="post" >
						<input id="id_gid" name="id" type="hidden" value="${groupEntity.id}"/>
						<input name="gid" id="gid" type="hidden" value="${groupEntity.id}"/>
						<input name="enable" id="enable" type="hidden" value="${groupEntity.enable}"/>
						<ul>
							<li style="margin-top:30px;">
								<label style="width: 100px;">菜单组名称 </label>
								<input id="id_name" type="text" name="name" maxlength="10" 
								value="${groupEntity.name}">
								<span style="color:red">*</span>
								<input class="btn" style="background:#1A9DA7" onclick="doSaveMenu();" type="button" value="保 存"/>
								<input class="btn" style="background:#1A9DA7" onclick="doBack();" type="button" value="返回"/>
							</li>
						</ul>
					</form>
					<!-- 菜单组id -->
					<input type="hidden" id="gid" value="${gid}">
					<table class="fm-table" style="margin-top:30px;">
						<thead>
							<tr >
								<td rowspan="2" style="width: 100px;">名称</td>
								<td colspan="3" >消息类型</td>
								<td rowspan="2" style="width: 100px;">一级菜单</td>
								<td rowspan="2" style="width: 50px;">顺序</td>
								<td rowspan="2" style="width: 120px;">
									<input class="btn" onclick="doAdd();" type="button" style="background:#1A9DA7" value="添 加"/>
								</td>
							</tr>
							<tr>
							  <td>关键字消息</td>
							  <td>指定消息</td>
							  <td>链接消息</td>
							</tr>
						</thead>
						<tbody>
						    <c:forEach var="row" items="${page.list}" varStatus="status">
							 <tr > 
								<td>${row.name}</td>
								<td>${row.inputcode}</td>
								<td>${row.msgId}</td>
								<td>${row.url}</td>
								<td>${row.parentName}</td>
								<td>${row.sort}</td>
								<td>
									<a href="javascript:void(0);" onclick="doDelete('${row.id}')">删除</a>
								</td>
							  </tr>
							</c:forEach>
							 <tr>
	                            <td colspan="4">
	                            	<div class="page">
	                            	    <input type="button" value="首页" class="homePage"
	                            	       onclick="doPageOver('1','${page.pageSize}')">
						                <c:choose>
						                   <c:when test="${page.pageNum != 1}">
						                      <input type="button" value="上一页" class="prePge"
						                    onclick="doPageOver('${page.pageNum-1}','${page.pageSize}')">
						                   </c:when>
						                   <c:otherwise>
						                       <input type="button" value="上一页" class="prePge"
						                         onclick="doPageOver('${page.pageNum}','${page.pageSize}')">
						                   </c:otherwise>
						                </c:choose>
		                                <label>第
		                                   <span>
		                                     <a href="javascript:void(0);" 
		                                       onclick="doPageOver('${page.pageNum}','${page.pageSize}')">
		                                    ${page.pageNum}</a>
		                                   </span>/
		                                   <span>${page.pages}</span>页
		                                </label> 
						                            
						                <c:if test="${page.pageNum != page.pages}"> 
						                    <input type="button" value="下一页" class="nextPage" 
						                       onclick="doPageOver('${page.pageNum+1}','${page.pageSize}')"> 
						                </c:if>  
						                <input type="button" value="尾页" class="lastPage"
						                    onclick="doPageOver('${page.pages}','${page.pageSize}')">                  
	                                </div>
	                            </td>
	                        </tr>	
						</tbody>
					</table>
				</div>
			</div>
			<div class="clearfloat"></div>
	 </div>		
</div>
<script type="text/javascript">
//翻页函数
function doPageOver(pageNum,pageSize){
	var gid = $("#gid").val();
	window.location.href = path + "/accountmenu/list?gid="+gid+"&page="+pageNum+"&pageSize="+pageSize;
}
</script>
</body>
</html>
