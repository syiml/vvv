<%@ page import="util.Main" %>
<%@ page import="util.Tool" %>
<%@ page import="util.HTML.GroupListHTML" %><%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2017/9/24
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Main.saveURL();

    int groupType = Tool.parseInt(request.getParameter("type"),1);
    int pageInt = Tool.parseInt(request.getParameter("page"),1);
%>
<html>
<head>
    <title>组队排名 - <%=Main.config.OJName%></title>
</head>
<body>
    <div class="container-fluid">
        <jsp:include page="module/head.jsp?page=home"/>
        <%=new GroupListHTML(pageInt,groupType).HTML()%>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="member_list" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">队员详情</h4>
                </div>
                <div class="modal-body " id="member_list_body">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        function member(group_id){
            $('#member_list').modal('toggle');
            $('#member_list_body').html(HTML.loader).load("module/groupMemberList.jsp?id="+group_id);
        }
    </script>
    <jsp:include page="module/foot.jsp"/>
</body>
</html>
