<%@ page import="util.Main" %>
<%@ page import="util.HTML.HTML" %>
<%@ page import="entity.UserVerifyInfo" %>
<%@ page import="servise.UserService" %>
<%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2016/10/15
  Time: 23:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>认证 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
<jsp:include page="module/head.jsp"/>
    <%
        boolean flag = true;
        UserVerifyInfo userVerifyInfo = UserService.verifySQL.getUserVerifyInfo(Main.loginUser().getUsername());
        if(userVerifyInfo!=null){
            out.print(HTML.col(8,2,HTML.panel("认证状态",HTML.showVerifyInfo(userVerifyInfo))));
            flag = false;
        }
        try{
            int id = Integer.parseInt(request.getParameter("id"));
            userVerifyInfo = UserService.verifySQL.getUserVerifyInfo(id);
            if(userVerifyInfo != null && userVerifyInfo.username.equals(Main.loginUser().getUsername())){
                out.print(HTML.col(8,2,HTML.panel("认证状态",HTML.showVerifyInfo(userVerifyInfo))));
                flag = false;
            }
        }catch (Exception e){}
        if(flag){
    %>
    <div class="col-xs-8 col-xs-offset-2">
        <div id="out">
        </div>
    </div>
    <script>
        $div = HTML.$panel($("#out"),"实名认证","<font color='red'>${prompt}</font>").find(".panel-body");
        $formToHTML($div,{
            col:[2,10],
            action:"VerifyCommit.action",
            method:"post",
            id:"edit",
            enctype:true,
            onSubmit:"",
            list:[{
                    type:"select",
                    name:"VerifyType",
                    id:"VerifyType",
                    label:"认证类型",
                    option:[
                        ["-1","请选择认证内容"],
                        ["0","申请修改个人信息（未认证用户直接修改）"],
                        ["1","申请为ACM集训队员"],
                        ["2","申请ACM退役"],
                        ["3","认证为中世竞创协会成员"],
                        ["4","认证为FJUT在校学生"]],
                    value:"${VerifyType}"
                },{
                    type:"text",
                    name:"name",
                    id:"name",
                    label:"真实姓名",
                    value:"${name}",
                    placeholder:"提示"
                },{
                    type:"select",
                    name:"gender",
                    id:"gender",
                    label:"性别",
                    option:[
                        ["0","请选择"],
                        ["1","男"],
                        ["2","女"]],
                    value:"${gender}"
                },{
                    type:"text_select",
                    name:"school",
                    id:"school",
                    label:"学校全称",
                    value:"${school}",
                    option:[
                        ["福建工程学院","福建工程学院"],
                        ["西南石油大学","西南石油大学"]
                    ]
                },{
                    type:"text_select",
                    name:"faculty_text",
                    id:"faculty_text",
                    label:"学院全称",
                    value:"${faculty_text}",
                    option:[
                        ["信息科学与工程学院","信息科学与工程学院"],
                        ["数理学院","数理学院"],
                        ["国脉信息学院","国脉信息学院"],
                        ["计算机科学学院","计算机科学学院"]
                    ]
                },{
                    type:"text_select",
                    name:"major_text",
                    id:"major_text",
                    label:"专业全称",
                    value:"${major_text}",
                    option:[
                        ["计算机科学与技术","计算机科学与技术"],
                        ["软件工程","软件工程"],
                        ["信息管理与信息系统","信息管理与信息系统"],
                        ["网络工程","网络工程"],
                        ["物联网工程","物联网工程"],
                        ["通信工程","通信工程"],
                        ["电子信息工程","电子信息工程"],
                        ["电气工程及其自动化","电气工程及其自动化"],
                        ["电子科学与技术","电子科学与技术"],
                        ["建筑电气与智能化","建筑电气与智能化"]
                    ]
                },{
                    type:"text",
                    id:"cla",
                    name:"cla",
                    label:"班级编号",
                    value:"${cla}"
                },{
                    type:"text",
                    name:"no",
                    id:"no",
                    label:"学号",
                    value:"${no}"
                },{
                    type:"text",
                    name:"phone",
                    id:"phone",
                    label:"联系方式",
                    value:"${phone}"
                },{
                    type:"text",
                    name:"email",
                    id:"email",
                    label:"邮箱",
                    value:"${email}"
                },{
                    type:"text",
                    name:"graduationTime",
                    id:"graduationTime",
                    label:"毕业年份",
                    value:"${graduationTime}"
                },{
                    label:"附加图片",
                    placeholder:"上传本人和一卡通/学生证的合照，帮助自助认证",
                    title:"上传本人和一卡通/学生证的合照，帮助自助认证",
                    type:"file",
                    name:"pic",
                    id:"upload_pic",
                    accept:"image/jpeg,image/png"
                },{
                    type:"submit",
                    id:"submit",
                    label:"提交"
                }
            ]
        });

        $("#edit").validate({
            rules: {
                VerifyType:{
                    min:0
                },
                email: {
                    required: true,
                    email: true
                },
                school: {
                    required: true,
                    maxlength: 30
                },
                name:{
                    required: true
                },
                motto: {
                    required: true,
                    maxlength: 50
                },
                phone:{
                    required: true,
                    number:true,
                    minlength: 11,
                    maxlength: 11
                },
                no:{
                    required: true,
                    number:true,
                    minlength: 10,
                    maxlength: 10
                },
                cla:{
                    required: true,
                    number:true,
                    minlength:4,
                    maxlength:4
                },
                faculty_text:{
                    required: true,
                    minlength:2,
                    maxlength:15
                },
                major_text:{
                    required: true,
                    minlength:2,
                    maxlength:15
                },
                graduationTime:{
                    required:true,
                    number:true,
                    minlength:4,
                    maxlength:4
                }
            },
            messages: {
                VerifyType:"请选择认证内容",
                name:"请输入姓名",
                school:"输入学校名称",
                faculty_text: "学院长度必须是2~15",
                major_text: "专业长度必须是2~15",
                email: "请输入一个合法的email地址",
                phone:"请输入一个合法的11位电话号码",
                no:"请输入一个合法的学号(10位数字)",
                cla:"请输入合法的班级号（4位数字）"
            }
        });

        if(${pre_id}){
            $("#submit").after("　"+HTML.a("Verify.action?id="+${pre_id},"上次申请记录"));
        }
    </script>
    <%}%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
