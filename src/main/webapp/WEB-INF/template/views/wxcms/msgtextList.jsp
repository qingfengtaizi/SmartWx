<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文本消息</title>
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
	var obj = new Object();
	obj.id = id;
	obj.baseId = baseId;
	if(confirm("确定删除?")){
		
		//同步访问
		var result = "";
		$.ajax({
			url:path + '/msgtext/deleteById',
		    type:'POST',
		    async: false,
		    dataType:'json',
		    data:obj,
		    success:function(msg){
		    	result = msg;
		    },
		    error:function(msg){
		    	alert("保存异常");
		    }
		});

	    if(result == "1"){
	       alert("删除成功");
		}else{
	       alert("删除失败");
	    }
	    window.location.href = path+"/msgtext/list";
	}
}
//弹出粉丝列表进行选择
function getFans(currrentTextId){
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
	paramObj.textId = currrentTextId;
	paramObj.openIds = fansOpenIds;
	
	//path + '/wxapi/batchSendText'客服接口
	//同步访问
	var result = "";
	$.ajax({
		url:path + '/wxapi/massSendTextByOpenIds',
	    type:'POST',
	    async: false,
	    dataType:'json',
	    data:paramObj,
	    success:function(msg){
	    	result = msg;
	    }
	});
	
    if(result == "1"){
        alert("发送成功");
 	}else{
 		alert("{errcode:"+result.errcode+" , "+result.errmsg+"}");
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
                    <a href="<%=path%>/wxcms/main?userId=${sessionScope.sysUser.id}">首页</a>
                    </h6>
                   <h6>></h6>
                   <h6>文本管理</h6>
                </li>
            </ul>
        </div>
       
       
        <div class="rightInfo">
        	<h2 class="infoTitle">文本消息</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
            	文本消息管理
            </p>
            <!-- 图文文本信息的选择 -->
			<div id="id_fans" class="layer">
				<iframe id="id_fans_frame" style="width:100%;height:100%;border:none;" src="<%=path%>/accountfans/fansSelect">
		    	</iframe>
			</div>
            <div class="textMessInfo">
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
                            	<span class="tableTitle"><img src="<%=path%>/res/images/detail_icon.png"></span>
                                <h2 class="tableTitle">消息描述</h2>
                            </td>
                        	<td class="thirdTd">
                            	<input type="button" class="addBtn" onclick="window.location.href='<%=path%>/msgtext/toMerge'" value="添加">                               
                            </td>
                        </tr>
                    </thead>
                    <tbody>
                      <c:forEach var="row" items="${page.list}" varStatus="status">
                    	<tr>
                        	<td>${row.inputcode} </td>
                            <td>${row.content} </td>
                            <td>
                                
                                <input  type="button" class="deleteBtn"  value="删除"  onclick="doDelete('${row.id}','${row.baseId}')">
								<input  type="button" class="editeBtn" value="修改" onclick="window.location.href='<%=path%>/msgtext/toMerge?id=${row.id}'" >
								
								<input  type="button" class="editeBtn"  value="群发" onclick="getFans('${row.id}')">
								
								<!-- <input  type="button" class="deleteBtn"  value="批量发送" style="width:70px;" onclick="getFans('${row.id}')"> -->
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
    </div>
</div>
<script type="text/javascript">

//翻页函数
function doPageOver(pageNum,pageSize){
	window.location.href = path + "/msgtext/list?page="+pageNum+"&pageSize="+pageSize;
}


</script>
</body>
</html>
