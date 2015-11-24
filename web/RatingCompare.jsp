<%@ page import="util.HTML.HTML" %>
<%@ page import="util.HTML.FromHTML.FormHTML" %>
<%@ page import="util.HTML.FromHTML.text.text" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/23 0023
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String user=request.getParameter("user");
  String user2=request.getParameter("user2");
  boolean t=(request.getParameter("true")!=null);
%>
<html>
<head>
  <title>Rating Compare - T^T Online Judge</title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp?page=home"/>
      <%
        FormHTML f=new FormHTML();
        text t1=new text("user","user1");
        t1.setValue(user);
        text t2=new text("user2","user2");
        t2.setValue(user2);
        f.addForm(t1);
        f.addForm(t2);
        f.setType(1);
        f.setAction("RatingCompare.jsp");
      %>
      <%=
        HTML.panelnobody("Rating"+HTML.floatRight(f.toHTML()),"<div id='show'></div>")
      %>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>
<script type="text/javascript" src="js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="js/highcharts/theme.js"></script>
<script>
  <%if(t){%>
  $('#show').load('module/ratingshow.jsp?user=<%=user%>&user2=<%=user2%>&true=1');
  <%}else{%>
  $('#show').load('module/ratingshow.jsp?user=<%=user%>&user2=<%=user2%>');
  <%}%>
</script>