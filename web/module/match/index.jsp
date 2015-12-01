<%@ page import="util.Main" %>
<%@ page import="entity.Contest" %><%--
    观战模式：
        动态刷新排行榜
        自定义排行榜显示列
        在线列表
        公屏聊天（弹幕）
        动态显示提交状态
        控制台
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/11/25 0025
  Time: 15:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Contest c= Main.contests.getContest(Integer.parseInt(request.getParameter("cid")));
%>
<html>
<head>
    <title><%=c.getName()%> - 观战模式</title>
    <link href="match.css" rel="stylesheet">
</head>
<body>
<div class="main"></div>
<div class="debug"><div class="title">debug</div><div class="body"></div></div>
<div class="debug-switch open">Debug</div>
</body>
</html>
<script src="matchICPC.js"></script>
<script src="rankDynamic.js"></script>
<script src="../../js/jquery-1.11.1.js"></script>
<script>
    matchICPC(<%=c.getCid()%>);
</script>