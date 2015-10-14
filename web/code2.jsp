<%@ page import="Main.status.statu" %>
<%@ page import="Main.Main" %>
<%@ page import="Main.User.Permission" %>
<%@ page import="Tool.HTML.HTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/29 0029
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
//  if(session.getAttribute("user")==null){
//    response.sendRedirect("Login.jsp");
//    return;
//  }
  String rid = request.getParameter("rid");
  String rid2 = request.getParameter("rid2");
  Object user=session.getAttribute("user");
%>
<link href="js/prism/css.css" rel="stylesheet" />
<script src="js/prism/js.js"></script>
<html>
<head>
  <title>代码对比 - T^T Online Judge</title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
    相似度：<%=String.format("%.2f",100*Main.codeCmp(Main.getCode(Integer.parseInt(rid)), Main.getCode(Integer.parseInt(rid2))))+"%"%>
    <div class="row">
    <div class="col-sm-6">
      <%=HTML.viewCode(rid,user,true)%>
    </div><div class="col-sm-6">
      <%=HTML.viewCode(rid2,user,true)%>
    </div></div>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>