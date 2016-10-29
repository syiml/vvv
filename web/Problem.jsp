<%@ page import="util.Main" %>
<%@ page import="util.HTML.HTML" %>
<%@ page import="org.apache.struts2.ServletActionContext" %>
<%@ page import="entity.User" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/23
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>
<%--
  所需参数:pid
--%>
<%
  Main.saveURL();
  String pid = request.getParameter("pid");
  User user=Main.loginUser();
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>题目 - <%=Main.config.OJName%></title>
    <%--<script type="text/javascript" src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>--%>
    <script type="text/javascript" src="http://acm.hdu.edu.cn/js/MathJax/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
    <%--<script type="text/javascript" src="js/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>--%>
    <%--<script type="text/javascript" src="js/MathJax/MathJax.js?config=MMLorHTML"></script>--%>
</head>
<body><div class="container-fluid">
  <jsp:include page="module/head.jsp"/>
  <%=HTML.problem(user,null,pid)%>
  </div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<script src='js/problemTag.js'></script>
