<%@ page import="util.HTML.HTML" %>
<%@ page import="util.Main" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/27 0027
  Time: 21:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  Main.saveURL();
//  if(session.getAttribute("user")==null){
//    response.sendRedirect("Login.jsp");
//    return;
//  }
  String user=request.getParameter("user");
  String day=request.getParameter("day");
  String times=request.getParameter("times");
%>
<html>
<head>
  <title>签到 - T^T Online Judge</title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
    <%=HTML.clockIn(user,day,times)%>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>
