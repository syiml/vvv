<%@ page import="util.JSON.JSON" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%=JSON.getProblemValidate(request.getParameter("oj"), request.getParameter("pid"))%>
