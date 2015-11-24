<%@ page import="util.Main" %>
<%@ page import="util.HTML.HTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/10/21 0021
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%//未登录跳转
  if(session.getAttribute("user")==null){
    Main.saveURL();
    response.sendRedirect("Login.jsp");
    return;
  }
  if(!Main.loginUserPermission().getAddLocalProblem()){
    response.sendRedirect("info.jsp?info=没有权限");
  }
  Main.saveURL();
  String pid=request.getParameter("pid");
%>
<html>
<head>
    <title>上传数据</title>
</head>
<body>
  <div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
    <%=HTML.uploadSample(pid)%>
  </div>
  <jsp:include page="module/foot.jsp"/>
</body>
</html>
