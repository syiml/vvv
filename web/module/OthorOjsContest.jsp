<%@ page import="util.MyClient" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/9/5 0005
  Time: 0:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  MyClient mc=new MyClient();
  out.println(mc.get("http://contests.acmicpc.info/contests.json").html());
%>
