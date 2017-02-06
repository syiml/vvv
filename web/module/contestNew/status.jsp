<%@ page import="entity.User" %>
<%@ page import="util.HTML.HTML" %>
<%@ page import="util.Main" %>
<%@ page import="util.Tool" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/22 0022
  Time: 12:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String cid=request.getParameter("cid");
  String pa=request.getParameter("page");

  int paInt=1,cidInt=-1;
  if(pa!=null) paInt=Integer.parseInt(pa);
  if(cid!=null) cidInt=Integer.parseInt(cid);

  String ssuser=request.getParameter("user");
  String pid=request.getParameter("pid");
  String result=request.getParameter("result");
  String lang=request.getParameter("lang");
  int pidInt=-1,resultInt=-1,langInt=-1;
  if(pid!=null&&!pid.equals("")) pidInt=Integer.parseInt(pid);
  if(result!=null&&!result.equals("")) resultInt=Integer.parseInt(result);
  if(lang!=null&&!lang.equals("")) langInt=Integer.parseInt(lang);
  if(cid!=null)
  {
    long t= Tool.now().getTime();
    out.println(HTML.StatusHTML(cidInt, paInt, pidInt, resultInt, langInt, ssuser,false,false));
  }
%>