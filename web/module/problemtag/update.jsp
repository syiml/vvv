<%@ page import="dao.ProblemTagSQL" %>
<%@ page import="entity.User" %>
<%@ page import="util.Main" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/25 0025
  Time: 11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String type=request.getParameter("type");
  int tagid=Integer.parseInt(request.getParameter("tagid"));
  int pid=Integer.parseInt(request.getParameter("pid"));
  User u= Main.loginUser();
  if(type.equals("add")){
    ProblemTagSQL.addTag(pid,u.getUsername(),tagid);
  }else{
    ProblemTagSQL.delTag(pid,u.getUsername(),tagid);
  }
%>