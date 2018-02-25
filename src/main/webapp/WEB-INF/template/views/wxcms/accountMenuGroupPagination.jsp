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
<link href="<%=path%>/res/css/web/menuMess.css" rel="stylesheet">

<script src="<%=path%>/res/js/common/jquery.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>
<script src="<%=path%>/res/js/web/table.js"></script>
		
		<script type="text/javascript">
		        //删除本地菜单
				function doDeleteLocal(id,enable){
		        	if(enable == '1'){
		        		alert("是正在使用的微信账号菜单不能删除");
		        	}else{
		        		if(confirm("确定删除本地菜单?")){
		        			//同步访问
							var result = "";
							$.ajax({
								url:path + '/wxcms/accountMenuGroup/deleteMenu',
							    type:'POST',
							    async: false,
							    dataType:'json',
							    data:{"id":id},
							    success:function(msg){
							    	result = msg;
							    },
							    error:function(msg){
							    	alert("删除本地菜单异常");
							    }
							});

						    if(result == "1"){
						       alert("删除本地菜单成功");
							   window.location.href = path+"/wxcms/accountMenuGroup/paginationEntity";
							}else{
						       alert("删除本地菜单失败");
						       window.location.href = path+"/wxcms/accountMenuGroup/paginationEntity";
						    }
						}
		        	}
				}
				function doDelete(id){
					if(confirm("确定删除菜单?")){
						window.location.href='<%=path%>/wxcms/accountMenuGroup/delete?id='+id;
					}
				}
				function doPublish(){
					var gid = $('input:radio[name=radio_name]:checked').val();
					if(gid != null && gid != 'undefined'){
						if(confirm("确定生成微信账号菜单?")){
							window.location.href='<%=path%>/wxapi/publishMenu?gid='+gid;
						}
					}else{
						alert("请选择菜单组");
					}
				}
				
				function doCancel(){
					if(confirm("确定删除微信账号菜单?")){
						window.location.href='<%=path%>/wxapi/deleteMenu';
					}
				}
				
				//删除微信菜单
				function doCancelPublishMenu(){
					if(confirm("确定删除微信账号菜单?")){
						//同步访问
						var result = "";
						$.ajax({
							url:path + '/wxapi/deletePublicMenu',
						    type:'GET',
						    async: false,
						    dataType:'text',
						    data:null,
						    success:function(msg){
						    	result = msg;
						    },
						    error:function(msg){
						    	alert(msg);
						    }
						});

					    if(result == "1"){
					       alert("删除微信账号菜单成功");
						   window.location.href = path+"/wxcms/accountMenuGroup/paginationEntity";
						}else{
					       alert(result);
					    }
						
					}
				}
				
				
				
				//添加菜单
				function doPublishMenu(){
					var gid = $('input:radio[name=radio_name]:checked').val();
					if(gid != null && gid != 'undefined'){
						if(confirm("确定生成微信账号菜单?")){
							//确定生成
							doSaveMenu(gid);
						}
					}else{
						alert("请选择菜单组");
					}
				}
				//跳转到后台保存
				function doSaveMenu(gid){
					//同步访问
					var result = "";
					$.ajax({
						url:path + '/wxapi/doPublishMenu',
					    type:'POST',
					    async: false,
					    dataType:'text',
					    data:{"gid":gid},
					    success:function(msg){
					    	result = msg;
					    },
					    error:function(msg){
					    	alert(msg);
					    }
					});

				    if(result == "1"){
				       alert("微信账号菜单成功");
					   window.location.href = path+"/wxcms/accountMenuGroup/paginationEntity";
					}else{
				       alert(result);
				    }
				}
		</script>
</head>

<style type="text/css">
.cannotDeleteMenu {
    background: RGB(145,145,145) none repeat scroll 0 0;
    height: 35px;
    margin: 20px 10px;
    width: 90px;
}
</style>
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
                <li><h6>首页</h6><h6>></h6><h6>菜单管理</h6></li>
            </ul>
        </div>
       
       
        <div class="rightInfo">
        	<h2 class="infoTitle">菜单管理</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
            	每个账号可以创建多套菜单，根据不同情况选择不同的菜单 
            </p>
			
            <div class="txtImgInfo">
				<table>
                	<thead>
                    	
                    	<tr>
                        	<td class="isNotTd"></td>
                        	<td class="firstTd">
                            	<span class="tableIcon"><img src="<%=path%>/res/images/menuName_icon.png"></span>
                                <h2 class="tableTitle">菜单组名称</h2>
                            </td>
                        	<td class="secondTd">
                            	<span class="tableTitle"><img src="<%=path%>/res/images/isnot_icon.png"></span>
                                <h2 class="tableTitle">是否在用</h2>
                            </td>
                        	<td class="thirdTd">
                            	<input type="button" onclick="window.location.href='<%=path%>/wxcms/accountMenuGroup/toMerge.html'"  class="addBtn" value="添加">                               
                            </td>
                        </tr>
                    </thead>
                    <tbody>
                       <c:forEach var="item" items="${pagination.items}" varStatus="status">
                    	<tr>
                        	<td class="isNotTd"><input type="radio" name="radio_name" value="${item.id}"/></td>
                        	<td>${item.name} </td>
                            <td><c:choose>
									   <c:when test="${item.enable == 1}">
									       <span style="color:green;">是</span>
									   </c:when>
									   <c:otherwise>
									               否
									   </c:otherwise>
									</c:choose>
							</td>
                            <td>
                            	
								<input type="button" class="deleteBtn" value="删除" onclick="doDeleteLocal('${item.id}','${item.enable}')"/>  
				                <input type="button" class="editeBtn" value="修改" onclick="window.location.href='<%=path%>/accountmenu/list?gid=${item.id}'"/>  
				                
				                <c:choose>
								   <c:when test="${item.enable == 1}">
								      <!-- 正在使用的菜单可以使用，但是不能再创建了 -->
								      <input type="button" value="生成微信菜单" disabled="disabled" class="cannotDeleteMenu">
								      <input type="button" value="删除微信菜单" onclick="doCancelPublishMenu();" class="deleteMenu">
								   </c:when>
								   <c:otherwise>
								      <!-- 没有使用的菜单，可以创建但是不能删除 -->
								      <input type="button" value="生成微信菜单" onclick="doPublishMenu();" class="creatMenu">
								      <input type="button" value="删除微信菜单" disabled="disabled" class="cannotDeleteMenu">
								   </c:otherwise>
								</c:choose>                                                  
                            </td>
                        </tr>
                        </c:forEach>
                        <!-- 
                          <tr>
                        	<td colspan="4">
                            	<input type="button" value="生成菜单" onclick="doPublishMenu();" class="creatMenu">
                                <input  type="button" value="删除菜单" onclick="doCancel();" class="deleteMenu">
                            </td>
                          </tr>

                         -->
                        
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>
</body>
</html>
