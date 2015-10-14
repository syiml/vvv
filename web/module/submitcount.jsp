<%@ page import="Tool.JSON.JSON" %>
<%--
  Created by IntelliJ IDEA.
  User: Syiml
  Date: 2015/10/7 0007
  Time: 21:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  //num、sec
  int num=Integer.parseInt(request.getParameter("num"));
  int sec=Integer.parseInt(request.getParameter("sec"));
  String user=request.getParameter("user");
%>
<div id="_submitcount"></div>
<script>
  var data = <%=JSON.getSubmitCount(user,num,sec)%>;
  //{ac:[ac1,ac2,ac3,...,acnum],submit:[s1,s2,s3,...,snum],user,num,sec}
  $(function () {
    function getTimeText(sec){
      if(sec==60) return "分钟";
      if(sec==60*5) return "个5分钟";
      if(sec==60*20) return "个20分钟";
      if(sec==60*60) return "小时";
      if(sec==60*60*3) return "个3小时";
      if(sec==60*60*6) return "个6小时";
      if(sec==60*60*8) return "个8小时";
      if(sec==60*60*24) return "天";
      if(sec==60*60*24*7) return "周";
      if(sec==60*60*24*30) return "月";
      if(sec==60*60*24*30*3) return "季度";
      if(sec==60*60*24*365) return "年";
    }
    function getTitle(){
      if(data.user){
        return data.user+" 最近"+data.num+getTimeText(data.sec)+"的提交记录";
      }else{
        return "最近"+data.num+getTimeText(data.sec)+"的提交记录";
      }
    }
    $('#_submitcount').highcharts({
      chart: {
        height:400
      },
      colors: ['#000000', '#5cb85c'],
      title: {
        text: getTitle()
      },
      subtitle: {
        text: ''
      },
      xAxis: {
        type: 'datetime',
        //categories: getCategories(data.num),
        tickmarkPlacement: 'on',
        dateTimeLabelFormats:{
          millisecond: '%H:%M:%S.%L',
          second: '%H:%M:%S',
          minute: '%H:%M',
          hour: '%H:%M',
          day: '%b-%e',
          week: '%b-%e',
          month: '%Y-%b',
          year: '%Y'
        },
        title: {
          enabled: false
        }
      },
      yAxis: {
        title: {
          text: '提交数'
        },
        min:0,
        labels: {
          formatter: function() {
            return this.value;
          }
        }
      },
      tooltip: {
        shared: true,
        valueSuffix: '',
        formatter: function() {
          return '<b>开始时间：</b>'+ Highcharts.dateFormat('%Y-%b-%e %H:%M:%S',this.x-data.sec*1000)+'<br>'+
                 '<b>结束时间：</b>'+ Highcharts.dateFormat('%Y-%b-%e %H:%M:%S',this.x)+'<br>'+
                  '<b>Submit:</b>' + this.points[0].y+"<br>"+
                 '<b>AC:</b>' + this.points[1].y;
        }
      },
      series: [{
        name: 'submit',
        pointInterval: -data.sec*1000,
        pointStart: Date.now()+8*60*60*1000,
        data: data.submit
      }, {
        name: 'ac',
        pointInterval: -data.sec*1000,
        pointStart: Date.now()+8*60*60*1000,
        data: data.ac
      }]
    });
  });
</script>