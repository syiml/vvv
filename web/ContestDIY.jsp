<%@ page import="util.Main" %>
<%@ page import="util.HTML.HTML" %><%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2017/5/17
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>创建DIY比赛 - <%=Main.config.OJName%></title>
</head>

<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp?page=home"/>
    <%=HTML.panel("DIY比赛",HTML.adminAddContestForm(true))%>
</div>
<div id="modal"></div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>

<script type="text/javascript">
    $().ready(function() {
        $.validator.addMethod("bsf",function(value,element,params){
            if(params==false) return true;
            for(var i=0;i<value.length;i++){
                var c=value.charAt(i);
                if(!((c==',')||(c>='0'&&c<='9'))){
                    return false;
                }
            }
            return true;
        },"题目列表格式不合法，必须是以半角逗号隔开的题号列表");
        $("#addcontest").validate({
            onfocusout: false,
            rules: {
                name: {
                    required: true,
                    minlength: 4,
                    maxlength: 25
                },
                pass: {
                    minlength: 4,
                    maxlength: 25
                },
                problems: {
                    required: true,
                    maxlength: 250,
                    bsf:true,
                    remote: {
                        url: "module/problemsvalidate.jsp",     //后台处理程序
                        type: "post",               //数据发送方式
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            username: function() {
                                return $("#problems").val();
                            }
                        }
                    }
                },
                icpc_m1_t:{
                    required: true,
                    number:true
                },
                icpc_m2_t:{
                    required: true,
                    number:true
                },
                icpc_m3_t:{
                    required: true,
                    number:true
                },
                icpc_penalty:{
                    required: true,
                    number:true
                },
                shortcode_chengfa:{
                    required: true,
                    number:true
                },
                shortcode_m1_t:{
                    required: true,
                    number:true
                },
                shortcode_m2_t:{
                    required: true,
                    number:true
                },
                shortcode_m3_t:{
                    required: true,
                    number:true
                },
                training_m1_t:{
                    required: true,
                    number:true
                },
                training_m2_t:{
                    required: true,
                    number:true
                },
                training_m3_t:{
                    required: true,
                    number:true
                }
            },
            messages: {
                name: {
                    required: "名字不能为空",
                    minlength: "长度为4~25个字符",
                    maxlength: "长度为4~25个字符"
                },
                problems: {
                    required: "题目列表不能为空",
                    maxlength: "题目列表过长（最多50题）",
                    remote:"题目列表内有题目不存在或无权限加入比赛"
                }
            }
        });
    });
</script>