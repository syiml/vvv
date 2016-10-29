<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/6/26 0026
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="util.HTML.HTML" %>
<%@ page import="entity.User" %>
<%@ page import="util.Main"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Main.saveURL();
  String ShowUser=request.getParameter("user");
  User user=Main.loginUser();
  if(ShowUser==null&&user!=null){
    ShowUser=user.getUsername();
  }
%>
<html>
<head>
  <title>用户信息 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
    <%=HTML.user(ShowUser,user)%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<script type="text/javascript" src="js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="js/highcharts/highcharts-more.js"></script>