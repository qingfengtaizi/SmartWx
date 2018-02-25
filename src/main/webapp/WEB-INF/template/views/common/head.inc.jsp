<%@ page language="java"  pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/u" prefix="u"%>

<c:set var="advertBaseURL" value="http://mifenghyb.com/advert"/>

<%-- <c:set var="advertBaseURL" value="http://jnjava.iok.la/advert"/>--%>
<%--  <c:set var="advertBaseURL" value="http://localhost:8080/advert"/>--%>
<%
    String path = request.getContextPath(); ///wxmp
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; //http://localhost:8080/wxmp
    String basePath_WS = "ws"+"://"+request.getServerName()+":"+request.getServerPort()+path; //ws://localhost:8080/wxmp

%>
<script>
    var path ="<%=path%>";
    var basePath ="<%=basePath%>";
    var basePath_WS ="<%=basePath_WS%>";
    /**var advertHttpURL = "http://localhost:8080/advert";*/
    var advertHttpURL = "http://mifenghyb.com/advert";
</script>