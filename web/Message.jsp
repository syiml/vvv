<%@ page import="Tool.HTML.HTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/11 0011
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
Main.Main.saveURL();
  if(session.getAttribute("user")==null){
    response.sendRedirect("Login.jsp");
    return;
  }
%>
<html>
<head>
  <title>消息 - T^T Online Judge</title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp?page=home"/>
    <div id="messageMain"></div>
    <script src="js/HTML.js"></script>
    <script src="js/Message.js"></script>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>
