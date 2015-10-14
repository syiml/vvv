<%@ page import="jdk.nashorn.internal.ir.RuntimeNode" %>
<%@ page import="Tool.HTML.HTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/29 0029
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
 int cid= Integer.parseInt(request.getParameter("cid"));
  double f=Double.parseDouble(request.getParameter("f"));
%>
<%=HTML.contestCodeCompare(cid, f)%>
