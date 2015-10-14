<%@ page import="java.sql.Timestamp" %>
<%@ page import="Tool.HTML.HTML" %>
<%@ page import="Main.Main" %>
<!--
  所需参数：cid  不能为空
-->
<%--<script src="js/sco.countdown.js"></script>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String cid = request.getParameter("cid");
  if(cid!=null){
    int qcid=Integer.parseInt(cid);
    String pa=null;
    Timestamp now=new Timestamp(System.currentTimeMillis());
    boolean start= Main.contests.getContest(Integer.parseInt(cid)).getBeginDate().before(now);
    Object user=session.getAttribute("user");
%>
<div class="row"><div class="col-md-12">
  <%--<div class="row"><h2 class="text-center"><%out.print(Main.contests.getContest(q1).getName());%></h2></div>--%>
   <%=Main.contests.getContest(qcid).getHomeHTML()%>
    <%if(start){%>
  <div class="row"><div class="col-md-8 col-md-offset-2"><%=HTML.problemList(cid, user)%></div></div>
  <%}%>
</div></div>
<%}else{
  out.print("Wrong Parameters");
}%>
