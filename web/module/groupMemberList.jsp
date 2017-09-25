<%@ page import="util.HTML.UserListHTML.GroupUserListHTML" %><%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2017/9/24
  Time: 23:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%=new GroupUserListHTML(Integer.parseInt(request.getParameter("id")),false).HTML()%>
