<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/template/views/common/head.inc.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="renderer" content="webkit"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>URL和TOKEN</title>
    <link href="<%=path%>/res/css/common/bootstrap.min.css" rel="stylesheet">
    <link href="<%=path%>/res/css/web/initial.css" rel="stylesheet">
    <%--<link href="<%=path%>/res/css/web/URL_Token.css" rel="stylesheet">--%>
    <link href="<%=path%>/res/css/web/top_left.css" rel="stylesheet">

    <script src="<%=path%>/res/js/common/jquery.js"></script>
    <script src="<%=path%>/res/js/common/bootstrap.min.js"></script>
    <script src="<%=path%>/res/js/web/leftNav.js"></script>

    <script type="text/javascript">
        function dosubmit() {
            var account = $('#id_account').val();
            account = account.replace(/(\s*$)/g, '');
            if (account == '') {
                alert('请填写微信公众号ID');
                return false;
            } else {
                return true;
            }
        }
        function getUrl() {
            var account = $('#id_account').val();
            account = account.replace(/(\s*$)/g, '');
            if (account == '') {
                alert('请填写微信公众号ID');
                return false;
            } else {
                $('#id_url').val('url');
                $('#id_tocken').val('tocken');
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
                <li><h6><a href="#">首页</a></h6><h6>></h6><h6><a href="javascript:void(0)">URL和Token</a></h6></li>
            </ul>
        </div>


        <div class="rightInfo">
            <h2 class="infoTitle">URL 和 Token</h2>
            <p class="warning">
                <img src="<%=path%>/res/images/warnIcon_03.png">
                填写 公众号ID，点击 保存 按钮， 系统将自动生成 URL 和 Token 。将它们填写到公众平台 开发者中心 中，公众账号即可升级成为开发者账号
            </p>
            <form class="form-horizontal" action="<%=path%>/wxcms/getUrl" method="post" onsubmit="return dosubmit();">
                <input type="hidden" name="id" value="${account.id}"/>
                <div class="form-group form-inline">
                    <label for="id_account" class="col-sm-2 control-label">公众号ID</label>
                    <div class="col-sm-10">
                        <input id="id_account" class="form-control" name="account" type="text"
                               value="${account.account}">
                        <span class="tips">( * 字母或者数字)</span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="id_url" class="col-sm-2 control-label">URL</label>
                    <div class="col-sm-10">
                        <input type="text" id="id_url" class="form-control" readonly value="${account.url}" name="url">
                    </div>
                </div>
                <div class="form-group">
                    <label for="id_tocken" class="col-sm-2 control-label">Token</label>
                    <div class="col-sm-10">
                        <input type="text" id="id_tocken" class="form-control" readonly name="token"
                               value="${account.token}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">AppID</label>
                    <div class="col-sm-10">
                        <input type="text" name="appid" class="form-control" value="${account.appid}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">AppSecret</label>
                    <div class="col-sm-10">
                        <input type="text" name="appsecret" class="form-control" value="${account.appsecret}">
                    </div>
                </div>

                <div class="form-group form-inline">
                    <label class="col-sm-2 control-label">公众号ID</label>
                    <div class="col-sm-10">
                        <select name="msgcount" class="form-control">
                            <c:forEach var="oldMsgCount" items="${msgCountList}" varStatus="status">
                                <c:choose>
                                    <c:when test="${account.msgcount == oldMsgCount}">
                                        <option value="${oldMsgCount}" selected="selected">${oldMsgCount}条</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${status.count}">${status.count}条</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <span class="tips">(回复图文消息条数)</span>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <input type="submit" class="btn" style="background: #1A9DA7;color: #fff;" value="保 存"/>
                        <c:if test="${successflag==true}">
                        <span style="margin-left:10px;color:#44b549;">已成功获取URL 和 Token，请填写到微信平台中</span>
                        </c:if>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
