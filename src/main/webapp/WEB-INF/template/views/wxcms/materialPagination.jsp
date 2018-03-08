<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>永久素材管理</title>
<link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/URL_Token.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/top_left.css" rel="stylesheet">
<link href="<%=path%>/res/css/web/materialMess.css" rel="stylesheet">

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
                <li><h6>首页</h6><h6>></h6><h6>永久素材管理</h6></li>
            </ul>
        </div>
       
       
        <div class="rightInfo">
        	<h2 class="infoTitle">永久素材管理</h2>
			<p class="warning">
            	<img src="<%=path%>/res/images/warnIcon_03.png">
            	分页管理、同步微信公众账号的永久素材(这里管理的是永久图文素材)  
            </p>
			
            <div>
            永久素材管理参考：WxApiCtrl.syncMaterials 方法 
            </div>
            <div class="mateMessInfo">
            	<div>
                	<input type="button" value="上传永久素材" class="materialUp"  onclick="window.location.href='<%=path%>/wxcms/toUploadMaterial'">
                </div>
				<table>
                	<thead>
                    	
                    	<tr>
                        	<td class="firstTd">
                            	<span class="tableIcon"><img src="<%=path%>/res/images/title_icon.png"></span>
                                <h2 class="tableTitle">标题</h2>
                            </td>
                        	<td class="secondTd">
                            	<span class="tableTitle"><img src="<%=path%>/res/images/author_icon.png"></span>
                                <h2 class="tableTitle">作者</h2>
                            </td>
                        </tr>
                    </thead>
                    <tbody>
                          <c:forEach var="row" items="${pagination.items}" varStatus="status">
                    	<tr>
                        	<td><a target="_blank" href="${row.url}">${row.title}</a></td>
                        	<td>${row.author}</td>
                        </tr>
                    	</c:forEach>
                    	<tr>
                        	<td colspan="4">
                            	<div class="page">
									<input type="button" value="首页" class="homePage">
                                    <input type="button" value="上一页" class="prePge">
                                    <label>第<span>1</span>/<span>1</span>页</label>
                                    <input type="button" value="下一页" class="nextPage">
                                    <input type="button" value="尾页" class="lastPage">                              
                                </div>
                            </td>
                        </tr>
                    	
                        
                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>
</body>
</html>
