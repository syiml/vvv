<%@ page import="Main.Main" %>
<%@ page import="Discuss.DiscussHTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/4 0004
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Main.saveURL();
//  if(session.getAttribute("user")==null){
//    response.sendRedirect("Login.jsp");
//    return;
//  }
  int num= Main.contestShowNum;
  String pa=request.getParameter("page");
  if(pa==null||pa.equals("")) pa="1";
  String seach=request.getParameter("seach");
  String user=request.getParameter("user");
  if(seach!=null){//处理中文乱码
    byte source [] = seach.getBytes("iso8859-1");
    seach = new String (source,"UTF-8");
  }
%>
<html>
<head>
  <title>讨论列表 - T^T Online Judge</title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp?page=home"/>
    <%=DiscussHTML.DiscussList(-1,num,Integer.parseInt(pa),seach,user)%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
