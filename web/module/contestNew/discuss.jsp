<%@ page import="util.HTML.DiscussHTML" %>
<%@ page import="util.Main" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/11/20 0020
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  int cid= Integer.parseInt(request.getParameter("cid"));
  int id;
  int pa=0;
  if(request.getParameter("page")!=null){
    pa=Integer.parseInt(request.getParameter("page"));
  }
  if(request.getParameter("id")==null){
    out.print(DiscussHTML.DiscussList(cid,Main.config.discussShowNum,pa,"",""));
  }else{
    id=Integer.parseInt(request.getParameter("id"));
    out.print(DiscussHTML.Discuss(id+"",pa+""));
  }
%>
