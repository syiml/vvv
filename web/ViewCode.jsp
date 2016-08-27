<%@ page import="entity.Status" %>
<%@ page import="util.Main" %>
<%@ page import="entity.Permission" %>
<%@ page import="util.HTML.HTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/6/8
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
//  if(session.getAttribute("user")==null){
//    response.sendRedirect("Login.jsp");
//    return;
//  }
  String rid = request.getParameter("rid");
%>
<link href="js/prism/css.css" rel="stylesheet" />
<script src="js/prism/js.js"></script>
<html>
<head>
  <title>查看代码 - T^T Online Judge</title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
      <%=HTML.viewCode(rid,true)%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>