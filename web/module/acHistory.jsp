<%@ page import="dao.ratingSQL" %>
<%@ page import="util.JSON.JSON" %>
<%@ page import="util.Main" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/7/6 0006
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
  <%--<script type="text/javascript" src="../js/jquery-1.11.1.js"></script>--%>
  <%--<script type="text/javascript" src="../js/highcharts/highcharts.js"></script>--%>
  <%--<script type="text/javascript" src="../js/highcharts/highcharts-more.js"></script>--%>
  <script>
    var history_username='<%=request.getParameter("user")%>';
    var history_selfname='<%=Main.loginUser()==null?"":Main.loginUser().getUsername()%>';
    var history_d=[];
    var history_d2=[];
    var history_s={
      colors: ['#000000', '#ff0000' , '#50B432', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
      chart: {
        backgroundColor: {
          linearGradient: { x1: 0, y1: 0, x2: 1, y2: 1 },
          stops: [
            [0, 'rgb(255, 255, 255)'],
            [1, 'rgb(240, 240, 255)']
          ]
        },
        borderWidth: 0,
        plotBackgroundColor: 'rgba(255, 255, 255, .9)',
        plotShadow: true,
        plotBorderWidth: 1/*,
         type: 'spline'*/
      },
      title: {
        text: ''
      },
//          subtitle: {
//            text: ''
//          },
      plotOptions: {
        series: {
          marker:{
            enabled:true,
            fillColor: '#ffffff',
            lineWidth: 1,
            radius:0,
            lineColor: null // inherit from series
          }
        }
      },
      xAxis: {
        gridLineWidth: 0,
        lineColor: '#000',
        tickColor: '#000',
        labels: {
          style: {
            color: '#000',
            font: '11px Trebuchet MS, Verdana, sans-serif'
          }
        },
        title: {
          style: {
            color: '#333',
            fontWeight: 'bold',
            fontSize: '12px',
            fontFamily: 'Trebuchet MS, Verdana, sans-serif'

          }
        },
        type: 'datetime',
        dateTimeLabelFormats: {
          millisecond: '%Y-%m-%e',
          second: '%Y-%m-%e',
          minute: '%Y-%m-%e',
          hour: '%Y-%m-%e',
          day: '%Y-%m-%e',
          week: '%Y-%m-%e',
          month: '%Y年%m月',
          year: '%Y年%m月'
        }/*{ // don't display the dummy year
         month: '%b月%e日',
         year: '%Y年%b月'
         }*/
      },
      yAxis: {
        allowDecimals:false,
        //minorTickInterval: 'auto',
        gridLineWidth: 0,
        lineColor: '#000',
        lineWidth: 1,
        tickWidth: 1,
        tickColor: '#000',
        labels: {
          style: {
            color: '#000',
            font: '11px Trebuchet MS, Verdana, sans-serif'
          }
        },
        title: {
          text:'',
          style: {
            color: '#333',
            fontWeight: 'bold',
            fontSize: '12px',
            fontFamily: 'Trebuchet MS, Verdana, sans-serif'
          }
        }
      },
      tooltip: {
        formatter: function() {
          return '<b>'+ this.series.name +'</b><br/>'+
                  Highcharts.dateFormat('%Y-%m-%e', this.x) +'<br>'+'AC='+ this.y ;
        }
      },
      series: [{
        name: history_username,
        // Define the data points. All series have a dummy year
        // of 1970/71 in order to be compared on the same x axis. Note
        // that in JavaScript, months start at 0 for January, 1 for February etc.
        data: history_d
      }]
    };
    //    $.getJSON("module/ratingget.jsp?user="+username,function(data){
    //      alert(data);
    //      alert($.toJSON(data));
    history_d=<%=JSON.getAcHistory(request.getParameter("user"))%>;
    history_d2=<%=Main.loginUser()==null?"[]":JSON.getAcHistory(Main.loginUser().getUsername())%>;

    $(function () {
      /*history_s.yAxis.min=m-100;*/
      history_s.series[0].data=history_d;
      if(history_d2.length!=0){
        history_s.series[1]={name: history_selfname,data:history_d2};
      }
      $('#AcHistory').highcharts(history_s);
      if(history_d2.length!=0) {
        $('#AcHistory').highcharts().series[1].hide();
      }
    });
    //    });



  </script>
</head>
<body>
<div id="AcHistory" style="min-width:700px;height:400px"></div>
</body>
</html>