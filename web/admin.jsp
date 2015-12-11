<%@ page import="util.HTML.HTML" %>
<%@ page import="entity.Permission" %>
<%@ page import="util.Main" %>
<%@ page import="entity.User" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/6/14 0014
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String pa=request.getParameter("page");
    Object user=session.getAttribute("user");
    if(session.getAttribute("user")==null){
        response.sendRedirect("Login.jsp");
        return;
    }
    Permission per=null;
    if(user!=null) per=Main.getPermission(((User)user).getUsername());
    else response.sendRedirect("Login.jsp");

    if(pa!=null&&(pa.equals("AddTag")||pa.equals("PermissionAdmin")||pa.equals("ChallengeAdmin"))){
        Main.saveURL();
    }
%>
<html>
<head>
    <title>管理 - T^T Online Judge</title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
      <div style='width:14%;float:left' class="adminNAV">
          <%=HTML.adminNAV(per,pa)%>
      </div>
      <div style='width:84%;float:right'>
          <%=HTML.adminMAIN(per, pa)%>
      </div>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>

<!-- 配置文件 -->
<script type="text/javascript" src="module/UEditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="module/UEditor/ueditor.all.js"></script>
<!-- 实例化编辑器 -->
<script type="text/javascript">
    var ue = UE.getEditor('container');
</script>
