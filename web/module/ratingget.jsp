<%@ page import="dao.ratingSQL" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/6 0006
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String user=request.getParameter("user");
  boolean t=(request.getParameter("true")!=null);
%>

<%=ratingSQL.getJson(user,t)%>
