<%@ page import="util.Main" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/21
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
    所需参数:   cid
                pid
--%>
<%
    //未登录跳转
    if(session.getAttribute("user")==null){
        response.sendRedirect("Login.jsp");
    }else{
    String cid=request.getParameter("cid");
    String pid=request.getParameter("pid");
%>
<!DOCTYPE html>
<html>
<head>
    <title>提交 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
    <center>
        <%@include file="module/submitForm.jsp"%>
    </center>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>
<%}%>