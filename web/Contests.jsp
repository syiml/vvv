<%@ page import="Main.Main" %>
<%@ page import="Tool.HTML.HTML" %>
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
//  if(session.getAttribute("user")==null){
//    response.sendRedirect("Login.jsp");
//    return;
//  }
  String num = request.getParameter("num");
  String pa  = request.getParameter("page");
  String st  = request.getParameter("statu");
  String name= request.getParameter("name");
  String ty  = request.getParameter("type");
  String kind= request.getParameter("kind");
  if(name!=null){//处理中文乱码
    byte source [] = name.getBytes("iso8859-1");
    name = new String (source,"UTF-8");
  }
%>
<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>比赛列表 - T^T Online Judge</title>
</head>
<body>
<div class="container-fluid">
  <jsp:include page="module/head.jsp?page=contests"/>
  <%=HTML.contestList(num, pa, st, name, ty , kind)%>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>
