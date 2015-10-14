<%@ page import="ProblemTag.ProblemTagSQL" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/25 0025
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    String name=request.getParameter("name");
    if(name!=null){//处理中文乱码
        byte source [] = name.getBytes("iso8859-1");
        name = new String (source,"UTF-8");
    }
  String type=request.getParameter("type");
  if(type.equals("add")){
    ProblemTagSQL.addTag(name);
  }else if(type.equals("rename")){
    ProblemTagSQL.renameTag(Integer.parseInt(request.getParameter("tagid")),name);
  }
  response.sendRedirect("../../admin.jsp?page=AddTag");
%>