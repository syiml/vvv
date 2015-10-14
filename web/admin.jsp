<%@ page import="Tool.HTML.HTML" %>
<%@ page import="Main.User.Permission" %>
<%@ page import="Main.Main" %>
<%@ page import="Main.User.User" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/6/14 0014
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Main.saveURL();
    String pa=request.getParameter("page");
    Object user=session.getAttribute("user");
    if(session.getAttribute("user")==null){
        response.sendRedirect("Login.jsp");
        return;
    }
    Permission per=null;
    if(user!=null) per=Main.getPermission(((User)user).getUsername());
    else response.sendRedirect("Login.jsp");
%>
<html>
<head>
    <title>管理 - T^T Online Judge</title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
      <div style='width:14%;float:left'>
          <%=HTML.adminNAV(per,pa)%>
      </div>
      <div style='width:84%;float:right'>
          <%=HTML.adminMAIN(per, pa)%>
      </div>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>
