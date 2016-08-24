<%@ page import="entity.User" %>
<%@ page import="util.HTML.HTML" %>
<%@ page import="dao.MessageSQL" %>
<%@ page import="action.ClockIn" %>
<%@ page import="ClockIn.*" %>
<script>

  if(location.href.indexOf("acm.fjut.edu.cn/vj")!=-1){
    location.href=location.href.replace("acm.fjut.edu.cn","210.34.193.66:8080");
  }else if(location.href.indexOf("acm.fjut.edu.cn")!=-1){
    location.href=location.href.replace("acm.fjut.edu.cn","210.34.193.66:8080/vj");
  }
</script>
<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.css">
<link href="js/bootstrap-datepicker-master/datepicker.css" rel="stylesheet">
<link href="js/modern-menu/modern-menu.css" rel="stylesheet">
<link rel="stylesheet" href="js/front-awesome/font-awesome.min.css">
<link rel="stylesheet" href="css/main.css">
<link href="css/loaders.css" rel="stylesheet">

<script src="js/jquery-1.11.1.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
<script src="js/bootstrap-datepicker-master/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="js/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/dateFormat.js"></script>
<script src="js/modern-menu/jquery.transit.min.js"></script>
<script src="js/modern-menu/jquery.modern-menu.min.js"></script>
<script src="js/HTML.js"></script>

<script type="text/javascript" src="module/UEditor/ueditor.config.js"></script>
<script type="text/javascript" src="module/UEditor/ueditor.all.js"></script>

<script src='js/problemTag.js'></script>

<div class="row"><div class="col-sm-3 col-sm-offset-9">

</div></div>
<div class="panel panel-default" style="margin-top: 30px">
  <ul class="modern-menu theme8" style="width:100%">
    <%--<ul id="nav" >--%>
    <%--<%--%>
    <%--String q1 = request.getParameter("page");--%>
    <%--if(q1==null) q1="";--%>
    <%--%>--%>
    <li><a href="index.jsp"><span>HOME</span></a></li>
    <li><a href="ProblemSet.jsp"><span>PROBLEM</span></a></li>
    <li><a href="Status.jsp"><span>STATUS</span></a></li>
    <li><a href="Contests.jsp"><span>CONTEST</span></a>
      <ul>
        <li><a href="Contests.jsp?kind=0"><span>练习</span></a></li>
        <li><a href="Contests.jsp?kind=1"><span>积分</span></a></li>
        <li><a href="Contests.jsp?kind=2"><span>趣味</span></a></li>
        <li><a href="Contests.jsp?kind=3"><span>正式</span></a></li>
        <%--<li><a href="ExamList.jsp"><span>考试</span></a></li>--%>
      </ul>
    </li>
    <li><a href="User.jsp"><span>RANK</span></a></li>
    <li><a href="DiscussList.jsp"><span>DISCUSS</span></a></li>
    <li><a href="Challenge.jsp"><span>CHALLENGE</span></a></li>
    <li><a href="ClockIn.jsp"><span <%
    if(ClockInSQL.mustClockIn()>=0){
    out.print("style='background:red;color:white'");
    }
    %>>签到</span></a></li>
    <%
      String ss="";
      if(session.getAttribute("user")!=null){
        String s=((User)session.getAttribute("user")).getUsername();
        int messnoread=MessageSQL.getNoRead(s);
        ss+="<li  class=' mm-right' style='min-width:135px'>";//float:right; min-width: 115px;
        ss+=HTML.a("UserInfo.jsp?user="+s,"<span><i class='icon-user'></i> "+s+(messnoread==0?"":"<text class='badge'>"+messnoread+"</text>")+"</span>");
        ss+="<ul>";
        if(messnoread>0) ss+="<li>"+HTML.a("Message.jsp","<span><i class='icon-envelope-alt'></i> MESSAGE<text class='badge'>"+messnoread+"</text></span>")+"</li>";
        else ss+="<li>"+HTML.a("Message.jsp","<span><i class='icon-envelope-alt'></i> MESSAGE</span>")+"</li>";
        ss+="<li>"+HTML.a("EditInfo.jsp","<span><i class='icon-edit'></i> EDIT")+"<span></li>";
        ss+="<li>"+" <a href='Logout.jsp'><span><i class='icon-signout'></i> LOGOUT</span></a>"+"</li>";
        ss+="</ul></li>";
      }else{
        ss+="<li style='float:right'><a href='Register.jsp'><span> Register</span></a></li><li style='float:right'><a href='Login.jsp'><span><i class='icon-signin'></i> Login</span></a></li>";
      }
      out.print(ss);
    %>
  </ul>
</div>
<script type="text/javascript">
  $(".modern-menu").modernMenu();
  var ch={//对应翻译
    HOME:"主页",
    PROBLEM:"题目",
    STATUS:"评测",
    CONTEST:"比赛",
    RANK:"排名",
    DISCUSS:"讨论",
    CHALLENGE:"挑战模式",
    Login:"登录",
    Register:"注册",
    MESSAGE:"消息",
    EDIT:"编辑",
    LOGOUT:"退出"
  };
  $(".mm-over,.mm-hdrop-over").each(function () {
    var $th=$(this);
    //alert($th.text());
    //if(ch[$th.html()]) $th.text(ch[$th.html()]);
    var ps=$th.html();
    if(ps.indexOf("class=\"icon-user\"")<0){//排除User显示
      for(var s in ch){
        ps=ps.replace(s,ch[s]);
      }
      $th.html(ps);
    }
  });
</script>