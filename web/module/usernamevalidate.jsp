<%@ page import="Main.Main" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/7 0007
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String username= request.getParameter("username");
  System.out.println("validate:"+username);
%>
<%=Main.users.getUser(username)==null%>
