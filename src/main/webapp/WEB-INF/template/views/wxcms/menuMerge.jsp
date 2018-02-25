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
<script src="<%=path%>/res/js/common/jquery-ui.min.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>
<script src="<%=path%>/res/js/web/table.js"></script>

<script type="text/javascript" src="<%=path %>/res/js/plugins/kindeditor/kindeditor-min.js"></script>

		
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
		function doDelete(id){
			if(confirm("确定删除?")){
				window.location.href='<%=path%>/accountmenu/delete?id='+id+'&gid=${groupEntity.id}';
			}
		}
		function doBack(){
			window.location.href='<%=path%>/wxcms/accountMenuGroup/paginationEntity';
		}
</script>
</head>
	
<body>

<div class="top">
	<jsp:include page="/WEB-INF/template/views/common/top.jsp"></jsp:include>
</div>
<div class="main" >
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
            	菜单管理
            </p>

           <div class="block-content" >
              <div class="content" style=" ">
				<div class="block-content-nav">
					菜单<span class="title"> 添加/修改 </span>
				</div>
				
				<div class="block-content-description">
					<span>
						微信公众账号的菜单管理：可创建最多3个一级菜单，每个一级菜单下可创建最多5个二级菜单
					</span>
				</div>
				<div class="block-content-content">
				   		<div id="id_msgs" class="layer">
							<iframe id="id_msgs_frame" style="width:100%;height:100%;border:none;" src="<%=path%>/msgbase/menuMsgs">
					    	</iframe>
						</div>
					<form class="fm-form" action="<%=path%>/accountmenu/doMerge" method="post" onsubmit="return doSubmit();">
						<input type="hidden" value="${gid}" name="gid"/>
						<ul>
							<li style="margin-top:20px;">
								<div class="bor"><label >名称 </label></div>
								<input id="id_name"  style="width:30%;" type="text" name="name" value="">
								<span style="color:red">*</span>
							</li>
							<li  style="margin-top:20px;">
								<div class="bor"><label >一级菜单 </label></div>
								<select name="parentid" style="width:30%;height:35px;">
									<option value="0"> -- </option>
									<c:if test="${parentMenu != null}">
									    	<c:forEach var="row" items="${parentMenu}" varStatus="status">
											     <option value="${row.id}">${row.name}</option>
											</c:forEach>
									</c:if>
								</select>
							</li>
							<li style="margin-top:20px;">
								<div class="bor"><label >顺序 </label></div>
								<select name="sort" style="width:30%;height:35px;">
									<option value="1" >1</option>
									<option value="2" >2</option>
									<option value="3" >3</option>
									<option value="4" >4</option>
									<option value="5" >5</option>
									<option value="6" >6</option>
									<option value="7" >7</option>
									<option value="8" >8</option>
								</select>
							</li>
							<li style="margin-top:20px;">
								<div class="bor"><label >菜单类型 </label></div>
								<select id="id_type" name="mtype" style="width:30%;height:35px;" onchange="typeChange()">
									<option value="click" >消息</option>
									<option value="view" >链接</option>
								</select>
								<span class="gray-span" style="display:block;margin-top:5px;">消息：点击菜单时回复消息；链接：点击菜单打开链接</span>
							</li>
							
							<li style="margin-top:20px;" id="id_msg">
								<div class="bor"><label >消息类型 </label></div>
								<select id="id_event_type" name="eventType" style="width:30%;height:35px;" onchange="eventTypeChange()">
									<option value="key">关键字</option>
									<option value="fix">指定</option>
								</select>
								<br/>
								<div id="id_keymsg" style="margin-top:45px;">
									<div class="bor"><label style="width: 100px;">关键字 </label></div>
									<input type="text" name="inputcode" style="width:30%;margin-left；-5px;" />
									<span class="gray-span" style="display:block;padding-top:5px;">消息的关键字</span>
								</div>
								<div id="id_fixmsg" style="display: none;margin-top:45px;">
									<div class="bor"><label style="width: 100px;">指定消息 </label></div>
									<input type="text" id="id_msgIds" name="msgId" style="width:30%;margin-left；-5px;" readonly="readonly"/>
									<input type="button" class="btn" value="选择" style="background:#ccc;margin-left:5px;" onclick="getMsgs();">
								</div>
							</li>
							
							<li id="id_view" style="display: none;">
								<div class="bor"><label style="width: 100px;">链接URL </label></div>
								<input id="id_url" type="text" name="url" style="width:30%;height:35px;" >
								<span style="color:red">*</span>
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
<script type="text/javascript">
$(document).ready(function(){
    $("#id_msgs").hide();
});
			function typeChange(){
				var value = $("#id_type  option:selected").val();
				if(value == 'click'){
					$("#id_view").css("display","none")
					$("#id_msg").css("display","inline")
				}else{
					$("#id_view").css("display","inline")
					$("#id_msg").css("display","none")
				}
			}
			
			function eventTypeChange(){
				var value = $("#id_event_type  option:selected").val();
				if(value == 'key'){
					$("#id_keymsg").css("display","block")
					$("#id_fixmsg").css("display","none")
				}else{
					$("#id_keymsg").css("display","none")
					$("#id_fixmsg").css("display","block")
				}
			}
			
			function doSubmit(){
				var name = $("#id_name").val();
				if(name.replace(/(\s*$)/g, '') == ''){
					alert('菜单名称不能为空');
					return false;
				}
				return true;
			}
			
			function getMsgs(){
				$('#id_msgs').dialog({
					title:'选择消息',
			        width: 600,
			        height:400,
			        modal: true,
			        buttons: {
			            "确定": function() {
			            	var msgtype = $("#id_msgs_frame").contents().find('input[name="msgtype"]').val();
			            	if(msgtype == 'news'){
			            		var val = [];
			            		$("#id_msgs_frame").contents().find('input[name="checkname"]:checked').each(function(){ 
			            			val.push($(this).val())
			                    })
			            		if(val.length > 0){
				            		$("#id_msgIds").val(val.join(','));
				            	}
			            	}else{
			            		var val = $("#id_msgs_frame").contents().find('input[name="radioname"]:checked').val();
			            		if(val != undefined){
				            		$("#id_msgIds").val(val);
				            	}
			            	}
			                $(this).dialog('close');
			            }
			        }
			    });
				
					
					$(".ui-dialog-titlebar-close").html("X")
					
					
				
			}
			
			
					
</script>
</html>
