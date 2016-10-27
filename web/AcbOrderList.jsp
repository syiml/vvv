<%@ page import="util.Main" %>
<%@ page import="util.HTML.AcbOrderListHTML" %><%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2016/10/26
  Time: 22:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = request.getParameter("username");
    int pa;
    try {
        pa = Integer.parseInt(request.getParameter("page"));
    }catch(NumberFormatException e){
        pa = 1;
    }
%>
<html>
<head>
    <title>ACB账单 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
    <%=new AcbOrderListHTML(username,pa).HTML()%>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>
