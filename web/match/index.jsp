<%@ page import="util.Main" %>
<%@ page import="entity.Contest" %>
<%@ page import="util.Tool" %><%--
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
    Main.saveURL();
    if(Main.loginUser()==null) response.sendRedirect("../Login.jsp");
    Contest c= Main.contests.getContest(Integer.parseInt(request.getParameter("cid")));
%>
<html>
<head>
    <title><%=c.getName()%> - 观战模式</title>
    <link href="match.css" rel="stylesheet">
</head>
<body>
<div class="main"></div>
<div class="debug"><div class="body"></div></div>
<div class="debug-switch open">Debug</div>

<div class="setting" style="display: none;">
    <input type="checkbox" id="setting-nick"/> 昵称<br>
    <input type="checkbox" id="setting-name"/> 姓名<br>
    <input type="checkbox" id="setting-gender"/> 性别<br>
    <input type="checkbox" id="setting-faculty"/> 学院<br>
    <input type="checkbox" id="setting-major"/> 专业<br>
    <input type="checkbox" id="setting-cla"/> 班级<br>
    <input type="checkbox" id="setting-no"/> 学号<br>
    <input type="button" id="setting-submit" value="确定"/><br>
    <input type="button" id="setting-autowidth" value="自动调整表格宽度"/>
</div>
<div class="setting-switch close">Setting</div>

<div class="chat">
    <div class="online"></div>
    <div class="chat-body"></div>
    <div class="chat-form">
        <input type="text" id="chat-text" class="form-control" onkeypress="if(event.keyCode==13) {$('#chat-submit').click();return false;}"/>
        <input type="button" value="发送" class="btn" id="chat-submit"/>
    </div>
</div>
<div class="chat-switch open">聊天</div>

</body>
</html>
<script src="../js/jquery-1.11.1.js"></script>
<script src="matchICPC.js"></script>
<script>
    var match;
</script>
<script src="rankDynamic.js"></script>
<script>
    match=matchICPC(<%=c.getCid()%>);
</script>