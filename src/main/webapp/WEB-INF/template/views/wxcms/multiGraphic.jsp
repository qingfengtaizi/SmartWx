<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>多图文素材管理</title>
<link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/URL_Token.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/top_left.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/multiGraphic.css" rel="stylesheet">
<link href="<%=path%>/res/css/common/jquery-ui.css" rel="stylesheet">

<script src="<%=path%>/res/js/common/jquery.js"></script>
<script src="<%=path%>/res/js/common/jquery-ui.min.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>
<script src="<%=path%>/res/js/web/table.js"></script>
		
<script type="text/javascript" src="<%=path%>/res/js/plugins/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="<%=path %>/res/js/plugins/kindeditor/zh_CN.js"></script>	

<script type="text/javascript" >
      
	  $(document).ready(function(){
		    $("#id_fans").hide();
	  });

	  
		//弹出粉丝列表进行选择
		function getFans(newsMediaId){
			//刷新iframe
			document.getElementById('id_fans_frame').contentWindow.location.reload(true);
			$('#id_fans').dialog({
				title:'选择粉丝',
		        width: 600,
		        height:400,
		        modal: true,
		        buttons: {
		            "确定": function() {
		           		var val = [];
		           		$("#id_fans_frame").contents().find('input[name="checkname"]:checked').each(function(){ 
		           			val.push($(this).val())
		                });
		           		//勾选的数组的长度
		           		var selectValLength = val.length;
		           		if(selectValLength > 1){
		           			$("#id_fans_openid").val(val.join(','));
		           		}else{
		           			$("#id_fans_openid").val("0");
		           		}
		            		
		                var fansOpenIds = $("#id_fans_openid").val();
		                if(!fansOpenIds || fansOpenIds=="0"){
		                	alert("至少选择2个粉丝");
		                	return false;
		                }else{
		                	$(this).dialog('close');
		                	initSendParam(newsMediaId,fansOpenIds);
		                }
		                
		            }
		        }
		    });
				
		    $(".ui-dialog-titlebar-close").html("X");
		    
		}
		
		//发送
		function initSendParam(newsMediaId,fansOpenIds){
			var paramObj = new Object();
			paramObj.mediaId = newsMediaId;
			paramObj.openIds = fansOpenIds;
			
			//同步访问
			var result = "";
			$.ajax({
				url:path + '/wxapi/sendMaterialByOpenIds',
			    type:'POST',
			    async: false,
			    dataType:'json',
			    data:paramObj,
			    success:function(msg){
			    	result = msg;
			    	if(result == ""){
			    		alert("发送失败");
				 	}else if(result == "1"){
				 		alert("发送成功");
				 	}else{
				 		alert("{errcode:"+result.errcode+" , "+result.errmsg+"}");
				 	}
			    },
		        error: function (errormsg) {
		            alert("发送失败");
		        }
			});
			

		}
	  
      //添加单图文
      function doAddSingle(){
    	  window.location.href='<%=path%>/wxcms/toSingleNews';
      }
      //添加多图文
      function doAddMore(){
    	  window.location.href='<%=path%>/wxcms/toMoreNews';
      }
      
      function doTestImg(){
    	  window.location.href='<%=path%>/wxcms/test';
      }
      
     //**删除多图文消息
  	 function doDelete(mediaId){
		if(confirm("确定删除?")){
			//同步访问
			var result = "";
			$.ajax({
				url:path + '/msgnews/deleteMaterial',
			    type:'POST',
			    async: false,
			    dataType:'json',
			    data:{"mediaId":mediaId},
			    success:function(msg){
			    	result = msg;
			    },
			    error:function(msg){
			    	alert("保存异常");
			    }
			});

		    if(result == "1"){
		       //跳转到多图文素材管理列表
			   window.location.href = path+"/wxcms/toMultiGraphic";
			}
		    
		    if(result == "-1"){
		    	alert("保存失败");
		    }
			
		}
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
                <li><h6>
                    <a href="<%=path%>/wxcms/main?userId=${sessionScope.sysUser.id}">首页</a></h6>
                    <h6>></h6>
                    <h6>菜单管理</h6>
               </li>
            </ul>
        </div>
        <div class="rightInfo">
        	<h2 class="infoTitle">多图文素材管理</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
                                               图文列表
            </p>
            <!-- 图文文本信息的选择 -->
			<div id="id_fans" class="layer">
				<iframe id="id_fans_frame" style="width:100%;height:100%;border:none;" src="<%=path%>/accountfans/fansSelect">
		    	</iframe>
			</div>
           <div class="block-content" >
				
				<div class="block-content-description">
					<span>
					    <!-- 
					    <input class="btn" onclick="doTestImg();" type="button" style="background:#1A9DA7" value="测试图片上传"/>
					    
					     -->
					    
						<input class="btn" onclick="doAddSingle();" type="button" style="background:#1A9DA7" value="添 加单图文"/>
						<input class="btn" onclick="doAddMore();" type="button" style="background:#1A9DA7" value="添 加多图文"/>
					</span>
				</div>
				<div class="block-content-content">
				   <!-- fans的openId -->
                   <input type="hidden" id="id_fans_openid">
					<table class="fm-table" style="margin-top:30px;">
						<thead>
							<tr >
								<td  style="width: 100px;">标题</td>
								<td style="width: 100px;">作者</td>
								<td style="width: 50px;">简介</td>
								<td style="width: 120px;">
									操作
								</td>
							</tr>
						</thead>
						<tbody>
						   <c:forEach  var="media" items="${page.list}" varStatus="status">
						                <tr > 
											<td>
											<c:forEach var="news" items="${newsList}" varStatus="statusnew">
											    <ul>
											     <c:if test="${media.mediaId == news.mediaId}">
											            <li>
											                <a href="${news.fromurl}" target="_blank"> 
											                ${news.title}</a>
											            </li>
											     </c:if>
											    </ul>

						                    </c:forEach>
											</td>
											<td>
											   <c:forEach var="news" items="${newsList}" varStatus="statusnew">
											     <ul>
											       <c:if test="${media.mediaId == news.mediaId}">
											            <li> ${news.author}</li>
											       </c:if>
											     </ul>
						                       </c:forEach>
											</td>
											<td>
											   <c:if test="${media.mediaType == 'news'}">
													<c:forEach var="news" items="${newsList}" varStatus="statusnew">
												      <c:if test="${media.mediaId == news.mediaId}">
												             ${news.brief}
												      </c:if>
							                       </c:forEach>
						                       </c:if>
						                       <c:if test="${media.mediaType == 'more'}">
						                                                                                      无
						                       </c:if>
											</td>
											<td>
											    <input type="button" class="editeBtn" value="群发"   onclick="getFans('${media.mediaId}');" />
												<a href="javascript:void(0);" onclick="doDelete('${media.mediaId}')">删除</a>
												<c:if test="${media.mediaType == 'news'}">
											        <input type="button" class="viewBtn" value="修改"    
											                 onclick="window.location.href='<%=path%>/msgnews/toUpdateSingleNews?mediaId=${media.mediaId}'" />
											    </c:if>
											    
											    <c:if test="${media.mediaType == 'more'}">
											        <input type="button" class="viewBtn" value="修改"    
											                 onclick="window.location.href='<%=path%>/msgnews/toUpdateMoreNews?mediaId=${media.mediaId}'" />
											    </c:if>
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
</div>
<script type="text/javascript">

//翻页函数
function doPageOver(pageNum,pageSize){
	window.location.href = path + "/wxcms/toMultiGraphic?page="+pageNum+"&pageSize="+pageSize;
}


</script>
</body>
</html>
