<%@ page import="util.Main" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/8/13 0013
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String pid=request.getParameter("pid");
  Main.problems.addSample(Integer.parseInt(pid));
  response.sendRedirect("../return.jsp");
%>