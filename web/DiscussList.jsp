<%@ page import="util.Main" %>
<%@ page import="util.HTML.DiscussHTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/4 0004
  Time: 11:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Main.saveURL();
//  if(session.getAttribute("user")==null){
//    response.sendRedirect("Login.jsp");
//    return;
//  }
  int num= Main.contestShowNum;
  String pa=request.getParameter("page");
  if(pa==null||pa.equals("")) pa="1";
  String seach=request.getParameter("seach");
  String user=request.getParameter("user");
  if(seach!=null){//处理中文乱码
    byte source [] = seach.getBytes("iso8859-1");
    seach = new String (source,"UTF-8");
  }
%>

<!-- 配置文件 -->
<script type="text/javascript" src="module/UEditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="module/UEditor/ueditor.all.js"></script>
<html>
<head>
  <title>讨论列表 - T^T Online Judge</title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp?page=home"/>
    <%=DiscussHTML.DiscussList(-1,num,Integer.parseInt(pa),seach,user)%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<script>
  $("#adddiscussForm").validate({
  onfocusout: false,
  rules: {
    title: {
      required: true
    }
  },
  messages: {
    title: {
      required: "标题不能为空"
    }
  }
});
</script>