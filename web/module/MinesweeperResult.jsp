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
<style>
    .sl_row{
        display: table;
    }
    .ce{
        height: 16px;
        width: 16px;
        float: left;
    }
    .cell{
        background-image: url('MinesweeperPic/cell.gif');
    }
    .cell_0{
        background-image: url('MinesweeperPic/empty.gif');
    }
    .cell_1{
        background-image: url('MinesweeperPic/1.gif');
    }
    .cell_2{
        background-image: url('MinesweeperPic/2.gif');
    }
    .cell_3{
        background-image: url('MinesweeperPic/3.gif');
    }
    .cell_4{
        background-image: url('MinesweeperPic/4.gif');
    }
    .cell_5{
        background-image: url('MinesweeperPic/5.gif');
    }
    .cell_6{
        background-image: url('MinesweeperPic/6.gif');
    }
    .cell_7{
        background-image: url('MinesweeperPic/7.gif');
    }
    .cell_8{
        background-image: url('MinesweeperPic/8.gif');
    }
    .cell_-1{
        background-image: url('MinesweeperPic/mine_yes.gif');
    }
    .sl{
        margin-top:10px;
    }

</style>
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
<div class="sl">

</div>
<div class="score"></div>
</body>
</html>
<script>
    var wzq_show = function(){
        var flag = 0;
        var $wzq = $('.sl');
        function init(row,col){
            $wzq.html("");
            for(var i=0;i<row;i++){
                var $row = $wzq.append("<div class='sl_row'></div>").find('.sl_row').last();
                for(var j=0;j<col;j++){
                    buildBlock($row,i,j);
                }
            }
        }
        function buildBlock($row,i,j){
            $row.append("<div class='cell ce' id='sl_"+i+"_"+j+"'></div>");
        }
        function setNumber(i,j,z){
            $("#sl_"+i+"_"+j) .removeClass("cell") .addClass("cell_"+z);
        }
        return {
            init:init,
            buildBlock:buildBlock,
            setNumber:setNumber,
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

    var i_pan,j_st,open_num=0;

    function pan(i){
        open_num = 0;
        $("#res").remove();
        $('.nav a').removeClass("btn-primary").eq(i).addClass("btn-primary");
        i_pan = i;
        j_st = 0;

        wzq_show.init(data.record[i].row,data.record[i].col);
        $(".score").html("本局得分："+Math.floor(open_num*100/(data.record[i_pan].row*data.record[i_pan].col-data.record[i_pan].mineNum))+"("+open_num+"/"+(data.record[i_pan].row*data.record[i_pan].col-data.record[i_pan].mineNum)+")");

    }

    function showEnd(){
        while(next());
    }
    function next()
    {
        while(j_st < data.record[i_pan].step.length){
            var st = data.record[i_pan].step[j_st];
            //console.log(data.record[i_pan].step[j_st]);
            wzq_show.setNumber(st.x,st.y,st.number);
            if(st.number>=0&&st.number<=8) open_num++;
            j_st++;

            st = data.record[i_pan].step[j_st];
            if(st) {
                var flag = false;
                for (var ii = -1; ii <= 1; ii++) {
                    for (var jj = -1; jj <= 1; jj++) {
                        if ($("#sl_" + (st.x + ii) + "_" + (st.y + jj)).hasClass("cell_0")) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) break;
                }
                if (flag) {
                    continue;
                }
            }
            $(".score").html("本局得分："+Math.floor(open_num*100/(data.record[i_pan].row*data.record[i_pan].col-data.record[i_pan].mineNum))+"("+open_num+"/"+(data.record[i_pan].row*data.record[i_pan].col-data.record[i_pan].mineNum)+")");
            return true;
        }
        if(au){
            clearInterval(au);
            au = null;
            $("#auto").html("自动播放");
        }
        return false;
    }
    var au = null;
    function auto(time){
        if(au == null) {
            au = setInterval("next()", time);
            $("#auto").html("停止播放");
        } else {
            clearInterval(au);
            au = null;
            $("#auto").html("自动播放");
        }
    }

    pan(0);
    $(".next").append("<a role=‘button’ class='btn btn-default btn-sm' href='javascript:next()'>下一步</a>" +
            "<a role=‘button’ class='btn btn-default btn-sm' href='javascript:showEnd()'>显示结果</a>"+
    "<a role=‘button’ class='btn btn-default btn-sm' id='auto' href='javascript:auto(100)'>自动播放</a>");
</script>