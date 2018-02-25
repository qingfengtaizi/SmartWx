<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/URL_Token.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/top_left.css" rel="stylesheet">
<link href="<%=path%>/res/css/common/jquery-ui.css" rel="stylesheet">


<script src="<%=path%>/res/js/common/jquery.js"></script>
<script src="<%=path%>/res/js/common/jquery-ui.min.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>
<script src="<%=path%>/res/js/web/table.js"></script>
		
<script type="text/javascript">
	$(document).ready(function(){
	    $("#id_fans").hide();
	});
	function doDelete(id,baseId){
		if(confirm("确定删除?")){
			window.location.href='<%=path%>/msgnews/delete?id='+id+'&baseId='+baseId
		}
	}
	//弹出粉丝列表进行选择
	function getFans(currrentTextId){
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
	                	initSendParam(currrentTextId,fansOpenIds);
	                }
	                
	            }
	        }
	    });
			
	    $(".ui-dialog-titlebar-close").html("X");
	    
	}
	//发送
	function initSendParam(currrentTextId,fansOpenIds){
		var paramObj = new Object();
		paramObj.newsId = currrentTextId;
		paramObj.openIds = fansOpenIds;
		
		//同步访问
		var result = "";
		$.ajax({
			url:path + '/wxapi/massSendNewsByOpenIds',
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
	
	//测试上传图片
    function doUpload(currrentNewId){
		//同步访问
		var result = "";
		var paramObj = new Object();
		paramObj.newsId = currrentNewId;
		$.ajax({
			url:path + '/msgnews/sendNewsMaterial',
		    type:'POST',
		    async: false,
		    dataType:'json',
		    data:paramObj,
		    success:function(msg){
		    	result = msg;
			    if(result == "" || result == "-1"){
			        alert("同步失败");
			 	}else if(result == "1"){
			 		 alert("同步成功");
			 	}else{
			 		alert("{errcode:"+result.errcode+" , "+result.errmsg+"}");
			 	}
		    },
	        error: function (errormsg) {
	            alert("同步异常");
	        }
		});

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
                <li><h6>首页</h6><h6>></h6><h6>图文消息</h6></li>
            </ul>
        </div>
        <div class="rightInfo">
        	<h2 class="infoTitle">图文消息</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
            	图文消息管理(图文消息同步到微信后才能群发)
            </p>
            <!-- 图文文本信息的选择 -->
			<div id="id_fans" class="layer">
				<iframe id="id_fans_frame" style="width:100%;height:100%;border:none;" src="<%=path%>/accountfans/fansSelect">
		    	</iframe>
			</div>
            <div class="txtImgInfo">
                <!-- fans的openId -->
                <input type="hidden" id="id_fans_openid">
				<table>
                	<thead>
                    	<tr>
                        	<td class="firstTd">
                            	<span class="tableIcon"><img src="<%=path%>/res/images/key_icon.png"></span>
                                <h2 class="tableTitle">关键词</h2>
                            </td>
                        	<td class="secondTd">
                            	<span class="tableTitle"><img src="<%=path%>/res/images/title_icon.png"></span>
                                <h2 class="tableTitle">标题</h2>
                            </td>
                        	<td class="thirdTd">
                            	<input type="button" class="addBtn"  onclick="window.location.href='<%=path%>/msgnews/toMerge'" value="添加">                               
                            </td>
                        </tr>
                    </thead>
                    <tbody>
                       <c:forEach var="row" items="${pageList}" varStatus="status">
                    	<tr>
                        	<td>${row.inputcode} </td>
                            <td>${row.title} </td>
                            <td>
                            	<input type="button" class="deleteBtn" value="删除" onclick="doDelete('${row.id}','${row.baseId}');"/>
								<input type="button" class="editeBtn" value="修改" onclick="window.location.href='<%=path%>/msgnews/toMerge?id=${row.id}'"/>
								<c:choose>
								   <c:when test="${not empty row.fromurl}">
								       <input type="button" class="viewBtn" value="预览"   onclick="window.location.href='${row.fromurl}'" />
								   </c:when>
								   <c:otherwise>
								       <input type="button" class="viewBtn" value="预览" onclick="window.location.href='${row.url}'" />
								   </c:otherwise>
								</c:choose>
								
								<c:choose>
								   <c:when test="${not empty row.mediaId}">
								      <input type="button" class="editeBtn" value="群发"   onclick="getFans('${row.id}');" />
								      <input type="button" class="viewBtn" value="同步"   disabled="disabled" />
								   </c:when>
								   <c:otherwise>
								      <input type="button" class="viewBtn" value="群发"  disabled="disabled" />
								      <input type="button" class="deleteBtn" value="同步"   onclick="doUpload('${row.id}');" />
								   </c:otherwise>
								</c:choose>
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
