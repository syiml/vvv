<%@ page import="util.Main" %><%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2017/4/3
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int rid = Integer.parseInt(request.getParameter("rid"));
%>
<html>
<head>
    <link rel="stylesheet" href="../bootstrap-3.3.4-dist/css/bootstrap.css">
    <link rel="stylesheet" href="../css/main.css">
    <script src="../js/jquery-1.11.1.js"></script>
</head>
<body>
<pre class="GuessNumberTest sample">

</pre>
</body>
</html>
<script>
    var data=<%=Main.status.getCEInfo(rid)%>;
    var $text = $('.GuessNumberTest');
    for(var i=0;i<data.record.length;i++){
        $text.append("==第").append(i+1).append("盘游戏==<br>");
        $text.append("答案是").append(data.record[i].answer).append("<br>");
        for(var j=0; j<data.record[i].step.length;j++){
            $text.append("第").append(j+1).append("次猜数：").append(data.record[i].step[j].guess).append(",");
            if(data.record[i].step[j].ret == 1){
                $text.append("太大<br>");
            }else if(data.record[i].step[j].ret == -1){
                $text.append("太小<br>");
            }else{
                $text.append("猜对了！<br>");
            }
        }
    }
</script>