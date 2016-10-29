<%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2016/6/30 0030
  Time: 22:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="util.Main" %>
<%@ page import="dao.statusSQL" %>
<%@ page import="util.HTML.HTML" %>
<%@ page import="entity.User" %>
<%@ page import="util.HTML.TeamAwardList" %>
<!DOCTYPE html>
<%--
  参数：page
--%>
<%
  Main.saveURL();
  String pa=request.getParameter("page");
  int paInt=1;
  if(pa!=null) paInt=Integer.parseInt(pa);
%>
<html>
<head>
  <title>评测状态 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
  <jsp:include page="module/head.jsp?page=status"/>
  <%=new TeamAwardList(paInt).HTML()%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<script src="js/jquery-ui.min.js"></script>
<script src="js/autoRefreshTable.js"></script>
<script>
  function AwardAdmin(id,time,username1,username2,username3,name1,name2,name3,lv1,lv2,text){
    $('#AwardAdmin').modal();
    $("[name='awardTime_d']").val(time);
    $("[name='id']").val(id);
    $("[name='username1']").val(username1);
    $("[name='username2']").val(username2);
    $("[name='username3']").val(username3);
    $("[name='naem1']").val(name1);
    $("[name='name2']").val(name2);
    $("[name='name3']").val(name3);
    $("[name='contestLevel']").val(lv1);
    $("[name='awardLevel']").val(lv2);
    $("[name='text']").val(text);
  }
</script>