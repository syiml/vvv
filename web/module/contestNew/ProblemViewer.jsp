<%@ page import="util.HTML.HTML" %>
<%@ page import="util.Main" %>
<%@ page import="servise.ContestMain" %>
<%@ page import="entity.User" %>
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
  User user=Main.loginUser();
  //System.out.println(user);
%>
<%=ContestMain.canShowProblem(Integer.parseInt(cid))?(HTML.problem(user,cid, pid)):""%>

