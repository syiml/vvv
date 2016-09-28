<%@ page import="util.Main" %>
<%@ page import="entity.User" %>
<%@ page import="util.HTML.HTML" %>
<%@ page import="util.Tool" %>
<%@ page import="entity.exam.Exam" %>
<%@ page import="servise.ExamMain" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/23
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>考试 - <%=Main.config.OJName%></title>
</head>
<body><div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
<%
    Main.saveURL();
    Object user=session.getAttribute("user");
    String id=request.getParameter("id");
    int qid=0;
    Exam exam = null;
    if(user==null){//未登录跳转
        response.sendRedirect("Login.jsp");
        return;
    }
    boolean bo=false;
    if(id==null){//参数错误
        out.print("Wrong Parameter");
        return ;
    }else{
        qid = Integer.parseInt(id);
        exam = ExamMain.getExam(qid);
        if(exam == null){
            out.print("Wrong Parameter1");
            return ;
        }
        int in = exam.canin(((User)user));
        if (in == 0) {
            out.print("没有权限");
        } else {
            bo=true;
        }
    }
    if(bo){
%>
    <h2 style="text-align:center"><%=exam.getName()%></h2>
    <%//进度条
        long time= Tool.now().getTime()-exam.getBegintime().getTime();
        long alltime=exam.getEndtime().getTime() - exam.getBegintime().getTime();
        if(time>=0&&time<=alltime) {
            out.print(HTML.progress(time, alltime, "contest_pro", ""));
            out.print(HTML.center(HTML.time_djs((alltime - time) / 1000, "contest_djs")));
        }
    %>
    <%--<%@include file="module/contestModule/main.jsp"%>--%>
    <script>
        var js_id=<%=id%>;
        var examInfo=<%=exam.toJSON()%>;//{id,name,begintime,endtime,info,admin,problems:[{type:},{}]}
    </script>
    <%@include file="module/Exam/main.html"%>
<%}%>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>