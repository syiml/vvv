<%@ page import="util.HTML.HTML" %>
<%@ page import="ClockIn.ClockInHTML" %>
<%@ page import="util.Main" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/28 0028
  Time: 10:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
  .width100{
    width:100px;
  }
  .width10{
    width:10px;
  }

</style>
<html>
<head>
  <title>点名册 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
    <%=ClockInHTML.HTMLtable()%>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>
