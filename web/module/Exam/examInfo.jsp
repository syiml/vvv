<%@ page import="servise.ExamMain" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  int id=Integer.parseInt(request.getParameter("id"));
%>
<%=ExamMain.getExam(id).toJSON()%>