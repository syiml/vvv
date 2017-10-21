<%@ page import="entity.User" %>
<%@ page import="util.HTML.HTML" %>
<%@ page import="dao.MessageSQL" %>
<%@ page import="action.ClockIn" %>
<%@ page import="ClockIn.*" %>
<%@ page import="util.Main" %>
<script>
  if(location.href.indexOf("acm.fjut.edu.cn")!=-1) {
    location.href = location.href.replace("acm.fjut.edu.cn", "59.77.139.92");
  }
</script>
<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%--<link rel="stylesheet" href="bootstrap-3.3.4-dist/css/bootstrap.css">--%>
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<%--<link href="js/bootstrap-datepicker-master/datepicker.css" rel="stylesheet">--%>
<link href="https://cdn.bootcss.com/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker.css" rel="stylesheet">

<link href="js/modern-menu/modern-menu.css" rel="stylesheet">
<link rel="stylesheet" href="js/front-awesome/font-awesome.min.css">
<link rel="stylesheet" href="css/main.css">
<link href="css/loaders.css" rel="stylesheet">

<%--<script src="js/jquery-1.11.1.js"></script>--%>
<script src="https://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>

<%--<script src="js/jquery-ui.min.js"></script>--%>
<script src="https://cdn.bootcss.com/jqueryui/1.12.1/jquery-ui.min.js"></script>

<%--<script src="bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>--%>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<%--<script src="js/bootstrap-datepicker-master/bootstrap-datepicker.js"></script>--%>
<script src="https://cdn.bootcss.com/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.min.js"></script>

<%--<script type="text/javascript" src="js/jquery.validate.min.js"></script>--%>
<script type="text/javascript" src="https://cdn.bootcss.com/jquery-validate/1.17.0/jquery.validate.min.js"></script>

<script type="text/javascript" src="js/dateFormat.js"></script>

<%--<script src="js/modern-menu/jquery.transit.min.js"></script>--%>
<script src="https://cdn.bootcss.com/jquery.transit/0.9.12/jquery.transit.min.js"></script>

<script src="js/modern-menu/jquery.modern-menu.min.js"></script>
<script src="js/HTML.js"></script>

<script type="text/javascript" src="module/UEditor/ueditor.config.js"></script>

<script type="text/javascript" src="module/UEditor/ueditor.all.min.js"></script>
<%--<script type="text/javascript" src="http://ueditor.baidu.com/ueditor/ueditor.all.min.js"></script>--%>


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
        <li><a href="Contests.jsp?kind=5"><span>DIY</span></a></li>
        <%--<li><a href="ExamList.jsp?kind=4"><span>考试</span></a></li>--%>
      </ul>
    </li>
    <li><a href="User.jsp"><span>RANK</span></a>
      <ul>
        <li><a href="Awards.jsp"><span>荣誉榜</span></a></li>
        <li><a href="WeekRank.jsp"><span>活跃榜</span></a></li>
        <li><a href="User.jsp?status=1"><span>现役榜</span></a></li>
        <li><a href="GroupRank.jsp"><span>组队榜</span></a></li>
      </ul>
    </li>
    <li><a href="DiscussList.jsp"><span>DISCUSS</span></a></li>
    <li><a href="Challenge.jsp?3.16"><span>CHALLENGE</span></a></li>
    <li><a href="mall.jsp"><span>MALL</span></a> </li>
    <%--<li><a href="ClockIn.jsp"><span--%>
    <%--<%if(ClockInSQL.mustClockIn()>=0){--%>
    <%--out.print(" style='background:red;color:white'");--%>
    <%--}%>--%>
    <%-->签到</span></a></li>--%>
    <%
      String ss="";
      User u = Main.loginUser();
      if(u!=null){
        String s=u.getUsername();
        int messnoread=MessageSQL.getNoRead(s);
        ClockInMain.autoClockIn();
        ss+="<li  class=' mm-right' style='min-width:135px'>";//float:right; min-width: 115px;
        ss+=HTML.a("UserInfo.jsp?user="+s,"<span><i class='icon-user'></i> "+s+(messnoread==0?"":"<text class='badge'>"+messnoread+"</text>")+"</span>");
        ss+="<ul>";
        if(messnoread>0) ss+="<li>"+HTML.a("Message.jsp","<span><i class='icon-envelope-alt'></i> MESSAGE<text class='badge'>"+messnoread+"</text></span>")+"</li>";
        else ss+="<li>"+HTML.a("Message.jsp","<span><i class='icon-envelope-alt'></i> MESSAGE</span>")+"</li>";
        ss+="<li>"+HTML.a("EditInfo.jsp","<span><i class='icon-edit'></i> EDIT")+"<span></li>";
        ss+="<li>"+HTML.a("Verify.action","<span><i class='icon-check'></i> VERIFY")+"<span></li>";
        ss+="<li>"+HTML.a("dmc.jsp","<span><i class='icon-calendar'></i> CLOCKIN</span>")+"<span></li>";
        ss+="<li>"+HTML.a("Title.jsp","<span><i class='icon-tag'></i> TITLE")+"<span></li>";
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
    LOGOUT:"退出",
    MALL:"商城",
    VERIFY:"认证",
    CLOCKIN:"签到",
    TITLE:"称号"
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