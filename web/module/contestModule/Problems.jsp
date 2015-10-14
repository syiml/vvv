<%@ page import="Main.Main" %>
<%@ page import="Main.problem.ProblemSQL" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
    所需参数：cid  不能为空
              pid  为空默认为0
              page 默认为0
--%>
<%
    String cid=request.getParameter("cid");
    String pid=request.getParameter("pid");
    if(pid==null||pid.equals("")) pid="0";
    int pidint=Integer.parseInt(pid);
    if(cid!=null){
        int q1=Integer.parseInt(cid);
        int pnum= Main.contests.getContest(q1).getProblemNum();
        %><ul class="nav nav-pills"><%
        for(int i=0;i<pnum;i++){
            %><li role="presentation" <%=pidint==i?"class='active'":""%>><a href="#Problem_<%=i%>"><%=(char)('A'+i)%></a></li><%
        }
    %></ul><%
    }else{
        %>Wrong Parameters<%
    }
%>
