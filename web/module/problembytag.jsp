<%@ page import="Tool.JSON.JSON" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/10/4 0004
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String tag=request.getParameter("tag");
  String pa=request.getParameter("pa");
%>
<%=JSON.getProblemByTag(Integer.parseInt(tag),Integer.parseInt(pa))%>