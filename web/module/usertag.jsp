<%@ page import="util.JSON.JSON" %>
<%@ page import="dao.ProblemTagSQL" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/8/22 0022
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
  <script>
    $(function () {
      $('#usertag').highcharts({
        chart: {
          polar: true,
          type: 'line',
          backgroundColor:'RGBA(0,0,0,0)'
        },

        title: {
          text: '',
          x: -80
        },

        pane: {
          size: '90%'
        },
        xAxis: {
          categories: ['基础', '数据<br>结构', '数学','几何', '图论','搜索', '动态规划'],
          tickmarkPlacement: 'on',
          lineWidth: 0,
          gridLineColor:'#ddd',
          labels:{
            style:{
              color:'#fff'
            }
          }
        },

        yAxis: {
          gridLineInterpolation: 'polygon',
          min: 0,
          gridLineColor:'#ddd',
          labels:{
            enabled:false
          }
        },

        tooltip: {
          shared: true,
          pointFormat: '<span style="color:{series.color}"> <b>{point.y:,.2f}%</b><br/>'//{series.name}:
        },

        legend: {
          enabled:false
        },

        plotOptions: {
          series: {
            shadow: true
          }
        },

        series: [{
          name: '<%=request.getParameter("user")%>',
          data: <%=ProblemTagSQL.userTag(request.getParameter("user"))%>,
          color:'#0cc',
          lineWidth: 2,
          marker: {
            radius: 0
          }

        }]

      });
    });
  </script>
</head>
<body>
<div id="usertag" style="width:230px;height:200px"></div>
</body>
</html>
