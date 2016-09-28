<%@ page import="util.HTML.HTML" %>
<%@ page import="util.Main" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/29 0029
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
//  if(session.getAttribute("user")==null){
//    response.sendRedirect("Login.jsp");
//    return;
//  }
  String rid = request.getParameter("rid");
  String rid2 = request.getParameter("rid2");
  Object user=session.getAttribute("user");
%>
<%--<link href="js/prism/css.css" rel="stylesheet" />--%>
<%--<script src="js/prism/js.js"></script>--%>
<html>
<head>
  <title>代码对比 - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp"/>
    <form style="margin:15px" class="form-inline" action="code2.jsp" method="get">
        <div class="form-group">
            <div class="input-group input-group-sm">
                <span class="input-group-addon">rid1</span>
                <input type="text" class="form-control " name="rid">
            </div>
        </div>
        <div class="form-group">
            <div class="input-group input-group-sm">
                <span class="input-group-addon">rid2</span>
                <input type="text" class="form-control " name="rid2">
            </div>
        </div>
        <button type="submit" class="btn btn-default btn-sm">submit</button>
    </form>
    <%=HTML.codeSimple(rid,rid2)%>
    <div class="row">
        <div class="col-sm-6">
          <%=HTML.viewCode(rid,true)%>
        </div>
        <div class="col-sm-6">
          <%=HTML.viewCode(rid2,true)%>
        </div>
    </div>
</div><jsp:include page="module/foot.jsp"/>
</body>
</html>
<script src="js/codeCmp.js"></script>
<script>
    var $v=$(".viewcode");
    var ret=comp($v.first().text(),$v.last().text());
    $v.first().html(ret.s1);
    $v.last().html(ret.s2);
</script>