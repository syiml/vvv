<%@ page import="util.Main" %>
<%@ page import="entity.User" %>
<%@ page import="util.HTML.HTML" %>
<%@ page import="entity.Permission" %>
<%@ page import="entity.Contest" %>
<%@ page import="util.Tool" %>
<%@ page import="servise.ContestMain" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/23
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  所需参数：cid
            session.user

            user
            pid
            result
            lang
            page
--%>
<!DOCTYPE html>
<html>
<head>
  <title>比赛 - <%=Main.config.OJName%></title>
</head>
<body><div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
<%
  Main.saveURL();
  Object user=session.getAttribute("user");
  String cid=request.getParameter("cid");
  String password=request.getParameter("password");
  String username=request.getParameter("username");
  int qcid=0;

  String pa=request.getParameter("page");
    if(pa==null) pa="0";
  String ssuser=request.getParameter("user");
  String pid=request.getParameter("pid");
  String result=request.getParameter("result");
  String lang=request.getParameter("lang");
    if(ssuser==null) ssuser="";
    if(pid==null) pid="";
    if(result==null) result="-1";
    if(lang==null) lang="-1";
  String main=request.getParameter("main");
    if(main==null) main="";
  boolean bo=false;
  Contest contest = null;
  /*if(user==null){//未登录跳转
    response.sendRedirect("Login.jsp");
  }else*/
  if(cid==null){//参数错误
    out.print("Wrong Parameter");
  }else if(password!=null){//提交密码到session
      session.setAttribute("contestpass"+cid,password);
      session.setAttribute("contestusername"+cid,username);
      response.sendRedirect("Contest.jsp?cid="+cid);
  }else{
      qcid = Integer.parseInt(cid);
      contest = ContestMain.getContest(qcid);
      int in = contest.canin(((User)user));
      if (in == 0) {
        out.print("没有权限，请"+HTML.a("Login.jsp","登录")+"或报名比赛后再进入");
      } else if (in == -1 || in == -2) {//need password
%>
    <%-- 密码模块 --%>
    <div class="col-sm-6 col-sm-offset-3"><div class="panel panel-default">
        <div class="panel-heading">输入密码</div>
        <div class="panel-body">
            <form class="form-horizontal" action="Contest.jsp" method="get">
                <%if(in==-2){%>
                <div class="form-group">
                    <label for="Username" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="Username" placeholder="请使用主办方提供的账号密码登录" name="username">
                    </div>
                </div>
                <%}%>
                <div class="form-group">
                    <label for="Password" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="Password" placeholder="" name="password">
                    </div>
                </div>
                <div class="form-group hidden">
                    <label for="Password" class="col-sm-2 control-label">cid</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="cid" placeholder="cid" name="cid" value=<%=cid%> readonly>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">进入比赛</button>
                    </div>
                </div>
            </form>
        </div>
    </div></div>
<%
      } else if(in == 1){
        bo=true;
      }
  }
  if(bo){
%>
    <script>
      if(location.pathname=="/sb.action"){
        window.location.replace("/Contest.jsp?cid=<%=cid%>#Status");
      }
    </script>

      <h2 style="text-align:center"><%=ContestMain.getContest(qcid).getName()%></h2>
        <%//进度条
            long time= Tool.now().getTime()-contest.getBeginDate().getTime();
            long alltime=contest.getEndTime().getTime() - contest.getBeginDate().getTime();
            if(time>=0&&time<=alltime){
                out.print(HTML.progress(time, alltime, "contest_pro",""));
                out.print(HTML.center(HTML.time_djs((alltime-time)/1000,"contest_djs")));
            }
        %>
        <div style="float:right"><%
            Permission p;
            if(user == null)
                p = new Permission();
            else {
                p = Main.getPermission(((User) user).getUsername());
            }
            if(p.getAddContest()){
                out.print(HTML.a("admin.jsp?page=AddContest&cid="+cid,"Edit")+" ");
                if(p.getAddContest()&&p.getAddProblem())
                    out.print(HTML.a("problemPublic.action?cid="+cid,"题目public"));
            }
        %></div>
      <%--<%@include file="module/contestModule/main.jsp"%>--%>
        <script>var js_cid=<%=cid%>;</script>
        <%@include file="module/contestNew/main.html"%>
<%}%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<script>
    $(startWebSocket(<%=cid%>));
</script>