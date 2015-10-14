<%@ page import="Tool.HTML.HTML" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/9/28 0028
  Time: 21:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp?page=home"/>
      <%=HTML.headImg(Main.Main.loginUser().getUsername(),1)%>
      <%=HTML.headImg(Main.Main.loginUser().getUsername(),2)%>
      <%=HTML.uploadHead()%>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>
