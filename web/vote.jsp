<%@ page import="util.Main" %>
<%@ page import="dao.VoteDao" %><%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2017/3/25
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新功能投票 - <%=Main.config.OJName%></title>
</head>
<body>
    <%=VoteDao.getInstance().getBeanByKey(Integer.parseInt(request.getParameter("did"))).toHTML()%>
</body>
</html>
