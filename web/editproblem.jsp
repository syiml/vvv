<%@ page import="entity.User" %>
<%@ page import="entity.Permission" %>
<%@ page import="util.HTML.HTML" %>
<%@ page import="util.Main" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/6/27 0027
  Time: 19:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  if(Main.loginUser()==null){
    response.sendRedirect("Login.jsp");
    return;
  }
  String pid=request.getParameter("pid");
  String edit=request.getParameter("edit");
  String num=request.getParameter("num");
  User user=Main.loginUser();
  if(user==null) response.sendRedirect("Login.jsp");
  else{
    Permission p= Main.getPermission(user.getUsername());
%>
<!-- 配置文件 -->
<script type="text/javascript" src="module/UEditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="module/UEditor/ueditor.all.js"></script>
<html>
<head>
    <title>编辑题目 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
      <%=HTML.editProblem(pid,edit,num)%>
      <%--<form>--%>
        <%--<textarea name="s" class="form-control" cols="100" rows="10" id="s" placeholder="Input HTML code"><%=1%></textarea>--%>
        <%--<%=HTML.panel("预览","<div id='view' style='word-break:break-all'></div>")%>--%>
        <%--<input type="submit" class="form-control" value="Submit"/>--%>
      <%--</form>--%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<%--<script>--%>
  <%--$s=$('#s');--%>
  <%--$s.on('keyup', function(){--%>
    <%--$('#view').html($('#s').val());--%>
  <%--});--%>
  <%--$('#view').html($s.val());--%>
<%--</script>--%>
<%}%>