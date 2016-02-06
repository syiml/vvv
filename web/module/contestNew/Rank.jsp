<%@ page import="util.Main" %>
<%@ page import="servise.ContestMain" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  所需参数:cid
--%>
<%
  String cid = request.getParameter("cid");
  if(cid!=null){
    out.print(ContestMain.getContest(Integer.parseInt(cid)).getRankHTML());
  }
%>