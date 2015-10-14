<%@ page import="Tool.HTML.HTML" %>
<%@ page import="Main.User.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  所需参数：cid、from
--%>
<%
  String cid=request.getParameter("cid");
  String pa=request.getParameter("page");
  Object u=session.getAttribute("user");
  String user=null;
  if(u!=null){
    user=((User)u).getUsername();
  }
  int paInt=0,cidInt=-1;
  if(pa!=null) paInt=Integer.parseInt(pa);
  if(cid!=null) cidInt=Integer.parseInt(cid);

  String ssuser=request.getParameter("user");
  String pid=request.getParameter("pid");
  String result=request.getParameter("result");
  String lang=request.getParameter("lang");
  int pidInt=-1,resultInt=-1,langInt=-1;
  if(pid!=null&&!pid.equals("")) pidInt=Integer.parseInt(pid);
  if(result!=null&&!result.equals("")) resultInt=Integer.parseInt(result);
  if(lang!=null&&!lang.equals("")) langInt=Integer.parseInt(lang);
  if(cid!=null)
  {
     out.println(HTML.StatusHTML(user, cidInt, paInt, pidInt, resultInt, langInt, ssuser));
  }
%>