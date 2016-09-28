<%@ page import="util.HTML.HTML" %>
<%@ page import="util.Main" %>
<%@ page import="util.HTML.ProblemTagHTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/8/18 0018
  Time: 11:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Main.saveURL();
//  if(session.getAttribute("user")==null){
//    response.sendRedirect("Login.jsp");
//    return;
//  }

  String pid=request.getParameter("pid");
  String pa=request.getParameter("pa");
  String cid=request.getParameter("cid");
%>
<html>
<head>
  <title>题目信息 - <%=Main.config.OJName%></title>
</head>
<body><div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
    <%=HTML.problemInfo(pid,pa,cid)%>
  </div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<script type="text/javascript" src="js/highcharts/highcharts.js"></script>