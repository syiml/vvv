<%@ page import="util.Main" %>
<%@ page import="util.HTML.HTML" %>
<%@ page import="util.HTML.ExamListHTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/23
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%--参数列表
名称    类型    默认值
num    int    Main.contestListNum
page   int    0
statu  int    -1
name   String null
type   int    -1
kind   int    -1
--%>
<%
  Main.saveURL();
  String pa= request.getParameter("page");
  int paInt;
  try{
    paInt=Integer.parseInt(pa);
  }catch (NumberFormatException e){
    paInt=1;
  }
%>
<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>考试列表 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
  <jsp:include page="module/head.jsp?page=contests"/>
  <%=new ExamListHTML(paInt).HTML()%>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>
