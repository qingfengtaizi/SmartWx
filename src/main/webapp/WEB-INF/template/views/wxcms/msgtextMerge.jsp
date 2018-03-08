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

<script src="<%=path%>/res/js/common/jquery.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>
<script src="<%=path%>/res/js/web/table.js"></script>
		
		<script type="text/javascript">
				function doDelete(id,baseId){
					if(confirm("确定删除?")){
						window.location.href='<%=path%>/msgtext/delete?id='+id+'&baseId='+baseId
					}
				}
		</script>
</head>
<!--引入自定义js-->
<script src="<%=path%>/res/js/web/msgtext.js"></script>	
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
                   <h6><a href="<%=path%>/msgtext/list">文本管理</a></h6>
                   <h6>></h6>
                   <h6>文本消息编辑</h6>
                </li>
            </ul>
        </div>
       
       
        <div class="rightInfo">
        
          <h2 class="infoTitle">文本消息</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
            	文本消息管理
            </p>
            
           <div class="block-content" >
             <div class="content" style=" ">
				<div class="block-content-nav">
					文本消息<span class="title"> 添加/修改</span>
				</div>
				
				<div class="block-content-description">
					<span>
						如果 <span style="color:#EDA776;">关键字 </span> 
						为 <span style="color:#EDA776;">subscribe</span> ，那么此消息为订阅消息
					</span>
				</div>
				<!-- 
				
				 
				 -->
				<div class="block-content-content">
					<form class="fm-form" action="" method="post">
						<input type="hidden" name="id" id="id" value="${entity.id}"/>
						<input type="hidden" name="baseId" id="baseId" value="${entity.baseId}"/>
						<ul>
							<li style="margin-top:30px;">
								<div class="bor">
								    <label for="gjianz">关键字 </label>
							    </div>
								<input style="width:30%;" id="gjianz" 
								name="inputcode" type="text" 
								maxlength="10"
								value="${baseEntity.inputcode}" onblur="checkCode(this)"/>
								<span id="code_tip" style="color:red"></span>
							</li>
							<li style="margin-top:30px;">
								<div class="bor"><label for="textmain">内容 </label></div>
								<textarea  id="textmain" style="width:30%;" name="content"
								   rows="10" cols="30" onblur="checkContent(this)">${entity.content}</textarea>
								<span id="content_tip" style="color:red"></span>
							</li>
							
						</ul>
						
						<div style="margin-top: 20px;margin-left:110px;">
							<input class="btn" onclick="checkMsgText()" 
							style="background:#1A9DA7;color:#fff;" type="button" value="保 存"/>
						</div>
			     </form>
				</div>
			  </div>	
			</div>
			
		</div>
        </div>	
    </div>

</body>
</html>
