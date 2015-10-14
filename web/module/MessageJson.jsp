<%@ page import="Tool.JSON.JSON" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/10 0010
  Time: 22:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String from=request.getParameter("from");
    String num=request.getParameter("num");
%>
<%=JSON.Messages(from,num)%>