<%@ page import="Main.Main" %>
<%@ page import="Tool.HTML.HTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/10/4 0004
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Main.saveURL();
//  if(session.getAttribute("user")==null){
//    response.sendRedirect("Login.jsp");
//    return;
//  }
%>
<html>
<head>
  <title>题目筛选列表 - T^T Online Judge</title>
</head>
<body>
<div class="container-fluid">
  <jsp:include page="module/head.jsp?page=problemset"/>
  <div id='ptg-main' class="panel panel-default">
    <div class="panel-heading">题目筛选列表</div>
    <div id="ptg-tb" class="panel-body" style="padding:5px">
      <div style="float: left">
        <div class="btn-toolbar" role="toolbar">
          <div class="btn-group" role="group">
            <a id="ptg-top" role="button" class="btn btn-default btn-sm" >首页</a>
          </div>
          <div class="btn-group" role="group">
            <a id="ptg-pre" role="button" class="btn btn-default btn-sm" disabled="disabled">上一页</a>
            <a id="ptg-now" role="button" class="btn btn-default btn-sm">1</a>
            <a id="ptg-next" role="button" class="btn btn-default btn-sm" disabled="disabled">下一页</a>
          </div>
        </div>
      </div>
      <div id="ptg-f" style="float: right"></div>
    </div>
  </div>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<script src="js/ptg.js"></script>