<%@ page import="entity.User" %>
<%@ page import="util.Main" %>
<%@ page import="util.Tool" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  User u=(User)session.getAttribute("user");
    if (u != null) {
        Tool.log("logout:" +(u.getUsername()));
  }
  session.removeAttribute("user");

  String url=(String)session.getAttribute("url");
  if(url==null) url="index.jsp";
  response.sendRedirect(url);
%>
<html>
<head>
  <title>退出 - T^T Online Judge</title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
    <div class="row"><div class="col-sm-8 col-sm-offset-2"><h2>Logout Success</h2></div></div>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>