<%@ page import="util.Main" %>
<%@ page import="entity.User" %>
<%@ page import="util.HTML.ExamProblemList" %>
<%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2016/5/31 0031
  Time: 13:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>考试管理 - T^T Online Judge</title>
</head>
<body><div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
<%
    User u=Main.loginUser();
    if(u==null||!u.getPermission().getExamAdmin()){
        out.print("没有权限");
        return;
    }
    int pa=0;
    try{
        pa=Integer.parseInt(request.getParameter("page"));
    }catch (Exception e){}
%>
<%=new ExamProblemList(pa).HTML()%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
