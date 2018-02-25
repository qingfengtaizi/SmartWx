<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>粉丝管理</title>
<link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/URL_Token.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/top_left.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/fansMess.css" rel="stylesheet">
<link href="<%=path%>/res/css/common/jquery-ui.css" rel="stylesheet">

<script src="<%=path%>/res/js/common/jquery.js"></script>
<script src="<%=path%>/res/js/common/jquery-ui.min.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>
<script src="<%=path%>/res/js/web/table.js"></script>
		
<script type="text/javascript">
$(document).ready(function(){
    $("#id_msgs").hide();
});
function doSync(){
	if(confirm("确定同步?")){
		window.location.href='<%=path%>/wxapi/syncAccountFansList';
	}
}
//弹出图文信息选择层			
function getMsgs(currentOpenid){
	//alert(currentOpenid);
	document.getElementById('id_msgs_frame').contentWindow.location.reload(true);
	$('#id_msgs').dialog({
		title:'选择消息',
        width: 600,
        height:400,
        modal: true,
        buttons: {
            "确定": function() {
            	var msgtype = $("#id_msgs_frame").contents().find('input[name="msgtype"]').val();
            	if(msgtype == 'news'){
            		//图文消息
            		var newsVal = $("#id_msgs_frame").contents().find('input[name="checkname"]:checked').val();
            		
            		if(newsVal != undefined){
	            		$("#id_msgIds").val(newsVal);
	            		//类型图文
	            		$("#id_msgType").val("news");
	            	}
            	}else{
            		//文本消息
            		var textVal = $("#id_msgs_frame").contents().find('input[name="radioname"]:checked').val();
            		if(textVal != undefined){
	            		$("#id_msgIds").val(textVal);
	            		//类型文本
	            		$("#id_msgType").val("text");
	            	}
            	}
                var msgId = $("#id_msgIds").val();
                if(!msgId){
                	alert("请选择消息");
                	return false;
                }else{
                	$(this).dialog('close');
                	
                	initSendParam(currentOpenid);
                }
                
            }
        }
    });
		
    $(".ui-dialog-titlebar-close").html("X");


}

function initSendParam(currentOpenid){
    
    var msgId = $("#id_msgIds").val();
   
   	var msgType = $("#id_msgType").val();
	var paramObj = new Object();
	paramObj.msgId = msgId;
	paramObj.openid = currentOpenid;
   	if(msgType == "text"){
   		doSendText(paramObj);
   	}
   	
   	if(msgType == "news"){
   		doSendNews(paramObj);
   	}
}
//发送文本消息
function doSendText(testObj){
	//同步访问
	var result = "";
	$.ajax({
		url:path + '/wxapi/sendTextMsgByOpenId',
	    type:'POST',
	    async: false,
	    dataType:'json',
	    data:testObj,
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

//发送图文消息
function doSendNews(newsObj){
	//同步访问
	var result = "";
	$.ajax({
		url:path + '/wxapi/sendNewsByOpenId',
	    type:'POST',
	    async: false,
	    dataType:'json',
	    data:newsObj,
	    success:function(msg){
	    	result = msg;
	    }
	});
	
    if(result == "1"){
        alert("发送成功");
 	}else{
 		alert("发送失败");
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
                <li>
                   <h6>
                      <a href="<%=path%>/wxcms/main?userId=${sessionScope.sysUser.id}">首页</a>
                   </h6>
                   <h6>></h6>
                   <h6>粉丝管理</h6>
                </li>
            </ul>
        </div>
       
       
        <div class="rightInfo">
        	<h2 class="infoTitle">粉丝管理</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
            	管理、同步微信公众账号的粉丝（认证公众号）  
            </p>
            <!-- 图文文本信息的选择 -->
			<div id="id_msgs" class="layer">
				<iframe id="id_msgs_frame" style="width:100%;height:100%;border:none;" src="<%=path%>/msgbase/msgselect">
		    	</iframe>
			</div>
            <div class="fansMessInfo">
                <!-- 文本或者图文消息 的openId -->
                <input type="hidden" id="id_msgIds">
                <input type="hidden" id="id_msgType">
				<table>
                	<thead>
                    	
                    	<tr>
                        	<td class="firstTd">
                            	<span class="tableIcon"><img src="<%=path%>/res/images/nick_icon.png"></span>
                                <h2 class="tableTitle">昵称</h2>
                            </td>
                        	<td class="secondTd">
                            	<span class="tableTitle"><img src="<%=path%>/res/images/sex_icon.png"></span>
                                <h2 class="tableTitle">性别</h2>
                            </td>
                        	<td class="thirdTd">
                            	<span class="tableTitle"><img src="<%=path%>/res/images/city_icon.png"></span>
                                <h2 class="tableTitle">省/市</h2>
                            </td>

                        	<td class="fourthTd">
                            	
                            	
									<input class="addBtn" onclick="doSync();" type="button" value="批量同步粉丝"/>
								                            
                            </td>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="row" items="${pagination.list}" varStatus="status">
                    	<tr>
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
                            <td>${row.province}-${row.city}</td>
                            <td>
                               <a style="text-decoration:none;" href="<%=path%>/wxapi/syncAccountFans?openId=${row.openId}">
									<input type="button"  class="editeBtn" value="同步粉丝信息"/>
							   </a>
							   <input type="button"  class="editeBtn" value="发送消息" onclick="getMsgs('${row.openId}');"/>
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
	window.location.href = path + "/accountfans/paginationEntity?page="+pageNum+"&pageSize="+pageSize;
}


</script>
</body>
</html>
