<%@ page import="util.HTML.ProblemInfo" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/8/18 0018
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String pid=request.getParameter("pid");
%>
<!doctype html>
<html lang="en">
<head>
  <script>
    $(function () {
      Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function(color) {
        return {
          radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
          stops: [
            [0, color],
            [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
          ]
        };
      });
      $('#container').highcharts({
        chart: {
          plotBackgroundColor: null,
          plotBorderWidth: null,
          plotShadow: false,
          backgroundColor:'rgba(0,0,0,0)'
        },
        colors: ['#FF3366', '#CCCCFF', '#FF8C00', '#FF66FF',
          '#6666FF', '#00FFFF', '#99CCFF','#90ed7d', '#8d4653', '#91e8e1'],
        title: {
          text: ''
        },
        tooltip: {
          pointFormat: '<b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
          pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
              enabled: true,
              color: '#000000',
              connectorColor: '#000000',
              formatter: function() {
                return '<b>'+this.point.name +'</b>: '+ this.y ;
              }
            }
          }
        },
        series: [{
          type: 'pie',
          name: 'Browser share',
          data: <%=ProblemInfo.getStatusJson(Integer.parseInt(pid))%>
//                  [
//            ['Firefox',   45.0],
//            ['IE',       26.8],
//            {
//              name: 'Chrome',
//              y: 12.8,
//              sliced: true,
//              selected: true
//            },
//            ['Safari',    8.5],
//            ['Opera',     6.2],
//            ['Others',   0.7]
//          ]
        }]
      });
    });
  </script>
</head>
<body>
<div id="container"></div>
</body>
</html>