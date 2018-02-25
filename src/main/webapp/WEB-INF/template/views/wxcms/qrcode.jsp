<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>生成参数二维码</title>
<link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/URL_Token.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/top_left.css" rel="stylesheet">

<script src="<%=path%>/res/js/common/jquery.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>
<script src="<%=path%>/res/js/web/table.js"></script>
		
		<script type="text/javascript">
			function doCreate(){
				var num = $('#num').val();
				if(!num){
					alert('请输入参数');
				}else{
					$('#queryPageForm').submit();
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
                <li><h6>首页</h6><h6>></h6><h6>生成参数二维码</h6></li>
            </ul>
        </div>
       
       
        <div class="rightInfo" >
        	<h2 class="infoTitle">生成参数二维码</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
            	公众账号创建带参数的二维码或者临时二维码实现场景需要。以创建临时参数二维码（60s）为例。  
            </p>
			
            <div class="twoCodeInfo contentContainer">
                <div class="content">
	                <c:choose> 
					  <c:when test="${not empty num}">   
					    <div>
							<span>参数：${num}</span>
						</div>
						<div style="margin: 5px 0px;">
							<span>永久二维码：</span>
						</div>
						<c:choose>
						   <c:when test="${not empty qrcode}">
						       	<div>
									<img src="<%=path%>/${qrcode}" alt="" style="width:200px;"/>
								</div>
								<div style="width:200px;text-align:center;">(扫一扫)</div>
						   </c:when>
						   <c:otherwise>   
							   <div>生成二维码失败</div>  
						   </c:otherwise> 
						</c:choose>
					  </c:when> 
					
					  <c:otherwise>   
					    <form id="queryPageForm" action="<%=path%>/wxapi/createQrcode" method="post">
		                   <table class="infoTable">
		                   		<thead>
		                        	<tr>
		                            	<td class="fInfo"></td>
		                                <td class="sInfo">生成临时二维码参考：WxApiCtrl.createQrcode 方法 </td>
		                                <td class="tInfo"></td>
		                            </tr>
		                        </thead>
		                        <tbody>
		                        	<tr>
		                            	<td class="fInfo"><span>请输入参数(数字)</span></td>
		                                <td class="sInfo"><input type="text" id="num" name="num" maxlength="8"></td>
		                                <td class="tInfo"><input type="button" value="生成临时二维码" class="creatCode" onclick="doCreate()"></td>
		                            </tr>
		                        </tbody>
		                     </table>
		                 </form>
					  </c:otherwise> 
					</c:choose>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
