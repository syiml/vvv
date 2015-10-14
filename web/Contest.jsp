<%@ page import="Main.Main" %>
<%@ page import="Main.User.User" %>
<%@ page import="Tool.HTML.HTML" %>
<%@ page import="Main.User.Permission" %>
<%@ page import="Main.contest.Contest" %>
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
  <title>比赛 - T^T Online Judge</title>
</head>
<body><div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
<%
  Main.saveURL();
  Object user=session.getAttribute("user");
  String cid=request.getParameter("cid");
  String password=request.getParameter("password");
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
  if(user==null){//未登录跳转
    response.sendRedirect("Login.jsp");
  }else if(cid==null){//参数错误
    out.print("Wrong Parameter");
  }else if(password!=null){//提交密码到session
      session.setAttribute("contestpass"+cid,password);
      response.sendRedirect("Contest.jsp?cid="+cid);
  }else{
      qcid = Integer.parseInt(cid);
      int in = Main.contests.getContest(qcid).canin(((User)user).getUsername());
      if (in == 0) {
        out.print("没有权限");
      } else if (in == -1) {//need password
        Object pass=session.getAttribute("contestpass"+cid);
        if(pass!=null && pass.toString().equals(Main.contests.getContest(qcid).getPassword())){//密码正确
          bo=true;
        }else{
%>
<%@include file="module/contestModule/password.jsp"%>
<%
        }
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

      <h2 style="text-align:center"><%=Main.contests.getContest(qcid).getName()%></h2>
        <%//进度条
            Contest c = Main.contests.getContest(qcid);
            long time=Main.now().getTime()-c.getBeginDate().getTime();
            long alltime=c.getEndTime().getTime() - c.getBeginDate().getTime();
            if(time>=0&&time<=alltime){
                out.print(HTML.progress(time, alltime, "contest_pro",""));
                out.print(HTML.center(HTML.time_djs((alltime-time)/1000,"contest_djs")));
            }
        %>
        <div style="float:right"><%
            Permission p=Main.getPermission(((User)user).getUsername());
            if(p.getAddContest())
                out.print(HTML.a("admin.jsp?page=AddContest&cid="+cid,"Edit"));
        %></div>
      <%--<%@include file="module/contestModule/main.jsp"%>--%>
        <script>var js_cid=<%=cid%>;</script>
        <%@include file="module/contestNew/main.html"%>
<%}%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<script src='js/problemTag.js'></script>