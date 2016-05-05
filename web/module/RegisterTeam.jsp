<%@ page import="util.HTML.HTML" %>
<%@ page import="util.HTML.FromHTML.text.text" %>
<%@ page import="util.HTML.FromHTML.select.select" %>
<%@ page import="util.HTML.FromHTML.text_select.text_select2" %>
<%@ page import="util.HTML.FromHTML.hidden.hidden" %>
<%@ page import="entity.RegisterTeam" %>
<%@ page import="servise.ContestMain" %>
<%@ page import="util.Main" %>
<%@ page import="entity.TeamMember" %>
<%@ page import="util.Tool" %>
<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%
  int cid = Integer.parseInt(request.getParameter("cid"));
  String username = request.getParameter("username");
  if(username==null || username.equals("")){
    username =  Main.loginUser().getUsername();
  }else{
      if(!Main.loginUserPermission().getContestRegisterAdmin()){
          username =  Main.loginUser().getUsername();
      }
  }
  RegisterTeam rt = ContestMain.getRegisterTeam(cid, username);
  if(rt == null){
    rt=new RegisterTeam();
    rt.addMember(new TeamMember());
    rt.addMember(new TeamMember());
    rt.addMember(new TeamMember());
  }
%>
<div class="panel panel-default">
  <div class="panel-heading">比赛注册</div>
  <div class="panel-body">
    <form id="register_team" method="post" class="form-horizontal" action="RegisterTeam.action">
      <div class="row">
        <div class="col-xs-4">
          <!-- 基础信息——队名 -->
          <%=new text("teamName","队名").setId("teamName").setValue(rt.teamName).toHTML(2,10)%>
          <%=new hidden("cid",cid+"").toHTML()%>
          <%=new hidden("username",username).toHTML()%>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-4" style="padding-right: 0">
          <div class="panel panel-default" style="box-shadow:0 0">
            <div class="panel-body">
              <h3 style="padding-bottom: 15px">队长：</h3>
          <!-- 队员信息1 -->
          <%=new text("name1","姓名").setId("name1").setValue(rt.getMember(0).getName()).toHTML(2, 10)%>
          <%=new select("gender1","性别").add(0,"请选择").add(1,"男").add(2, "女").setValue(rt.getMember(0).getGender()+"").toHTML(2, 10)%>
          <%=new text_select2("school1","学校")
                  .add("福建工程学院")
                  .add("福州大学")
                  .add("福建师范大学")
                  .add("福建农林大学")
                  .add("闽江学院")
                  .setId("school1")
                  .setValue(rt.getMember(0).getSchool())
                  .toHTML(2, 10)%>
          <%=new text_select2("faculty1","学院")
                  .add("信息科学与工程学院")
                  .add("数理学院")
                  .add("国脉信息学院")
                  .setValue(rt.getMember(0).getFaculty())
                  .setId("faculty1").toHTML(2,10)%>
          <%=new text_select2("major1","专业")
                  .add("计算机科学与技术")
                  .add("软件工程")
                  .add("网络工程")
                  .add("信息管理与信息系统")
                  .add("物联网工程")
                  .setValue(rt.getMember(0).getMajor())
                  .setId("major1").toHTML(2, 10)%>
          <%=new text("cla1","班级").setId("cla1").setPlaceholder("如:1501")
                  .setValue(rt.getMember(0).getCla()).toHTML(2, 10)%>
          <%=new text("no1","学号").setId("no1").setPlaceholder("如:3150303333")
                  .setValue(rt.getMember(0).getNo()).toHTML(2, 10)%>
          <%=new text("phone1","电话").setId("phone1")
                  .setValue(rt.getMember(0).getPhone()).toHTML(2, 10)%>
            </div>
          </div>
        </div>
        <div class="col-xs-4" style="padding-right: 0">
          <div class="panel panel-default" style="box-shadow:0 0">
            <div class="panel-body">
              <h3 style="padding-bottom: 15px">队员1：</h3>
          <!-- 队员信息2 -->
          <%=new text("name2","姓名").setId("name2")
                  .setValue(rt.getMember(1).getName()).toHTML(2, 10)%>
          <%=new select("gender2","性别").add(0,"请选择").add(1, "男").add(2, "女")
                  .setValue(rt.getMember(1).getGender() + "").toHTML(2,10)%>
          <%=new text_select2("school2","学校")
              .add("福建工程学院")
              .add("福州大学")
              .add("福建师范大学")
              .add("福建农林大学")
              .add("闽江学院")
              .setValue(rt.getMember(1).getSchool())
              .setId("school2").toHTML(2,10)%>
          <%=new text_select2("faculty2","学院").setId("faculty2")
                  .add("信息科学与工程学院")
                  .add("数理学院")
                  .add("国脉信息学院")
                  .setValue(rt.getMember(1).getFaculty()).toHTML(2,10)%>
          <%=new text_select2("major2","专业").setId("major2")
                  .add("计算机科学与技术")
                  .add("软件工程")
                  .add("网络工程")
                  .add("信息管理与信息系统")
                  .add("物联网工程")
                  .setValue(rt.getMember(1).getMajor()).toHTML(2,10)%>
          <%=new text("cla2","班级").setId("cla2").setPlaceholder("如:1501")
                  .setValue(rt.getMember(1).getCla()).toHTML(2,10)%>
          <%=new text("no2","学号").setId("no2").setPlaceholder("如:3150303333")
                  .setValue(rt.getMember(1).getNo()).toHTML(2,10)%>
          <%=new text("phone2","电话").setId("phone2")
                  .setValue(rt.getMember(1).getPhone()).toHTML(2,10)%>
            </div>
          </div>
        </div>
        <div class="col-xs-4">
          <div class="panel panel-default" style="box-shadow:0 0">
            <div class="panel-body">
              <h3 style="padding-bottom: 15px">队员2：</h3>
          <!-- 队员信息3 -->
          <%=new text("name3","姓名").setId("name3")
                  .setValue(rt.getMember(2).getName()).toHTML(2, 10)%>
          <%=new select("gender3","性别").add(0,"请选择").add(1, "男").add(2, "女")
                  .setValue(rt.getMember(2).getGender() + "").toHTML(2,10)%>
          <%=new text_select2("school3","学校")
                .add("福建工程学院")
                .add("福州大学")
                .add("福建师范大学")
                .add("福建农林大学")
                .add("闽江学院")
                .setValue(rt.getMember(2).getSchool())
                .setId("school3").toHTML(2,10)%>
          <%=new text_select2("faculty3","学院").setId("faculty3")
                  .add("信息科学与工程学院")
                  .add("数理学院")
                  .add("国脉信息学院")
                  .setValue(rt.getMember(2).getFaculty()).toHTML(2,10)%>
          <%=new text_select2("major3","专业").setId("major3")
                  .add("计算机科学与技术")
                  .add("软件工程")
                  .add("网络工程")
                  .add("信息管理与信息系统")
                  .add("物联网工程")
                  .setValue(rt.getMember(2).getMajor()).toHTML(2,10)%>
          <%=new text("cla3","班级").setId("cla3").setPlaceholder("如:1501")
                  .setValue(rt.getMember(2).getCla()).toHTML(2,10)%>
          <%=new text("no3","学号").setId("no3").setPlaceholder("如:3150303333")
                  .setValue(rt.getMember(2).getNo()).toHTML(2,10)%>
          <%=new text("phone3","电话").setId("phone3")
                  .setValue(rt.getMember(2).getPhone()).toHTML(2,10)%>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-xs-12">
          <input id="submit" class="submit btn btn-primary" type="submit" value="确定">
        </div>
      </div>
    </form>
  </div>
</div>

<script type="text/javascript">
  $().ready(function() {
    $.validator.addMethod("AllNumber",function(value,element,params){
      if(params==false) return true;
      for(var i=0;i<value.length;i++){
        var c=value.charAt(i);
        if(!(c>='0'&&c<='9')){
          return false;
        }
      }
      return true;
    },"只能包含数字");

    $("#register_team").validate({
      onfocusout: false,
      rules: {
        teamName: {
          required: true,
          minlength: 2,
          maxlength: 20
          /*remote: {
            url: "module/usernamevalidate.jsp",     //后台处理程序
            type: "post",               //数据发送方式
            dataType: "json",           //接受数据格式
            data: {                     //要传递的数据
              username: function() {
                return $("#teamName").val();
              }
            }
          }*/
        },
        name1: {
          required: true,
          minlength: 1,
          maxlength: 5
        },
        name2: {
          minlength: 1,
          maxlength: 5
        },
        name3: {
          minlength: 1,
          maxlength: 5
        },
        school1:{
          minlength: 3,
          maxlength: 15
        },
        school2:{
          minlength: 3,
          maxlength: 15
        },
        school3:{
          minlength: 3,
          maxlength: 15
        },
        faculty1: {
          minlength: 2,
          maxlength: 15
        },
        faculty2: {
          minlength: 2,
          maxlength: 15
        },
        faculty3: {
          minlength: 2,
          maxlength: 15
        },
        major1:{
          minlength: 2,
          maxlength: 15
        },
        major2:{
          minlength: 2,
          maxlength: 15
        },
        major3:{
          minlength: 2,
          maxlength: 15
        },
        no1:{
            AllNumber:true,
          minlength: 5,
          maxlength:15
        },
        no2:{
          AllNumber:true,
          minlength: 5,
          maxlength:15
        },
        no3:{
          AllNumber:true,
            minlength: 5,
            maxlength:15
        },
        cla1:{
            AllNumber:true,
            minlength: 4,
            maxlength: 4
        },cla2:{
            AllNumber:true,
            minlength: 4,
            maxlength: 4
        },cla3:{
            AllNumber:true,
            minlength: 4,
            maxlength: 4
        },
        phone1:{
            AllNumber:true,
            minlength: 11,
            maxlength: 11
        },
        phone2:{
            AllNumber:true,
            minlength: 11,
            maxlength: 11
        },
        phone2:{
            AllNumber:true,
            minlength: 11,
            maxlength: 11
        }
      },
      messages: {
        teamName: {
          required: "队名不能为空",
          minlength: "队名长度为2~15个字符",
          maxlength: "队名长度为2~15个字符",
          remote:"用户名已经存在"
        },
        name1:{
          required: "队长不能为空"
        }
      }
    });
  });
</script>
