<%@ page import="util.HTML.HTML" %>
<%@ page import="util.Main"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/20
  Time: 17:14
  To change this template use File | Settings | File Templates.
--%>
<%--
  所需参数：无
--%>
<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
  Main.saveURL();
//  if(session.getAttribute("user")==null){
//    response.sendRedirect("Login.jsp");
//    return;
//  }

%>
<!DOCTYPE html>
<html>
  <head>
    <title><%=Main.config.OJName%></title>
  </head>
  <body>
    <div class="container-fluid">
        <jsp:include page="module/head.jsp?page=home"/>
          <%=HTML.index()%>
    </div>
    <jsp:include page="module/foot.jsp"/>
  </body>
</html>
