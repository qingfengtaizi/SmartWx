
<%
Object obj = request.getAttribute("path");
obj = (obj == null)?request.getAttribute("url"):obj;
request.getRequestDispatcher(obj+"").forward(request,response);
%>