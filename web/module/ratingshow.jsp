<%@ page import="dao.ratingSQL" %>
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
  <script>
    var username='<%=request.getParameter("user")%>';
    var user2;
    <%
    boolean t=(request.getParameter("true")!=null);
      String user2=request.getParameter("user2");
      if(user2!=null){
          %>user2='<%=user2%>';<%
      }else{
          %>user2=null;<%
      }
    %>
//    alert(user2);
    var d=[];
    var d2=[];
    var s={
      colors: ['#000000', '#ED561B' , '#50B432', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
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
          plotBorderWidth: 1,
        type: 'spline'
      },
      title: {
        text: ''
      },
//          subtitle: {
//            text: ''
//          },
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
        dateTimeLabelFormats: { // don't display the dummy year
          month: '%b月%e日',
          year: '%Y年%b月'
        }
      },
      yAxis: {
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
          },
          plotBands: [
              {
                  from: -10000,
                  to: 1000,
                  color: 'rgba(128, 128, 128, 0.5)'
              }, {
                  from: 1000,
                  to: 1200,
                  color: 'rgba(64, 191, 64, 0.5)'
              }, { // Gentle breeze
                  from: 1200,
                  to: 1400,
                  color: 'rgba(0, 255, 0, 0.5)'
              }, {
                  from: 1400,
                  to: 1500,
                  color: 'rgba(0, 192, 255, 0.5)',
                  label: {
                      text: '',
                      style: {
                          color: '#606060'
                      }
                  }
              }, {
                  from: 1500,
                  to: 1600,
                  color: 'rgba(0, 0, 255, 0.5)',
                  label: {
                      text: '',
                      style: {
                          color: '#606060'
                      }
                  }
              }, {
                  from: 1600,
                  to: 1700,
                  color: 'rgba(192, 0, 255, 0.5)',
                  label: {
                      text: '',
                      style: {
                          color: '#606060'
                      }
                  }
              }, {
                  from: 1700,
                  to: 1900,
                  color: 'rgba(255, 0, 255, 0.5)',
                  label: {
                      text: '',
                      style: {
                          color: '#606060'
                      }
                  }
              }, {
                  from: 1900,
                  to: 2000,
                  color: 'rgba(255, 0, 128, 0.5)',
                  label: {
                      text: '',
                      style: {
                          color: '#606060'
                      }
                  }
              }, {
                  from: 2000,
                  to: 2200,
                  color: 'rgba(255, 0, 0, 0.5)',
                  label: {
                      text: '',
                      style: {
                          color: '#606060'
                      }
                  }
              }, {
                  from: 2200,
                  to: 2600,
                  color: 'rgba(255, 128, 0, 0.5)',
                  label: {
                      text: '',
                      style: {
                          color: '#606060'
                      }
                  }
              }, {
                  from: 2600,
                  to: 10000,
                  color: 'rgba(255, 215, 0, 0.5)',
                  label: {
                      text: '',
                      style: {
                          color: '#606060'
                      }
                  }
              }]
//        min: m-100
      },
      tooltip: {
        formatter: function() {
          return '<b>'+ this.series.name +'</b><br/>'+
                  Highcharts.dateFormat('%b月%e日', this.x) +':Rating='+ this.y +'<br>'+this.point.text;
        }
      },
      series: [{
        name: username,
        // Define the data points. All series have a dummy year
        // of 1970/71 in order to be compared on the same x axis. Note
        // that in JavaScript, months start at 0 for January, 1 for February etc.
        data: d
      }]
    };
//    $.getJSON("module/ratingget.jsp?user="+username,function(data){
//      alert(data);
//      alert($.toJSON(data));
      data=<%=ratingSQL.getJson(request.getParameter("user"),t)%>;
      var m=10000;
      for(var i=0;i<data.length;i++){
          //d[i]=new Array();
          d[i]={x:data[i].time,y:data[i].rating,text:data[i].text};
          m= (m<d[i].y?m:d[i].y);
      }
//        alert(user2);
      if(user2!=null){
        $.getJSON("module/ratingget.jsp?user="+user2<%=t?"+'&true=1'":""%>,function(data){
          <%--data=<%=ratingSQL.getJson(request.getParameter("user2"),t)%>;--%>
          for(var i=0;i<data.length;i++){
            //d[i]=new Array();
            d2[i]={x:data[i].time,y:data[i].rating,text:data[i].text};
            m= (m<d2[i].y?m:d2[i].y);
          }
          $(function () {
            s.yAxis.min=m-100;
            s.series[1]={};
            s.series[1].name=user2;
            s.series[1].data=d2;
            $('#container').highcharts(s);
          });
        });
      }else{
        $(function () {
          s.yAxis.min=m-100;
          s.series[0].data=d;
          $('#container').highcharts(s);
        });
      }
//    });




  </script>
</head>
<body>
<div id="container" style="min-width:700px;height:400px"></div>
</body>
</html>