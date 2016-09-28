<%@ page import="entity.User" %>
<%@ page import="util.HTML.HTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/6/27 0027
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  User user=(User)(session.getAttribute("user"));
  if(user==null){
    response.sendRedirect("Login.jsp");
  }else{
%>
<html>
<head>
  <title>修改用户信息 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
    <div class="row"><div class="col-sm-8 col-sm-offset-2"><%=HTML.panel("Edit", HTML.editInfoForm(user).toHTML())%></div></div>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<%--
form:id=edit
  password:  id=pass
  newpass:   id=newpass
  renewpass: id=renewpass
  NickName:  id=nick
  school:    id=school
  email:     id=email
  motto:     id=motto
--%>

<script type="text/javascript">
  $().ready(function() {

    $("#edit").validate({
      rules: {
        pass: {
          required: true
        },
        newpass: {
          minlength: 5,
          maxlength: 15
        },
        renewpass: {
          minlength: 5,
          maxlength: 15,
          equalTo: "#newpass"
        },
        nick: {
          maxlength: 20
        },
        email: {
          required: false,
          email: true
        },
        school: {
          maxlength: 30
        },
        motto: {
          maxlength: 50
        },
        phone:{
          number:true,
          minlength: 11,
          maxlength: 11
        },
        no:{
          number:true,
          minlength: 10,
          maxlength: 10
        },
        cla:{
          number:true,
          minlength:4,
          maxlength:4
        }
      },
      messages: {
        pass: {
          required: "密码不能为空"
        },
        newpass: {
          minlength: "密码至少要5个字符的长度",
          maxlength: "密码最多只能15个字符长度"
        },
        renewpass: {
          equalTo: "两次密码输入不一致",
          minlength: "密码至少要5个字符的长度",
          maxlength: "密码最多只能15个字符长度"
        },
        email: "请输入一个合法的email地址",
        phone:"请输入一个合法的11位电话号码",
        no:"请输入一个合法的学号(10位数字)",
        cla:"请输入合法的班级号（4位数字）"
      }
    });
  });
</script>
<%}%>
