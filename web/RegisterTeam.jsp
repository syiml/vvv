<%@ page import="util.Main" %>
<%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2016/5/4 0004
  Time: 12:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>比赛注册 - <%=Main.config.OJName%></title>
</head>
<body>
<%
  if(Main.loginUser() == null){
    response.sendRedirect("Login.jsp");
  }else{
%>
<div class="container-fluid">
  <jsp:include page="module/head.jsp"/>
  <div class="row"><div class="col-sm-12"><jsp:include page="module/RegisterTeam.jsp"/></div></div>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<%}%>
