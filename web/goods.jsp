<%@ page import="util.Main" %>
<%@ page import="entity.Mall.Goods" %>
<%@ page import="util.HTML.MallHTML" %>
<%@ page import="util.HTML.HTML" %>
<%@ page import="util.HTML.modal.modal" %>
<%@ page import="servise.MallMain" %>
<%@ page import="entity.Permission" %>
<%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2016/10/4
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int id;
    try {
        id = Integer.parseInt(request.getParameter("id"));
    }catch (NumberFormatException e){
        id=-1;
    }
    Goods goods = MallMain.getGoods(id);
    if(goods == null){
        id=-1;
    }
    Permission p = Main.loginUserPermission();
    if(goods.isHidden() && !p.getMallAdmin()){
        id=-1;
    }
    if(id == -1){
        //跳转
        return ;
    }
    if(request.getParameter("prompt")!=null){
        out.print("<script>alert(\""+request.getParameter("prompt")+"\");</script>");
    }
    Main.saveURL();
%>
<html>
<head>
    <title><%=goods.getTitle()%> - <%=Main.config.OJName%></title>
</head>
<body>
<div class="container-fluid">
    <jsp:include page="module/head.jsp?page=home"/>
    <div style="width: 1000px;margin:0 auto">
        <div class="row">
            <div class="col-xs-12">
                <div style="float:left;width:280px">
                    <%=MallHTML.coverImg(id)%>
                </div>
                <div style="float:left;width:720px">
                    <h1><%=goods.getTitle()%></h1>
                    <div>商品价格：<span style="color:red;font-size: 30px"><%=goods.getAcb()%></span>ACB</div>
                    <div>剩余数量：<%=goods.getStock()%></div>
                    <%
                      if(goods.getBuyLimit() >=0){
                    %>
                    <div>每人限购<%=goods.getBuyLimit()%>份</div>
                    <%}%>
                    <%
                        String body;
                        body = "是否花费"+goods.getAcb()+"ACB购买？";

                        modal m = new modal("buy","确认购买",body,"立即购买");
                        m.setAction("buy.action?id="+id);
                        m.setHavesubmit(true);
                        m.setBtnCls("danger");
                    %>
                    <div style="margin: 30px;float:left">
                        <%=m.toHTML()%>
                    </div>
                    <% if(Main.loginUserPermission().getMallAdmin()){%>
                    <div style="margin-top: 30px;float:left">
                        <%=HTML.abtn("md","admin.jsp?page=AddGoods&id="+id,"编辑","default")%>
                    </div>
                    <%}%>
                </div>
            </div>
        </div>
        <div class="row" style="margin-top: 30px">
            <div class="col-xs-12">
                <div style="float:left;width:800px;">
                    <%=goods.getDes()%>
                </div>
                <div style="float:right;width: 200px">
                    <%=MallHTML.goodsBuyRecord(id)%>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="module/foot.jsp"/>
</body>
</html>
