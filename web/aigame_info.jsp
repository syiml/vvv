
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="css/page3.css" rel="stylesheet"/>
    <script src="js/jquery-1.11.1.js"></script>
    <script>
        $(document).ready(function(){
			//获取跳转到当前页的用户id
			var url = location.href;
			var id = url.split("=")[1];
			//使用ajax获得信息
			$.ajax({
				//提交的数据格式
			   type: "POST",
			   //url地址
			   url: "http://localhost:8080/getAiInfoById.action",
			   //传递json "key=value"
			   data: "id=" + id,
			   dataType : "jsonp",
			   jsonp: "jsonpCallback",
			   success: function(msg){
				 //获得信息写入
				 //格式$("#写入目标的id").html(msg.传递的json属性名);
                   console.log(msg);
				 $("#name").html(msg.aiName);
				 $("#author").html(msg.username);
				 $("#content").html(msg.introduce);
				 $("#win").html(msg.win);
				 $("#lose").html(msg.total-msg.win);
			   }
			});					
			
            $("#back1").click(function(){
                $("body").animate({
                    opacity:'0.1',
                    height:'100%',
                    width:'0'
                },1000,function(){
                    window.location.href="aigame_index.html";
                });
            });
        });
		$(function(){
            $("#challenge").click(function(){
                $("body").animate({
                    opacity:'0.3'
                },500,function(){
                    window.location.href="aigame_list.html";
                });
            });
		});
    </script>
</head>
<body>
<div class="page-3">
    <img class="logo" src="pic/wzq/zsjc.png" alt="中世竞创·AI对战平台"/>
    <div class="page-title">
        <h1>AI：<span id="name">名字</span></h1>
        <i></i>
        <h2>作者：<span id="author">作者</span></h2>
    </div>
    <div class="page-content-2">
        <img class="content-bg" src="pic/wzq/quan.png" alt="中世竞创·AI对战平台"/>
        <div class="text-area" id="content">
        	<!-- 内容 -->
        </div>
        <div class="pk">
            <div class="vs">VS</div>
            <div class="pk-1">
                	Win:&nbsp;&nbsp;&nbsp;<span id="win"></span>
            </div>
            <div class="pk-1">
                Lose:&nbsp;&nbsp;<span id="lose"></span>
            </div>
        </div>
        <div class="come">
            <a href="aigame_main.jsp?id=<%=request.getParameter("id")%>" target="_blank" id="challenge">挑战</a>
        </div>
    </div>
    <div class="return return-2">
         <a target="_blank" id="back1">返回</a>
        <script type="text/javascript" language="javascript">
        	$("#back1").click(function(){
					window.history.back();
			});
        </script>
    </div>
</div>
</body>
</html>