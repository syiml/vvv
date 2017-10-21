<%@ page import="util.HTML.HTML" %>
<%@ page import="util.Main" %>
<%@ page import="util.Tool" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/5 0005
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="foot">
  <%=HTML.center("<h3>"+Main.config.OJName+"</h3>")%>
  <%=HTML.center("[<a href='Discuss.jsp?id=6'>BUG反馈</a>] [<a href='Discuss.jsp?id=4'>FAQ</a>] [<a href='http://www.miitbeian.gov.cn/'>闽ICP备17026590号-1</a>]")%>
  当前版本：<a href="Discuss.jsp?id=21" ><%=Main.config.topConfig.version%></a>
  <%=HTML.floatRight("系统时间：" + HTML.timeHTML("time", Tool.now().getTime()))%>
  <%--<%=HTML.floatRight("当前访问IP：" + request.getRemoteAddr())%>--%>
  <br>
</div>
