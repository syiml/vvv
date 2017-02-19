<%@ page import="util.HTML.HTML" %>
<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%
  if(request.getParameter("username")!=null){
    out.println("<script>alert('User name already exists, please use another user name')</script>");
  }
%>
<div class="panel panel-default">
  <div class="panel-heading">注册</div>
  <div class="panel-body">
    <%=HTML.registerForm()%>
  </div>
</div>

<script type="text/javascript">
  $().ready(function() {
    $.validator.addMethod("bsf",function(value,element,params){
      if(params==false) return true;
      for(var i=0;i<value.length;i++){
        var c=value.charAt(i);
        if(!((c>='a'&&c<='z')||(c>='A'&&c<='Z')||(c=='_')||(c>='0'&&c<='9'))){
            return false;
        }
      }
      return true;
    },"只能是字母数字和下划线");

    $("#register").validate({
      onfocusout: false,
      rules: {
        username: {
          required: true,
          minlength: 5,
          maxlength: 15,
          bsf:true,
          remote: {
              url: "module/usernamevalidate.jsp",     //后台处理程序
                      type: "post",               //数据发送方式
                      dataType: "json",           //接受数据格式
                      data: {                     //要传递的数据
                username: function() {
                  return $("#username").val();
                }
              }
            }
          },
        password: {
          required: true,
          minlength: 5,
          maxlength: 15
        },
        rpass: {
          required: true,
          minlength: 5,
          maxlength: 15,
          equalTo: "#password"
        },
        nick:{
          required: true,
          maxlength: 12
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
        }
      },
      messages: {
        nick: {
          required: "昵称不能为空",
          maxlength: "昵称最多只能有12个字符长度"
        },
        username: {
          required: "用户名不能为空",
          minlength: "用户名长度为5~15个字符",
          maxlength: "用户名长度为5~15个字符",
          remote:"用户名已经存在"
        },
        password: {
          required: "密码不能为空",
          minlength: "密码长度为5~15个字符",
          maxlength: "密码长度为5~15个字符"
        },
        rpass: {
          required: "重复密码不能为空",
          equalTo: "两次输入的密码不一致",
          minlength: "密码长度为5~15个字符",
          maxlength: "密码长度为5~15个字符"
        },
        email: "请输入一个合法的邮箱地址"
      }
    });
  });
</script>
