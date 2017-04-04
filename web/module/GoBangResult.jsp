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
    <link rel="stylesheet" href="../css/wzq.css">
    <link rel="stylesheet" href="../bootstrap-3.3.4-dist/css/bootstrap.css">
    <script src="../js/jquery-1.11.1.js"></script>
</head>
<body>
    <div class="nav btn-toolbar">

    </div>
    <div class="next btn-group">

    </div>

    <div class="info"> </div>
<div class="wzq">

</div>
</body>
</html>
<script>
    var wzq_show = function(){
        var flag = 0;
        var $wzq = $('.wzq');
        function init(row,col){
            $wzq.html("");
            for(var i=0;i<row;i++){
                var $row = $wzq.append("<div class='wzq_row'></div>").find('.wzq_row').last();
                for(var j=0;j<col;j++){
                    buildBlock($row,i,j);
                }
            }
            var $table = $wzq.append("<table></table>").find("table");
            for(var i=0;i<row-1;i++){
                var $row = $table.append("<tr></tr>").find('tr').last();
                for(var j=0;j<col-1;j++){
                    $row.append("<td><div></div></td>");
                }
            }
        }
        function buildBlock($row,i,j){
            var $block = $row.append("<div class='block' id='wzq_"+i+"_"+j+"'></div>").find(".block");
            /*$block.click(function(){
             if($(this).hasClass("black")||$(this).hasClass("white")) return ;
             $(this).unbind( "click" );
             flag = 1-flag;
             $(this).removeClass("block");
             $(this).removeClass("black");
             $(this).removeClass("white");
             if(flag){
             $(this).addClass("black");
             }else{
             $(this).addClass("white");
             }
             });*/
        }
        function setBlack(i,j,z){
            $("#wzq_"+i+"_"+j).removeClass("block").removeClass("black").removeClass("white").addClass("black").html("<div class='number'>"+z+"</div>");
        }
        function setWhite(i,j,z){
            $("#wzq_"+i+"_"+j).removeClass("block").removeClass("black").removeClass("white").addClass("white").html("<div class='number'>"+z+"</div>");
        }
        function setNone(i,j,z){
            $("#wzq_"+i+"_"+j).removeClass("block").removeClass("black").removeClass("white").addClass("block").html("<div class='number'>"+z+"</div>");
        }
        function get(i,j){
            var $block = $("#"+i+"_"+j);
            if($block.hasClass("block")) return 0;
            if($block.hasClass("black")) return 1;
            if($block.hasClass("white")) return 2;
        }
        return {
            init:init,
            setBlack:setBlack,
            setWhite:setWhite,
            setNone:setNone,
            get:get
        };
    }();

    var data=<%=Main.status.getCEInfo(rid)%>

    var $nav = $(".nav");
    var $gro;
    for(var i=0;i<data.record.length;i++)
    {
        if(i%10 == 0){
            $gro = $nav.append("<div class='btn-group' style='margin-bottom:10px' role='group'></div>").find(".btn-group").last();
        }
        $gro.append("<a role='button' style='width: 60px;' class='btn btn-default btn-sm' href='javascript:pan("+i+")'> 第"+(i+1)+"局 </a>");
    }

    var i_pan,j_st;

    function pan(i){
        $("#res").remove();
        $('.nav a').removeClass("btn-primary").eq(i).addClass("btn-primary");
        i_pan = i;
        j_st = 0;
        $(".info").html("").append("黑方："+data.record[i].black + "<br>白方："+data.record[i].white);
        wzq_show.init(data.record[i].row,data.record[i].col);
        /*for(var j=0;j<data.record[i].step.length;j++)
         {
         if(data.record[i].step[j].type == "success"){
         if(data.record[i].step[j].player == 1)
         wzq_show.setBlack(data.record[i].step[j].x,data.record[i].step[j].y,j+1);
         else
         wzq_show.setWhite(data.record[i].step[j].x,data.record[i].step[j].y,j+1);
         }
         }*/
    }

    function showEnd(){
        while(next());
    }
    function next()
    {
        if(j_st < data.record[i_pan].step.length){
            console.log(data.record[i_pan].step[j_st]);
            if(data.record[i_pan].step[j_st].type == "success")
            {
                if(data.record[i_pan].step[j_st].player == 1)
                    wzq_show.setBlack(data.record[i_pan].step[j_st].x,data.record[i_pan].step[j_st].y,j_st+1);
                else
                    wzq_show.setWhite(data.record[i_pan].step[j_st].x,data.record[i_pan].step[j_st].y,j_st+1);
            }else if(data.record[i_pan].step[j_st].type == "end"){
                if(data.record[i_pan].step[j_st].status == 1){
                    $(".next").append("<text id='res'>黑方胜</text>");
                }else if(data.record[i_pan].step[j_st].status == 2){
                    $(".next").append("<text id='res'>白方胜</text>");
                }else if(data.record[i_pan].step[j_st].status == 3){
                    $(".next").append("<text id='res'>黑方胜、白方程序出错</text>");
                }else if(data.record[i_pan].step[j_st].status == 4){
                    $(".next").append("<text id='res'>白方胜、黑方程序出错</text>");
                }else if(data.record[i_pan].step[j_st].status == 0){
                    $(".next").append("<text id='res'>平局</text>");
                }
            }
            j_st++;
            return true;
        }
        return false;
    }

    pan(0);
    $(".next").append("<a role=‘button’ class='btn btn-default btn-sm' href='javascript:next()'>下一步</a>" +
            "<a role=‘button’ class='btn btn-default btn-sm' href='javascript:showEnd()'>显示结果</a>");
</script>