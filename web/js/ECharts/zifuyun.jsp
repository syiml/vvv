<%@ page import="Tool.JSON.JSON" %>
<%@ page import="ProblemTag.ProblemTagHTML" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/8/19 0019
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<div id="main" style="height:250px;"></div>

</body>
</html>

<script>
  function createRandomItemStyle() {
    return {
      normal: {
        color: 'rgb(' + [
          Math.round(Math.random() * 160),
          Math.round(Math.random() * 160),
          Math.round(Math.random() * 160)
        ].join(',') + ')'
      }
    };
  }
  var myChart = echarts.init(document.getElementById('main'));
  var option = {
    tooltip: {
      show: true
    },
    series: [{
      name: 'tag',
      type: 'wordCloud',
      size: ['100%', '100%'],
      textRotation : [0,10,20,30,40,50,60,70,80,-10,-20,-30,-40,-50,-60,-70,-80],
      textPadding: 0,
      autoSize: {
        enable: true,
        minSize: 10
      },
      data: <%=ProblemTagHTML.problemTagJson(Integer.parseInt(request.getParameter("pid")))%>
    }]
  };
  myChart.setOption(option);
</script>