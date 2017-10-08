<%@ page import="entity.AiInfo" %>
<%@ page import="dao.AiSQL" %>
<%@ page import="entity.User" %>
<%@ page import="util.Main" %>
<%@ page import="util.HTML.UserHTML" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%
            int id = Integer.parseInt(request.getParameter("id"));
            AiInfo aiInfo = AiSQL.getInstance().getBeanByKey(id);
            User ai_author = Main.users.getUser(aiInfo.getUsername());
    %>
    <meta charset="UTF-8">
    <title>Gomoku</title>
    <link rel="stylesheet" href="css/Gomoku.css?1"/>
    <!--<script src="https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/js/lib/jquery-1.10.2_d88366fd.js"></script>-->
    <script src="js/jquery-1.11.1.js"></script>
    <script>
		//var ws = new WebSocket("ws://syiml.wicp.net:21329/AI_PVE?id=0");
		var ws = new WebSocket("ws://"+window.location.host+"/AI_PVE?game_id=1&id=<%=id%>");
		//打开
		ws.onopen = function () {
		   console.log("WebSocket Success");
		   ws.send(JSON.stringify({type:"start"}));
      	}
		//异常
      	ws.onerror = function () {
           console.log("WebSocket Error");
      	};
		//接收数据
		var flag = 1;
		var flag2 = 1;
		var flag3 = 0; //玩家颜色 1黑 0白
		var str = "";

		var now_player = 1;
		var clock;
        var clock_sec;
        var step = 1;
        function clock_d()
        {
            $("#clock").text("还剩"+clock_sec+"秒");
            clock_sec -- ;
            if(clock_sec < 0)
                clearInterval(clock);
        }
        function on_click(){
            if($(this).hasClass("black")||$(this).hasClass("white")) return ;
            if(now_player === 0) return ;

            $(this).unbind( "click" );
            flag = 1-flag;
            $(".black").removeClass("last");
            $(".white").removeClass("last");
            $(this).removeClass("block");
            $(this).removeClass("black");
            $(this).removeClass("white");
            $(this).addClass("last");
            if(flag3){
                $(this).addClass("black");
            }else{
                $(this).addClass("white");
            }
            var i = $(this).attr("id").split("_")[1];
            var j = $(this).attr("id").split("_")[2];
            ws.send(JSON.stringify({type:"int",value:i}));
            ws.send(JSON.stringify({type:"int",value:j}));
            now_player = 0;

            clearInterval(clock);
            clock_sec = 30;
            clock_d();
            clock = setInterval("clock_d()",1000);
            if(flag3 == 0) {
                $("#player").text("黑方");
            }else{
                $("#player").text("白方");
            }
            $("#info").text("等待对方下子...");
            $("#step").text("第"+step+++"子");
        }
		ws.onmessage = function(event){
			console.log("RECEIVE:"+event.data);
			var data = (new Function("","return "+event.data))();
			//alert(data.type + ";" + data.value);
			//alert("flag="+flag);

            if(flag == 1){
                //alert("初始化" + data.value);
                if(data.type == "int") {
                    if (data.value == 1) {
                        //用户黑子先手
                        flag3 = 1;
                        alert("用户黑子先手");
                        wzq_show.init(15, 15, on_click);
                        now_player = 1;


                        $("#player").text("黑方");
                        $("#info").text("轮到你了！");
                        $("#step").text("第"+step+++"子");
                        now_player = 1;

                        clearInterval(clock);
                        clock_sec=120;
                        clock_d();
                        clock = setInterval("clock_d()",1000);
                    } else {
                        flag3 = 0;
                        alert("用户白子后手");
                        wzq_show.init(15, 15, on_click);
                        now_player = 0;

                        clearInterval(clock);
                        clock_sec = 30;
                        clock_d();
                        clock = setInterval("clock_d()", 1000);
                        $("#player").text("黑方");
                        $("#info").text("等待对方下子...");
                        $("#step").text("第"+step+++"子");
                    }
                    flag++;
                    return ;
                }
            }

			if(flag2 < 3){
				//alert(data.value);
				str += data.value;
				if((flag2%2) != 0){
					str += "-";
				}
				flag2++;
			}
			if(flag2 == 3){
				var xy = str.split("-");
				if(flag3 == 0){
                    $(".white").removeClass("last");
                    wzq_show.setBlack(xy[0],xy[1]);
                    $("#player").text("白方");
				}else{
                    $(".black").removeClass("last");
				    wzq_show.setWhite(xy[0],xy[1]);
                    $("#player").text("黑方");
				}
                setLast(xy[0],xy[1]);
				//收到AI落子
                $("#info").text("轮到你了！");
                $("#step").text("第"+step+++"子");
                now_player = 1;

                clearInterval(clock);
                clock_sec=120;
                clock_d();
                clock = setInterval("clock_d()",1000);

				flag2 = 1;
				str = "";
			}

            if(data.type == "result") {
                if (flag3 == 1) {
                    if (data.value == 1) {
                        $("#info").text("恭喜你，获胜了！").append("<button onclick='javascript:location.reload();'>再来一局！</button>");
                    } else if (data.value == 2) {
                        $("#info").text("失败了！").append("<button onclick='javascript:location.reload();'>再来一局！</button>");
                    } else if (data.value == 3) {
                        $("#info").text("由于AI运行时出错，你赢得了比赛").append("<button onclick='javascript:location.reload();'>再来一局！</button>");
                    } else if(data.value == 4) {
                        $("#info").text("思考太久了~ 挑战失败").append("<button onclick='javascript:location.reload();'>再来一局！</button>");
                    } else if(data.value == -2){
                        $("#info").text("平局").append("<button onclick='javascript:location.reload();'>再来一局！</button>");
                    }
                } else {
                    if (data.value == 2) {
                        $("#info").text("恭喜你，获胜了！").append("<button onclick='javascript:location.reload();'>再来一局！</button>");
                    } else if (data.value == 1) {
                        $("#info").text("失败了！").append("<button onclick='javascript:location.reload();'>再来一局！</button>");
                    } else if (data.value == 4) {
                        $("#info").text("由于AI运行时出错，你赢得了比赛").append("<button onclick='javascript:location.reload();'>再来一局！</button>");
                    } else if(data.value == 3){
                        $("#info").text("思考太久了~ 挑战失败").append("<button onclick='javascript:location.reload();'>再来一局！</button>");
                    } else if(data.value == -2){
                        $("#info").text("平局").append("<button onclick='javascript:location.reload();'>再来一局！</button>");
                    }
                }
                if(data.value == 1 || data.value == 3)
                    $("#player").text("黑方获胜");
                else if(data.value == 2 || data.value == 4)
                    $("#player").text("白方获胜");
                else if(data.value == -2){
                    $("#player").text("平局");
                }
                now_player = 0;
                clearInterval(clock);
                return;
            }

		}

        $(document).ready(function(){
			//用ajax获取值写入页面
			//$.ajax({
			//   type: "POST",
			//   url: "#",
			//   data: null,
			//   success: function(msg){
				 //返回参数,获得返回总人数以及每个人的个人信息，执行循环并写入
			//	 $("#vs").html();
			//	 $("#name").html();
			//	 $("#author").html();
			//	 $("#honour").html();
			//   }
			//});

            $("button").click(function(){
                $("body").animate({
                    opacity:'0.3'
                },500,function(){
                    window.location.href="aigame_index.html";
                });
            });
      });

	function setBlack(i,j){
		$("#wzq_"+i+"_"+j).removeClass("block").removeClass("black").removeClass("white").addClass("black");
	}
	function setWhite(i,j){
		$("#wzq_"+i+"_"+j).removeClass("block").removeClass("black").removeClass("white").addClass("white");
	}
	function setNone(i,j){
		$("#wzq_"+i+"_"+j).removeClass("block").removeClass("black").removeClass("white").addClass("block");
	}
	function setLast(i,j){
        $("#wzq_"+i+"_"+j).addClass("last");
    }
    </script>
</head>
<body>
<div class="page-3">
    <div class="back"><button>回到主页</button></div>
    <img class="logo" src="syspic/wzq/zsjc.png" alt="中世竞创·AI对战平台"/>
    <div class="page-title">
        <h1>五子棋对战</h1>
        <i></i>
        <h2 id="vs"><%=Main.loginUser().getNick()%> VS <%=ai_author.getUsername()%>的AI <%=aiInfo.getAiName()%></h2>
        <div class="wzq_position">
            <div class="wzq" id="wzq">

            </div>
            <div class="about">
                <div class="game">
                    <div class="game-title">
                        实时赛况
                    </div>
                    <i></i>
                    <div class="game-about">
                        <p>当前下棋方：<span id="player"></span></p>
                        <p>统计：<span id="step"></span></p>
                        <p>计时：<span id="clock"></span></p>
                        <p>状态：<span id="info"></span></p>
                    </div>
                </div>
                <div class="about-title">
                    <h1 id="name">AI名称：<%=aiInfo.getAiName()%></h1>
                    <i></i>
                    <h2>作者：<span id="author"><%=ai_author.getUsername()%>(<%=ai_author.getNick()%>)</span></h2>
                </div>
                <div class="honour" id="honour">
                    在校荣誉：
                    <%=new UserHTML(ai_author,Main.loginUser()).TeamMemberInfo()%>
                    <%--<p>2013-11-17: 参加ACM/ICPC亚洲区域赛获得鼓励奖(长沙赛区)</p>--%>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
<script src="js/wzq.js"></script>
</html>