<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/9/27 0027
  Time: 20:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
  <script>
    alert('<%=request.getParameter("info")%>');
    location.href="<%=!request.getParameter("next").equals("")?request.getParameter("next"):"return.jsp"%>";
  </script>
</body>
</html>
