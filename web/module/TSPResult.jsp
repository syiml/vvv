<%--
  Created by IntelliJ IDEA.
  User: QAQ
  Date: 2017/4/23
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TSP</title>
</head>
<body>
<script type="text/javascript" src="../js/jquery-1.11.1.js"></script>
<script type="text/javascript" src="../js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="../js/highcharts/highcharts-more.js"></script>
<div id="container" style="min-width:700px;height:400px"></div>
<script>
    var d = [{x:0,y:0},{x:11,y:124},{x:121,y:14},{x:21,y:154}];
    var s={
        colors: ['#000000', '#000000' , '#50B432', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
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
                    lineWidth: 2,
                    radius:2,
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
            }
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
                        Highcharts.dateFormat('%Y-%m-%e', this.x) +':Rating='+ this.y +'<br>'+this.point.text;
            }
        },
        series: [{
            //name: username,
            // Define the data points. All series have a dummy year
            // of 1970/71 in order to be compared on the same x axis. Note
            // that in JavaScript, months start at 0 for January, 1 for February etc.
            data: d
        },{
        }]
    };

    $('#container').highcharts(s);

</script>

</body>
</html>
