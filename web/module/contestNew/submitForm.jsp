<%@ page import="Main.User.User" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/5/25
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<%--
  所需参数：cid   为null表示全局题目
            pid   为null则表单中pid为空
--%>
<%
  String cid=request.getParameter("cid");
  String pid=request.getParameter("pid");
  if(cid==null){
    cid="-1";
  }
  if(pid==null){
    pid="";
  }
%>
<%--<form action="sb.action" method="post" class="form-inline" id="submit">--%>
<center>
<form class="form-inline" id="submitForm">
  <div class="row">
    <div class="form-group">
      <label for="pidinput"> pid</label><input type="text" name ="pid" value="<%=pid%>" class="form-control" id="pidinput"/>
      <label for="laninput"> language</label><select name="language" class="form-control" id="laninput">
                                        <option value="0" selected>G++</option>
                                        <option value="1">GCC</option>
                                        <option value="2">Java</option>
                                        </select>
    </div>
  </div>
  <div class="row">
    <div class="form-group">
      <label for="codeinput"> code</label><br>
      <textarea name="code" class="form-control" cols="100" rows="15" id="codeinput" placeholder="Put your code here"></textarea>
    </div>
  </div>
  <div class="row">
    <input type="button" class="form-control btn-primary" value="Submit" onclick="doSubmit()"/>
  </div>
</form>
</center>
<%--<script type="text/javascript">--%>
  <%--$().ready(function() {--%>
    <%--$("#submitForm").validate({--%>
      <%--rules: {--%>
        <%--code: {--%>
          <%--required: true,--%>
          <%--minlength: 51,--%>
          <%--maxlength: 65535,--%>
<%--//          bsf: true,--%>
          <%--onkeyup: true--%>
        <%--}--%>
      <%--},--%>
      <%--messages: {--%>
        <%--code: {--%>
          <%--required: "Please enter your code",--%>
          <%--minlength: "Code length is improper! Make sure your code length is longer than 50 and not exceed 65536 Bytes",--%>
          <%--maxlength: "Code length is improper! Make sure your code length is longer than 50 and not exceed 65536 Bytes"--%>
        <%--}--%>
      <%--}--%>
    <%--});--%>
  <%--});--%>
<%--</script>--%>