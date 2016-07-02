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

    int id=0;
    String type;
    try{
      id=Integer.parseInt(request.getParameter("id"));
      type=request.getParameter("type");
    }catch (Exception e){}
    if(id==0){
      //if(type==)
      out.print("参数错误");
      return;
    }
  %>
  <%= 1 %>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
