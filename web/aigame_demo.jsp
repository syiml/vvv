<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="util.Main" %>
<%@ page import="action.AiAction" %>
<%@ page import="util.JSON.AiJsonGetAiList" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.AiInfo" %>
<%@ page import="dao.AiSQL" %>
<%--
  Created by IntelliJ IDEA.
  Date: 2017/9/5
  Time: 21:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Main.saveURL();
    if(Main.loginUser()==null){
        response.sendRedirect("Login.jsp");
        return;
    }
//
//    int pa=1;
//    try{
//        pa = Integer.parseInt(request.getParameter("page"));
//    }catch(NumberFormatException e)
//    {   }
//    String aiName = request.getParameter("aiName");
//    if (aiName == null) aiName ="";
//    int gameInt = -1;
//    try{
//        gameInt = Integer.parseInt(request.getParameter("game_id"));
//    }catch(NumberFormatException e)
//    {   }
    String username = Main.loginUser().getUsername();
//    List<AiInfo> aiList = new AiJsonGetAiList(username,gameInt,aiName,pa,10).jspGetList();
//    int totalpage = new AiJsonGetAiList(username,gameInt,aiName,pa,10).jspGetTotalPage();
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
    <script type="text/javascript" language="JavaScript" src="js/jquery-1.11.1.js"></script>
    <script type="text/javascript" language="JavaScript" src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/dateFormat.js"></script>
    <link rel="stylesheet" href="css/main.css">

    <title>AI上传格式和说明 - <%=Main.config.OJName%></title>
</head>
<body>
<br>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <nav class="navbar navbar-default" role="navigation">
                <!--
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
                    <img src="syspic/top1.jpg" class="img-rounded" width="73px" height="50px">
                </div>
                -->

                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li>
                            <a href="index.jsp">&nbsp;&nbsp;<b><%=Main.config.OJName%></b> &nbsp;&nbsp;</a>
                        </li>
                        <li class="active">
                            <a>&nbsp;&nbsp;AI管理&nbsp;&nbsp;</a>
                        </li>
                        <li>
                            <a href="aigame_index.html">&nbsp;&nbsp;AI主页 &nbsp;&nbsp;</a>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><%=username%> <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="UserInfo.jsp?user=<%=username%>">账号信息</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="Logout.jsp">退出账号</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>
            <div class="row clearfix">
                <div class="col-md-2 column">
                    <div class="panel-group" id="panel-84243">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <span class="glyphicon glyphicon-user"></span>  <span id="admin"><%=username%></span>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-84243" href="#panel-element-867475">AI 管理</a>
                            </div>
                            <div id="panel-element-867475" class="panel-collapse collapse in">
                                <div class="panel-body">
                                    <a href="aigame_userMangeAi.jsp" class="text-primary">AI 编辑</a>
                                </div>
                                <div class="panel-body">
                                    <a href="aigame_demo.jsp" class="text-primary">上传说明</a>
                                </div>
                                <%--<div class="panel-body">--%>
                                    <%--<a href="#" class="text-primary">列表1-2</a>--%>
                                <%--</div>--%>
                                <%--<div class="panel-body">--%>
                                    <%--<a href="#" class="text-primary">列表1-3</a>--%>
                                <%--</div>--%>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-84243" href="#panel-element-365878">对战记录查询</a>
                            </div>
                            <div id="panel-element-365878" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <a href="aigame_aiBattleRecord.jsp" class="text-primary">人机对战</a>
                                </div>
                                <%--<div class="panel-body">--%>
                                <%--<a href="#" class="text-primary">列表2-2</a>--%>
                                <%--</div>--%>
                                <%--<div class="panel-body">--%>
                                <%--<a href="#" class="text-primary">列表2-3</a>--%>
                                <%--</div>--%>
                            </div>
                        </div>
                        <%--<div class="panel panel-default">--%>
                            <%--<div class="panel-heading">--%>
                                <%--<a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-84243" href="#panel-element-365874">列表3</a>--%>
                            <%--</div>--%>
                            <%--<div id="panel-element-365874" class="panel-collapse collapse">--%>
                                <%--<div class="panel-body">--%>
                                    <%--<a href="#" class="text-primary">列表3-1</a>--%>
                                <%--</div>--%>
                                <%--<div class="panel-body">--%>
                                    <%--<a href="#" class="text-primary">列表3-2</a>--%>
                                <%--</div>--%>
                                <%--<div class="panel-body">--%>
                                    <%--<a href="#" class="text-primary">列表3-3</a>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    </div>
                </div>
                <div class="col-md-10 column embed-responsive embed-responsive-16by9" style="height:100%;padding-bottom: 15px">
                    <h1>AI上传说明</h1>
                    <h2>描述</h2>
                    <p></p>
                    <h2>输入</h2>
                    <p> 第一行输入两个整数表示棋盘的大小row col（固定15*15）
                        然后循环读取直到文件尾</p>

                    <p>第i是两个整数xi yi （0&lt;=xi&lt;row   0&lt;=yi&lt;col） (设i从0开始）</p>

                    <p>当i为偶数时表示黑方落子</p>
                    <p>当i为奇数时表示白方落子</p>

                    <p>若最后一行是黑方则你的AI要输出下一步的白方落子</p>
                    <p>反之输出黑方落子</p>

                    <h2>输出</h2>
                    <p>输出是一行，两个整数，表示AI落子的坐标

                        所有坐标均从0开始。</p>
                    <h2>样例输入</h2>
                    <pre>15 15
7 7
7 6</pre>
                    <h2>样例输出</h2>
                    <pre>6 6</pre>
                </div>
                <!--
                <div class="col-md-2 column">
                    <dl>
                        <dt>
                            关于学校AI简介
                        </dt>
                        <dd>
                            这是一个神奇的东西这是一个神奇的东西这是一个神奇的东西
                        </dd>
                        <dt>
                            阿尔法狗
                        </dt>
                        <dd>
                            这是一个神奇的东西这是一个神奇的东西这是一个神奇的东西这是一个神奇的东西这是一个神奇的东西
                        </dd>
                    </dl>
                </div>
                -->
            </div>
        </div>
    </div>
</div>
    <jsp:include page="module/foot.jsp"/>
</body>
</html>
