<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="util.Main" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.AiSQL" %>
<%@ page import="entity.AiInfo" %>
<%@ page import="util.HTML.HTML" %>
<%@ page import="entity.GameRep" %>
<%@ page import="dao.GameRepSQL" %>
<%@ page import="java.text.SimpleDateFormat" %>

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

    int pa=1;
    try{
        pa = Integer.parseInt(request.getParameter("page"));
    }catch(NumberFormatException e)
    {   }
//    String aiName = request.getParameter("aiName");
//    if (aiName == null) aiName ="";
//    int gameInt = -1;
//    try{
//        gameInt = Integer.parseInt(request.getParameter("game_id"));
//    }catch(NumberFormatException e)
//    {   }
    int aiId = -1;
    try{
        aiId = Integer.parseInt(request.getParameter("id"));
    }catch(NumberFormatException e)
    {   }
    String username = Main.loginUser().getUsername();
    //List<AiBattleInfo> List = AiSQL.getInstance().getAiGameRepetition(username,aiName,gameInt,aiId,(pa-1)*10,10);
    //int num = AiSQL.getInstance().getAiRepetitionTotalNum(username,aiName,gameInt);
    List<GameRep> List = GameRepSQL.getInstance().getAIBattleInfo(aiId,(pa-1)*10,10);
    int num = GameRepSQL.getInstance().getAIBattleInfoNum(aiId);
    int totalpage;
    if (num == 0){
         totalpage=1;
    }else{
        totalpage = num/10 + (num % 10 != 0 ? 1 :0);
    }
    int gameInt = -1;
    try {
        gameInt = AiSQL.getInstance().getBeanByKey(aiId).getGame_id();
    }catch (Exception e) {}
    List<AiInfo> aiInfos = AiSQL.getInstance().getAiListByUsername(username);
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
    <title>AI挑战记录 - <%=Main.config.OJName%></title>
</head>
<body>
<script>
    $(function () {
        //获取点击修改的列表的id值
        $(".modify").click(function(){
            var modifyid = $(this).attr("id");
            $('.rep').html("<iframe style='width:100%;height:700px;border-width:0' src='module/GoBangResult.jsp?type=2&rid="+modifyid+"'></iframe>");
            $('#repModal').modal('toggle');
        });
    });
</script>
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
                            <div id="panel-element-867475" class="panel-collapse collapse">
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
                            <div id="panel-element-365878" class="panel-collapse collapse in">
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
                <div class="col-md-10 column embed-responsive embed-responsive-16by9">
                    <!--搜索框组-->
                    <div class="row">
                        <%--<div class="col-sm-2">--%>
                            <%--<button class="btn btn-info btn-sm" data-toggle="modal" data-target="#exampleModalRedirest"><span class="glyphicon glyphicon-cloud-upload"></span>&nbsp;&nbsp;上 传</button><br><br>--%>
                        <%--</div>--%>
                        <div class="col-sm-8">
                            <!--用于发送搜索信息-->
                            <form class="form-inline" action="">
                                <%--<div class="form-group">--%>
                                    <%--<div class="input-group">--%>
                                        <%--<div class="input-group-addon">类型</div>--%>
                                        <%--<select class="form-control" id="game_id" name="game_id">--%>
                                            <%--<option value="-1" <%=gameInt==-1?"selected":""%>>不限</option>--%>
                                            <%--<option value="1" <%=gameInt==1?"selected":""%>>五子棋</option>--%>
                                        <%--</select>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <div class="form-group">
                                    <div class="input-group">
                                        <div class="input-group-addon">AI</div>
                                        <select class="form-control" id="ai_nanme" name="id">
                                            <%--<option value="-1" <%=gameInt==-1?"selected":""%>>不限</option>--%>
                                            <%for(int i=0;i< aiInfos.size();i++) {
                                            %>
                                                <option value="<%=aiInfos.get(i).getId()%>" <%=aiInfos.get(i).getId()==aiId?"selected":""%>><%=aiInfos.get(i).getAiName()%></option>
                                            <%
                                            }%>
                                        </select>
                                    </div>
                                </div>
                                <%--<div class="form-group">--%>
                                    <%--<div class="input-group">--%>
                                        <%--<div class="input-group-addon">AI名称</div>--%>
                                        <%--<input type="text" class="form-control" id="exampleInputAmount" placeholder="AI名称" name="aiName" value="<%=aiName%>">--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span>  筛选</button>
                            </form>

                            <script type="text/javascript" language="JavaScript">
                                    //搜索提交
                                    //1.搜索类型 sousuo = 按照作者名搜索
                                    //2.搜索值 sousuovalue = dwenfueg
                                    $(function () {
                                        $(".sousuo").click(function () {
                                            //查询输入不允许为空
                                            if($("#sousuoValue").val() == "" || $("#sousuoValue").val() == null){
                                                $("#searchbox").addClass("glyphicon glyphicon-remove form-control-feedback");
                                                return false;
                                            }
                                            $("#searchbox").removeClass("glyphicon glyphicon-remove form-control-feedback");
                                            var value = $("#sousuoValue").val();
                                            var name = $("#sousuoValue").attr("name");
                                            var url = $(this).attr("href") + "?sousuo=" + $(this).html() + "&" + name + "=" + value;
                                            $(this).attr("href",url);
                                        })
                                    })
                                </script>
                        </div><!-- /.col-lg-6 -->
                    </div><!-- /.row -->
                    <!--列表-->
                    <table class="table table-hover table-striped table-condensed">
                        <thead>
                        <tr>
                            <th class="col-sm-2">
                                编号
                            </th>
                            <th class="col-sm-2">
                                黑方
                            </th>
                            <th class="col-sm-2">
                                白方
                            </th>
                            <th class="col-sm-2">
                                AI结果
                            </th>
                            <th class="col-sm-2">
                                时间
                            </th>
                        </tr>
                        </thead>
                        <tbody id="ai-list">
                        <%for(int i=0;i<List.size();++i){%>
                        <tr>
                            <td><%=List.get(i).getId()%></td>
                            <td><%=List.get(i).getBlack()%></td>
                            <td><%=List.get(i).getWhite()%></td>
                            <%String buffer =null;
                                switch (List.get(i).getWin()){
                                case -1:buffer = HTML.textb("错误","black");break;
                                case 0:buffer = HTML.textb("失败","red");break;
                                case -2:buffer = HTML.textb("平局","blue");break;
                                default:buffer = HTML.textb("胜利","green");
                            }
                            %>
                            <td><a class='modify' href="#" id="<%=List.get(i).getId()%>"><%=buffer%></a></td>
                            <td>
                                <%=List.get(i).getTime()==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(List.get(i).getTime())%>
                            </td>
                        </tr>
                        <%}%>
                        </tbody>
                    </table>

                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-4">
                            <ul class="pagination pagination-sm ">
                                <li>
                                    <a href="aigame_aiBattleRecord.jsp?page=<%=Math.max(pa-1,1)%>&id=<%=aiId%>"><<</a>
                                </li>
                                <li>
                                    <a href="#"><%=""+pa+" / "+totalpage%></a>
                                </li>
                                <li>
                                    <a href="aigame_aiBattleRecord.jsp?page=<%=Math.min(pa+1,totalpage)%>&id=<%=aiId%>">>></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!-- ai新增-->
                <!--新增弹出框-->
                <div class="modal fade" id="repModal" tabindex="-1" role="dialog" aria-labelledby="rexampleModalLabel">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <h4 class="modal-title" id="rexampleModalLabel"><span class="glyphicon glyphicon-book"></span>   对战记录</h4>
                            </div>
                            <div class="modal-body rep">

                            </div>
                        </div>
                    </div>
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
