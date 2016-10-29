<%@ page import="util.Main" %>
<%@ page import="util.HTML.HTML" %>
<%@ page import="entity.User" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/23
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%--
  所需参数：cid  如果是null表示全局题目列表
            page 显示的页数
--%>
<%
  Main.saveURL();
  String cid=request.getParameter("cid");
  String pa=request.getParameter("page");
  User user=Main.loginUser();
  int paInt=1;
  if(pa!=null) paInt=Integer.parseInt(pa);
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>题目列表 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
  <jsp:include page="module/head.jsp?page=problemset"/>
  <%=HTML.problemList(Main.config.problemShowNum,paInt,user)%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
