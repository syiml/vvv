<%@ page import="util.Main" %>
<%@ page import="util.HTML.HTML" %><%--
  Created by IntelliJ IDEA.
  User: syimlzhu
  Date: 2016/9/7
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Main.saveURL();
    String pa=request.getParameter("page");
%>
<html>
<head>
    <title>活跃榜 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
    <%=HTML.WeekRankCountHTML(pa)%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<script src="js/useradmin.js"></script>
