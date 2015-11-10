<%@ page import="Challenge.ChallengeJSON" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/10/10 0010
  Time: 22:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Main.Main.saveURL();
  if(session.getAttribute("user")==null){
    response.sendRedirect("Login.jsp");
    return;
  }
%>
<link rel="stylesheet" href="css/challenge.css">
<script>
  var blockList=<%=ChallengeJSON.getBlockList(request.getParameter("user"))%>;
  //{blockList:[{id,name,group,score,isOpen,userScore},...],isSelf}
</script>
<html>
<head>
    <title>挑战模式 - T^T Online Judge</title>
</head>
<body>
<div class="container-fluid">
  <jsp:include page="module/head.jsp?page=home"/>
  <div id="challenge-main"></div>
  <div id="challenge-block"></div>
  <div id="info"></div>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<script src="js/challenge.js"></script>
<script src="js/status.js"></script>