<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/6/30 0030
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="Main.Main" %>
<%@ page import="Tool.HTML.HTML" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  所需参数:cid
--%>
<%
  String cid = request.getParameter("cid");
%>
<%=HTML.computeRating(cid)%>