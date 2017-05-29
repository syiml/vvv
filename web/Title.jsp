<%@ page import="util.Main" %>
<%@ page import="util.HTML.TitleListHTML" %><%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2017/5/27
  Time: 17:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>称号列表 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp?page=status"/>
    <%=new TitleListHTML(request.getParameter("username")).HTML()%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>

