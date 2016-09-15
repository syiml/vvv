<%@ page import="dao.ProblemTagSQL" %>
<%@ page import="util.Main" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/8/22 0022
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
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
          size: '80%'
        },
        xAxis: {
          categories: ['基础', '数据结构', '数学','几何', '图论','搜索', '动态规划'],
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
          tickInterval: 2,
          labels:{
            enabled:false
          }
        },

        tooltip: {
//          shared: true,
          pointFormat: '<span style="color:{series.color}"> <b>{point.y:,.2f}%</b>'//{series.name}:
        },

        legend: {
          enabled:true
        },

        plotOptions: {
          series: {
            shadow: true
          }
        },

        series: [

          <% if(Main.loginUser()!=null && !Main.loginUser().getUsername().equals(request.getParameter("user"))){%>
          {
            name: '<%=Main.loginUser().getUsername()%>',
            data: <%=ProblemTagSQL.userTag(Main.loginUser().getUsername())%>,
            connectEnds: true,
            color: '#000',
            lineWidth: 2,
            marker: {
              radius: 0
            }
          },<%}%>

          {
          name: '<%=request.getParameter("user")%>',
          data: <%=ProblemTagSQL.userTag(request.getParameter("user"))%>,
          connectEnds: true,
          color: '#0ff',
          lineWidth: 2,
          marker: {
            radius: 0
          }
        }


        ]

      });
    });
  </script>
</head>
<body>
<div id="usertag" style="width:290px;height:260px;background: rgba(0,0,0,0.1);border-radius: 5px;"></div>
</body>
</html>
