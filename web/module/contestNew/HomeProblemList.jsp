<%@ page import="Tool.JSON.JSON" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/21 0021
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  int cid=Integer.parseInt(request.getParameter("cid"));
%>
<%=JSON.getContestProblemList(cid)%>