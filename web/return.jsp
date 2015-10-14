<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/5 0005
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
  <%
    String url=(String)session.getAttribute("url");
    if(url==null) url="index.jsp";
    response.sendRedirect(url);
    //out.println(request.getRequestURI()+"?"+request.getQueryString());
  %>
</body>
</html>
