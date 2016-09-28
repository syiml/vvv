<%@ page import="util.HTML.HTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/2 0002
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String name=request.getParameter("name");
%>
<html>
<head>
  <title>错误 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp?page=home"/>
    <%=HTML.col(10,1,HTML.panel("ERROR",name,null,"danger"))%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
