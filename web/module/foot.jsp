<%@ page import="Tool.HTML.HTML" %>
<%@ page import="Main.Main" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/5 0005
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
  .container-fluid{
    min-height: 510px;
    padding-bottom: 10px;
  }
  .foot{
    /*min-height: 25%;*/
    /*position: absolute;*/
    /*bottom: 0;*/
    /*background-color: #AAA;*/
    /*width: 90%;*/
    padding: 20px 10% 20px 10% ;
    background-color: #EEEEEE;
  }
</style>
<div class="foot">
  <%=HTML.center("<h3>T^T Online Judge</h3>")%>
  <%=HTML.center("[<a href='Discuss.jsp?id=6'>BUG反馈</a>] [<a href='Discuss.jsp?id=4'>FAQ</a>]")%>
  当前版本：1.54
  <%=HTML.floatRight("系统时间：" + HTML.timeHTML("time", Main.now().getTime()))%>
  <%--<%=HTML.floatRight("当前访问IP：" + request.getRemoteAddr())%>--%>
  <br>
</div>
