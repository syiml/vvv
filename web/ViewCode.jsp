<%@ page import="entity.Status" %>
<%@ page import="util.Main" %>
<%@ page import="entity.Permission" %>
<%@ page import="util.HTML.HTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/6/8
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Main.saveURL();
  if(Main.loginUser()==null){
      response.sendRedirect("Login.jsp");
      return;
  }
  String rid = request.getParameter("rid");
  int type = 1;
  try{
      type = Integer.parseInt(request.getParameter("type"));
  }catch (Exception e){}
%>
<link href="js/prism/css.css" rel="stylesheet" />
<script src="js/prism/js.js"></script>
<html>
<head>
  <title>查看代码 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
    <%if(type==2){%>
        <%=HTML.viewAiCode(rid)%>
    <%}else{%>
        <%=HTML.viewCode(rid,true)%>
    <%}%>

</div><jsp:include page="module/foot.jsp"/>
</body>
</html>