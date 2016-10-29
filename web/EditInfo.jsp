<%@ page import="entity.User" %>
<%@ page import="util.HTML.HTML" %>
<%@ page import="util.Main" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/6/27 0027
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  User user=Main.loginUser();
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
        }
      }
    });
  });
</script>
<%}%>
