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

    int pa=1;
    try{
        pa = Integer.parseInt(request.getParameter("page"));
    }catch(NumberFormatException e)
    {   }
    String aiName = request.getParameter("aiName");
    if (aiName == null) aiName ="";
    int gameInt = -1;
    try{
        gameInt = Integer.parseInt(request.getParameter("game_id"));
    }catch(NumberFormatException e)
    {   }
    List<AiInfo> aiList = new AiJsonGetAiList(Main.loginUser().getUsername(),gameInt,aiName,pa,10).jspGetList();
    int totalpage = new AiJsonGetAiList(Main.loginUser().getUsername(),gameInt,aiName,pa,10).jspGetTotalPage();
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.min.css">
    <script type="text/javascript" language="JavaScript" src="js/jquery-1.11.1.js"></script>
    <script type="text/javascript" language="JavaScript" src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" language="JavaScript">
        $(function () {
        //获取点击修改的列表的id值
            var modifyid;
            $(".modify").click(function(){
                modifyid = $(this).attr("id");
            });
            $('#update-exampleModalRedirest').on('show.bs.modal', function (e) {
                // do something...
               //使用ajax发送请求,响应数据
                $.post("http://localhost:8080/getEditAiView?id=" + modifyid,
                    function(data){

                        //aiid
                        $("#aiid").val(data.id);
                        //ai名
                        $("#update-redist-name").val(data.aiName);
                        //简介
                        $("#update-jj").html(data.introduce);
                        //代码
                        $("#update-code").html(data.code);
                    }, "json");
            })
            //查看简介
            $('#exampleModalLogin').on('show.bs.modal', function (e) {
                // do something...

                //使用ajax发送请求,响应数据
                $.post("http://localhost:8080/getEditAiView?id=" + modifyid,
                    function(data){
                        $("#look_name").html(data.aiName);
                        $("#look_jj").html(data.introduce);
                    }, "json");
            })
        });
    </script>
    <title>用户AI管理</title>
</head>
<body class="container">
<br>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <nav class="navbar navbar-default" role="navigation">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"> <span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
                    <img src="pic/top1.jpg" class="img-rounded" width="73px" height="50px">
                </div>

                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav">
                        <li>
                            <a href="#">&nbsp;&nbsp;<b>AI</b> &nbsp;&nbsp;</a>
                        </li>
                        <li class="active">
                            <a>&nbsp;&nbsp;用户界面&nbsp;&nbsp;</a>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a>时间：<span id="time"></span></a>
                            <!--获取时间-->
                            <script type="text/javascript" language="JavaScript">
                                    $(function(){
                                        setInterval(function(){
                                            var time = "";
                                            var date = new Date();
                                            time += date.getFullYear() + "年" + (date.getMonth()+1) + "月" + date.getDate() + "日 "+ date.getHours() +"："+ date.getMinutes() +"："+ date.getSeconds();
                                            $("#time").html(time);
                                        },1000);
                                    });
                                </script>
                        </li>

                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">个人 <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">切换账号</a></li>
                                <li><a href="#">修改用户</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="#">退出账号</a></li>
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
                                <span class="glyphicon glyphicon-user"></span>  <span id="admin">1234567</span>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-84243" href="#panel-element-867475">AI 管理</a>
                            </div>
                            <div id="panel-element-867475" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <a href="#" class="text-primary">AI 编辑</a>
                                </div>
                                <div class="panel-body">
                                    <a href="#" class="text-primary">列表1-2</a>
                                </div>
                                <div class="panel-body">
                                    <a href="#" class="text-primary">列表1-3</a>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-84243" href="#panel-element-365878">列表2</a>
                            </div>
                            <div id="panel-element-365878" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <a href="#" class="text-primary">列表2-1</a>
                                </div>
                                <div class="panel-body">
                                    <a href="#" class="text-primary">列表2-2</a>
                                </div>
                                <div class="panel-body">
                                    <a href="#" class="text-primary">列表2-3</a>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <a class="panel-title collapsed" data-toggle="collapse" data-parent="#panel-84243" href="#panel-element-365874">列表3</a>
                            </div>
                            <div id="panel-element-365874" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <a href="#" class="text-primary">列表3-1</a>
                                </div>
                                <div class="panel-body">
                                    <a href="#" class="text-primary">列表3-2</a>
                                </div>
                                <div class="panel-body">
                                    <a href="#" class="text-primary">列表3-3</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-8 column embed-responsive embed-responsive-16by9">
                    <!--搜索框组-->
                    <div class="row">
                        <div class="col-sm-2">
                            <button class="btn btn-info btn-sm" data-toggle="modal" data-target="#exampleModalRedirest"><span class="glyphicon glyphicon-book"></span>&nbsp;&nbsp;新 增</button><br><br>
                        </div>
                        <div class="col-sm-8">
                            <!--用于发送搜索信息-->
                            <form class="form-inline" action="">
                                <div class="form-group">
                                    <div class="input-group">
                                        <div class="input-group-addon">AI名称</div>
                                        <input type="text" class="form-control" id="exampleInputAmount" placeholder="AI名称"name="aiName">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group">
                                        <div class="input-group-addon">类型</div>
                                        <select class="form-control" id="game_id" name="game_id">
                                            <option value="1" selected>五子棋</option>
                                        </select>
                                    </div>
                                </div>
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
                    <!-- ai新增-->
                    <!--新增弹出框-->
                    <div class="modal fade" id="exampleModalRedirest" tabindex="-1" role="dialog" aria-labelledby="rexampleModalLabel">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="rexampleModalLabel"><span class="glyphicon glyphicon-book"></span>   新增</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" action="http://localhost:8080/addAiInfo.action">
                                        <div class="form-group has-feedback">
                                            <label for="redist-name" class="control-label col-sm-2">AI名：</label>
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" id="redist-name" name="aiName" required>
                                            </div>
                                        </div>
                                        <div class="form-group text-center">
                                            <label for="game" class="control-label col-sm-2">游戏类型: </label>
                                            <div class="col-sm-3">
                                                <select class="form-control" id="game" name="game_id">
                                                    <option value="1" selected>五子棋</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group has-feedback">
                                            <label for="introduce" class="control-label col-sm-2">简介: </label>
                                            <div class="col-sm-9">
                                                <textarea class="form-control" id="introduce" name="introduce" required style="min-height: 150px"></textarea>
                                            </div>
                                        </div>
                                        <div class="form-group has-feedback">
                                            <label for="code" class="control-label col-sm-2">代码: </label>
                                            <div class="col-sm-9">
                                                <textarea class="form-control" id="code" name="code" required style="min-height: 150px"></textarea>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                            <button type="submit" class="btn btn-primary">上传</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--列表-->
                    <table class="table table-hover table-striped table-condensed">
                        <thead>
                        <tr>
                            <th class="col-sm-2">
                                编号
                            </th>
                            <th class="col-sm-2">
                                名称
                            </th>
                            <th class="col-sm-2">
                                游戏类型
                            </th>
                            <th class="col-sm-2">
                                胜率
                            </th>
                            <th class="col-sm-2">
                                简介
                            </th>
                            <th class="col-sm-2">
                                编辑
                            </th>
                        </tr>
                        </thead>
                        <tbody id="ai-list">
                        <%for(int i=0;i<aiList.size();++i){%>
                            <tr>
                                <td><%=i+1%></td>
                                <td><%=aiList.get(i).getAiName()%></td>
                                <%String type = "";
                                    switch (aiList.get(i).getGame_id()){
                                        case 1:type = "五子棋";break;
                                    }
                                %>
                                <td><%=type%></td>
                                <%int id = aiList.get(i).getId();
                                    int win = AiSQL.getInstance().getAiNumOfWin(id);
                                    int num = AiSQL.getInstance().getAiNumOfTotal(id);
                                    if (num == 0) num=1;
                                 %>
                                <td><%=(float)win*100/num %></td>
                            <td>
                                <button class="btn btn-default btn-sm modify" data-toggle="modal" data-target="#exampleModalLogin"  id=<%=id%> >查看</button>
                            </td>
                            <td>
                                <button class="btn btn-success btn-sm modify" data-toggle="modal" data-target="#update-exampleModalRedirest" id=<%=id%>>编辑</button>
                            </td>
                        </tr>
                        <%}%>
                        </tbody>
                    </table>
                    <!--编辑弹出标签-->
                    <div class="modal fade" id="update-exampleModalRedirest" tabindex="-1" role="dialog" aria-labelledby="update-rexampleModalLabel">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="update-rexampleModalLabel"><span class="glyphicon glyphicon-book"></span>   修改</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" action="http://localhost:8080/updateAiInfo">
                                        <input type="hidden" name="id" id="aiid">
                                        <div class="form-group has-feedback">
                                            <label for="update-redist-name" class="control-label col-sm-2">AI名：</label>
                                            <div class="col-sm-6">
                                                <input type="text" class="form-control" id="update-redist-name" name="aiName" required>
                                            </div>
                                        </div>
                                        <div class="form-group has-feedback">
                                            <label for="update-jj" class="control-label col-sm-2">简介: </label>
                                            <div class="col-sm-9">
                                                <textarea class="form-control" id="update-jj" name="introduce" required style="min-height: 150px"></textarea>
                                            </div>
                                        </div>
                                        <div class="form-group has-feedback">
                                            <label for="update-code" class="control-label col-sm-2">代码: </label>
                                            <div class="col-sm-9">
                                                <textarea class="form-control" id="update-code" name="code" required style="min-height: 150px"></textarea>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                            <button type="submit" class="btn btn-primary">修改</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!--查看简介弹出标签-->
                    <div class="modal fade" id="exampleModalLogin" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="exampleModalLabel"><span class="glyphicon glyphicon-eye-open"></span> <span id="look_name">简介</span></h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal">
                                        <div class="form-group has-feedback">
                                            <label for="look_jj" class="control-label col-sm-2">简介: </label>
                                            <div class="col-sm-9">
                                                <textarea class="form-control" id="look_jj" name="" required style="min-height: 150px"></textarea>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-4">
                            <ul class="pagination pagination-sm ">
                                <li>
                                    <a href="aigame_userMangeAi.jsp?page=<%=Math.max(pa-1,1)%>&aiName=<%=aiName%>&game_id=<%=gameInt%>>"><<</a>
                                </li>
                                <li>
                                    <a href="#"><%=""+pa+" / "+totalpage%></a>
                                </li>
                                <li>
                                    <a href="aigame_userMangeAi.jsp?page=<%=Math.min(pa+1,totalpage)%>&aiName=<%=aiName%>&game_id=<%=gameInt%>">>></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
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
            </div>
        </div>
    </div>
</div>
</body>
</html>
