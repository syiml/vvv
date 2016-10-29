<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="util.Main" %>
<%@ page import="dao.statusSQL" %>
<%@ page import="util.HTML.HTML" %>
<%@ page import="entity.User" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/23
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>

<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<%--
  参数：cid
       from
       page
       user   session内,当前登陆的user
  筛选：user
       pid
       result
       lang
--%>
<%
    Main.saveURL();
  String pa=request.getParameter("page");
  int paInt=1;
  if(pa!=null) paInt=Integer.parseInt(pa);

  String ssuser=request.getParameter("user");
  String pid=request.getParameter("pid");
  String result=request.getParameter("result");
  String lang=request.getParameter("lang");
  String all=request.getParameter("all");
  int pidInt=-1,resultInt=-1,langInt=-1;
  if(pid!=null&&!pid.equals("")) pidInt=Integer.parseInt(pid);
  if(result!=null&&!result.equals("")) resultInt=Integer.parseInt(result);
  if(lang!=null&&!lang.equals("")) langInt=Integer.parseInt(lang);
%>
<html>
<head>
    <title>评测状态 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
  <jsp:include page="module/head.jsp?page=status"/>
  <%=HTML.StatusHTML(-1,paInt,pidInt,resultInt,langInt,ssuser,all!=null)%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<script src="js/jquery-ui.min.js"></script>
<script src="js/autoRefreshTable.js"></script>
<script>
var auto=null;
$(window).on('hashchange', function() {
    if(auto!=null) auto.stop();
    if(location.hash=='#A'){
        auto=autoRefreshTable(location.href,"table",'table');
        auto.go("auto");
    }
});
</script>