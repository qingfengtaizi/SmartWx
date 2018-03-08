<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<% 
  String path = request.getContextPath() + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/u" prefix="u"%>
<style type="text/css">

    .clckClass{background:#849001;}
</style>
<div id="mws-sidebar-stitch"></div>
<div id="mws-sidebar-bg"></div>
<div id="mws-sidebar">
	<div id="mws-searchbox" class="mws-inset">
		<form
			action="http://www.malijuwebshop.com/themes/mws-admin/table.html">
			<input type="text" class="mws-search-input" /> <input type="submit"
				class="mws-search-submit" />
		</form>
	</div>
	<!-- 菜单 -->
	<div id="mws-navigation">
		<ul>
		    <c:forEach items="${menuP }" var="listp">
		        <li class="<c:if test="${listp.nodeId == menuSelected }">active</c:if>">
                     <a href="javascript:(0);" class="mws-i-24 i-list">${listp.menuCh }</a>
                     <ul style="display: <c:if test="${listp.nodeId == menuSelected }">block</c:if><c:if test="${listp.nodeId != menuSelected }">none</c:if>;" class="<c:if test="${listp.nodeId != menueSelected }">closed</c:if>">
                        <c:forEach items="${menuC}" var="listc">
                      	<c:if test="${listc.pNodeId == listp.nodeId }">
                      	   <li <c:if test="${listc.menuUrl == menuCSelected }">class='clckClass'</c:if>><a href="<%=path %>${listc.menuUrl}">${listc.menuCh } </a></li>
                      	</c:if>
                      	</c:forEach>
                     </ul>
                </li>
		    </c:forEach>
			<%-- <li><a href="javascript:(0);" class="mws-i-24 i-list">场地管理</a>
				<ul>
					<li ><a href="/sw-platformadmin-webapp/manager/site/list"> 场地管理</a></li>
				</ul>
			</li>
			<li><a href="javascript:(0);" class="mws-i-24 i-list">车辆管理</a>
				<ul>
					<li><a href="/sw-platformadmin-webapp/manager/car/list"> 车辆管理</a></li>
				</ul>
			</li>
			<li><a href="javascript:(0);" class="mws-i-24 i-list">策略管理</a>
				<ul>
					<li><a href="/sw-platformadmin-webapp/manager/strategy/list"> 策略管理</a></li>
				</ul>
			</li>
			<li><a href="javascript:(0);" class="mws-i-24 i-list">公告管理</a>
				<ul>
					<li><a href="/sw-platformadmin-webapp/manager/strategy/list"> 新闻公告</a></li>
				</ul>
			</li>
			<li><a href="javascript:(0);" class="mws-i-24 i-list">系统管理</a>
				<ul>
					<li class="user_list"><a href="/sw-platformadmin-webapp/manager/system/user/list"> 用户管理</a></li>
					<li class="dic_list"><a href="/sw-platformadmin-webapp/manager/system/dic/list"> 字典管理</a></li>
					<li class="optlog_list"><a href="/sw-platformadmin-webapp/manager/system/optlog/list"> 日志管理</a></li>
					<li class="strategy_list"><a href="/sw-platformadmin-webapp/manager/strategy/list"> 区域管理</a></li>
				</ul>
			</li> --%>
		</ul>
	</div>
</div>


<!--二级选中目录经过的时候背景颜色不改变-->

<script type="text/javascript">
	$(function(){
		$(".active ul").find(".clckClass").hover(function(){
			
			$(this).css("background","#849001");
		
			});
		});
	
	
	//input  placeholder  IE中的兼容
	
	$(function(){
if(!placeholderSupport()){   // 判断浏览器是否支持 placeholder
    $('[placeholder]').focus(function() {
        var input = $(this);
        if (input.val() == input.attr('placeholder')) {
            input.val('');
            input.removeClass('placeholder');
        }
    }).blur(function() {
        var input = $(this);
        if (input.val() == '' || input.val() == input.attr('placeholder')) {
            input.addClass('placeholder');
            input.val(input.attr('placeholder'));
        }
    }).blur();
};
})
function placeholderSupport() {
    return 'placeholder' in document.createElement('input');
}

</script>