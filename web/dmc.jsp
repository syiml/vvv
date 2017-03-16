<%@ page import="util.HTML.HTML" %>
<%@ page import="ClockIn.ClockInHTML" %>
<%@ page import="util.Main" %>
<%@ page import="util.Tool" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.Calendar" %>
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
    <%
        Main.saveURL();
        Calendar calendar = Calendar.getInstance();
        int l_year = calendar.get(Calendar.YEAR);
        int l_month = calendar.get(Calendar.MONTH)+1;
        int r_year = calendar.get(Calendar.YEAR);
        int r_month = calendar.get(Calendar.MONTH)+1;
        int r_day = calendar.get(Calendar.DAY_OF_MONTH);
        try {
            l_year = Integer.parseInt(request.getParameter("l_year"));
            l_month = Integer.parseInt(request.getParameter("l_month"));
            r_year = Integer.parseInt(request.getParameter("r_year"));
            r_month = Integer.parseInt(request.getParameter("r_month"));
            r_day = Integer.parseInt(request.getParameter("r_day"));
        }catch (Exception e){ }
    %>
    <%=ClockInHTML.HTMLCalendar(l_year,l_month,r_year,r_month,r_day)%>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>
