<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/6/25 0025
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="util.HTML.HTML" %>
<%@ page import="util.Main"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Main.saveURL();
//  if(session.getAttribute("user")==null){
//    response.sendRedirect("Login.jsp");
//    return;
//  }
  String pa=request.getParameter("page");
  String search=request.getParameter("search");
  String cid=request.getParameter("cid");
  if(search!=null){//处理中文乱码
    byte source [] = search.getBytes("iso8859-1");
    search = new String (source,"UTF-8");
  }

  String order=request.getParameter("order");
  String desc=request.getParameter("desc");
  String status = request.getParameter("status");
%>
<html>
<head>
  <title>用户列表 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
   <jsp:include page="module/head.jsp"/>
  <%=HTML.userList(cid,pa,search,order,desc!=null,status)%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<script src="js/useradmin.js"></script>


