<%@ page import="util.HTML.HTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/3 0003
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%--
  最近比赛
  num:数量
    根据开始时间排序
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%=HTML.recentlyContestTable(Integer.parseInt(request.getParameter("num")))%>
