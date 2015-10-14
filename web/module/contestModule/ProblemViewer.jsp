<%@ page import="Tool.HTML.HTML" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/26
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%--
  参数:cid、pid
--%>
<%
  String cid=request.getParameter("cid");
  String pid=request.getParameter("pid");
  Object user=session.getAttribute("user");
  //System.out.println(user);
%>
<%=Main.Main.canShowProblem(Integer.parseInt(cid))?(HTML.problem(user,cid, pid)):""%>

