<%@ page import="Main.Main" %>
<%@ page import="Main.problem.ProblemGetter" %>
<%@ page import="Tool.HTML.HTML" %>
<%@ page import="org.apache.struts2.ServletActionContext" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/23
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>
<%--
  所需参数:pid
--%>
<%
  Main.saveURL();
//  if(session.getAttribute("user")==null){
//    response.sendRedirect("Login.jsp");
//    return;
//  }
  String pid = request.getParameter("pid");
  Object user= session.getAttribute("user");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>题目 - T^T Online Judge</title>
</head>
<body><div class="container-fluid">
  <jsp:include page="module/head.jsp"/>
  <%=HTML.problem(user,null,pid)%>
  </div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<script src='js/problemTag.js'></script>
