<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发送消息</title>
<link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/URL_Token.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/top_left.css" rel="stylesheet">

<script src="<%=path%>/res/js/common/jquery.js"></script>
<script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
<script src="<%=path%>/res/js/web/leftNav.js"></script>
<script src="<%=path%>/res/js/web/table.js"></script>
		
		
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
                <li><h6>首页</h6><h6>></h6><h6>发送消息</h6></li>
            </ul>
        </div>
       
       
        <div class="rightInfo">
        	<h2 class="infoTitle">发送消息</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
            	以给openid（粉丝）发送消息为例：客服消息、模板消息、群发消息。客服消息，请确保24小时之内给微信公众号发送过消息才有效。
因为给openid（粉丝）发送消息有一定的私密性，请关注“官方微信(weixinpy-com)”给自己发送消息体验。 
            </p>
			
            <style>
            .sendMessInfo h6{ font-size:15px; color:#000; margin-bottom:10px;}
            </style>
            <div class="sendMessInfo contentContainer">
            	<h6>以给openid发送消息为例(openid到粉丝管理信息中获取)</h6>
                <div class="content">
                <form action="<%=path%>/wxapi/sendCustomTextMsg" method="post">
	                   <table class="infoTable">
	                   		<thead>
	                        	<tr>
	                            	<td class="fInfo"></td>
	                                <td class="sInfo">客服消息请参考：客服消息请参考：WxApiCtrl.sendCustomTextMsg 方法</td>
	                                <td class="tInfo"></td>
	                            </tr>
	                        </thead>
	                        <tbody>
	                        	<tr>
	                            	<td class="fInfo"><span>openid</span></td>
	                            	
	                                <td class="sInfo">
										<input type="text" name="openid"/>
									</td>
	                                <td class="tInfo"><input type="submit" value="发送" class="creatCode btn"></td>
	                            </tr>
	                        </tbody>
	                   </table>
                   </form>
                   <form action="<%=path%>/wxapi/sendTemplateMessage" method="post">
	                   <table class="infoTable">
	                   		<thead>
	                        	<tr>
	                            	<td class="fInfo"></td>
	                                <td class="sInfo">模板消息请参考：WxApiCtrl.sendTemplateMessage 方法 </td>
	                                <td class="tInfo"></td>
	                            </tr>
	                        </thead>
	                        <tbody>
	                        	<tr>
	                            	<td class="fInfo"><span>openid</span></td>
	                                <td class="sInfo">
										  <input name="openid"  type="text"/>
	                                </td>
	                                <td class="tInfo"><input type="submit" value="发送" class="creatCode btn"></td>
	                            </tr>
	                        </tbody>
	                   </table>
                   </form> 
                   <form action="<%=path%>/wxapi/massSendTextMsg" method="post">        
	                   <table class="infoTable">
	                   		<thead>
	                        	<tr>
	                            	<td class="fInfo"></td>
	                                <td class="sInfo">群发文本消息(时间有延迟)：WxApiCtrl.massSendTextMsg 方法</td>
	                                <td class="tInfo"></td>
	                            </tr>
	                        </thead>
	                        <tbody>
	                        	<tr>
	                            	<td class="fInfo"><span>openid</span></td>
	                                <td class="sInfo">
										   <input name="openid" type="text"/>
	                                </td>
	                                <td class="tInfo"><input type="submit" value="发送" class="creatCode btn"></td>
	                            </tr>
	                        </tbody>
	                   </table>
                  </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
