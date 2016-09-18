<%@ page import="util.HTML.MallHTML" %><%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2016/9/17
  Time: 1:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ACB商城 - T^T Online Judge</title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp?page=home"/>
    <%=MallHTML.index()%>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>
