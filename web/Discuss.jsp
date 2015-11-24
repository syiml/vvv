<%@ page import="util.HTML.DiscussHTML" %>
<%@ page import="util.Main"%>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/4 0004
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  Main.saveURL();
//  if(session.getAttribute("user")==null){
//    response.sendRedirect("Login.jsp");
//    return;
//  }
  String id=request.getParameter("id");
  String pa=request.getParameter("page");
  if(pa==null||pa.equals("")) pa="0";
%>
<html>
<head>
  <title>讨论 - T^T Online Judge</title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp?page=home"/>
    <%=DiscussHTML.Discuss(id,pa)%>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>
