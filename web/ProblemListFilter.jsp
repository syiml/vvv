<%@ page import="util.Main" %>
<%@ page import="util.HTML.HTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/10/13 0013
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Main.saveURL();
  String name=request.getParameter("name");
  if(name!=null){//处理中文乱码
    byte source [] = name.getBytes("iso8859-1");
    name = new String (source,"UTF-8");
  }
  String tag=request.getParameter("tag");
  String pa=request.getParameter("pa");
  String star=request.getParameter("star");
%>

<html>
<head>
  <title>题目筛选列表 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
  <jsp:include page="module/head.jsp?page=problemset"/>
  <%=HTML.problemListFilter(name,tag,star!=null,pa)%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
