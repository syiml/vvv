/**
 * 观战模式,不考虑重判
 * result-> yes/no/new -> 1/0/-1
 * rank:[
 *     {rank,username,nick,name,gender,faculty,major,cla,no,S,W,score[[{rid,result,time}],[{rid,result,time}]]},
 *     {...}
 * ]
 * Created by Administrator on 2015/11/25 0025.
 */
var matchICPC=function(cid){
    var status;
    var user;
    var rank=[];
    var pnum;
    var ws;
    function send(text){
        console.log("Send:"+text);
        ws.send(text);
    }
    var init=function(){
        //刚刚启动时做的初始化工作
        //获取status列表 = {pnum,status:[{rid,pid,username,result,time},...]}
        $.getJSON("/StatusJson.action?cid="+cid,function(data){
            pnum=data.pnum;
            for(var i=0;i<data.status.length;i++){
                putStatusToRank(data.status[i]);
            }
            rankToHtml();
            startWebSocket(cid);

            //ws;//webSocket
            var contestId=-1;
            if (!window.WebSocket && window.MozWebSocket)
                window.WebSocket=window.MozWebSocket;
            if (!window.WebSocket)
                alert("你的浏览器不支持动态更新，要使用该功能请使用其他浏览器（比如谷歌浏览器）");
            function startWebSocket(cid)
            {
                contestId=cid;
                ws = new WebSocket("ws://" + location.host + "/MatchWebSocket?cid="+cid);
                ws.onopen = function(){
                    console.log("success open");
                };
                ws.onmessage = function(event)//接收消息
                {
                    console.log("RECEIVE:"+event.data);
                    var data=(new Function("","return "+event.data))();
                    if(data.type=="login"){
                        rankDynameick.log("login:"+data.user);
                        rankDynameick.addOnline(data.user);
                        rankDynameick.chat_log("<b>"+data.user+"</b>进入观战<br>");
                    }else if(data.type=="logout"){
                        rankDynameick.log("logout:"+data.user);
                        rankDynameick.delOnline(data.user);
                        rankDynameick.chat_log("<b>"+data.user+"</b>离开观战<br>");
                    }else if(data.type=="status"){
                        putStatus(data);
                    }else if(data.type=="chat"){
                        rankDynameick.chat(data.user,data.text);
                    }else if(data.type=="OnlineUser"){
                        for(var i=0;i<data.data.length;i++){
                            rankDynameick.addOnline(data.data[i]);
                        }
                    }
                };
                ws.onclose = function(event) {
                    console.log("Client notified socket has closed",event);
                    alert("连接已经断开，请刷新重连");
                };
            }
        })
    }();
    var putStatusToRank=function(s){
        for(var i=0;i<rank.length;i++){
            if(rank[i].username == s.username){
                rank[i].score[s.pid].push({rid: s.rid,result: s.result,time: s.time});
                break;
            }
        }
        if(i>=rank.length){//没有这个用户
            rank[i]={rank:-1,username: s.username,score:[]};
            for(var j=0;j<pnum;j++){
                rank[i].score[j]=[];
            }
            rank[i].score[s.pid].push({rid: s.rid,result: s.result,time: s.time});
        }
    };
    function cmp(user1,user2){
        if(user1.S==user2.S){
            return user1.W >= user2.W?1:-1;
        }else{
            return user1.S < user2.S?1:-1;
        }
    }
    var rankToHtml=function(){
        //根据当前的rank产生样式，初始化时使用
        //计算所有人的分数
        for(var i=0;i<rank.length;i++){
            computeSW(rank[i]);
        }
        //排序
        rank.sort(cmp);
        //显示
        $main= $(".main");
        rankDynameick.bulidHead(pnum);
        for(var i=0;i<rank.length;i++){
            rank[i].rank=i+1;
            rankDynameick.log(rank[i].username+"["+rank[i].S+"]"+"["+rank[i].W+"]");
            rankDynameick.newRow(rank[i]);
            //$main.append("<div class='row'>"+rank[i].username+"["+rank[i].S+"]"+"["+rank[i].W+"]"+"</div>");
        }
    };
    var computeSW=function(user){
        var S=0;//solved
        var W=0;//wrTime
        for(var i=0;i<pnum;i++){
            var fs=0;
            for(var j=0;j<user.score[i].length;j++){
                var status=user.score[i][j];
                if(status.result==1){
                    S++;
                    fs+=status.time;
                    W+=fs;
                    break;
                }else if(status.result==0){
                    fs+=20*60;//罚时20分钟
                }
            }
        }
        user.S=S;
        user.W=W;
    };
    var putStatus=function(s){
        //有一个新的status状态改变，可能是一个新的提交，或者是提交结果有变化。s:{rid,pid,username,result,time}
        //同步改变rank和网页布局
        //如果这个username没出现过，则发送请求，单独获取它的信息
        rankDynameick.log("接收提交：{rid:"+ s.rid+",pid:"+ s.pid+",username:"+ s.username+",result:"+ s.result +"}");
        var i;
        for(i=0;i<rank.length;i++){
            if(rank[i].username== s.username) break;
        }
        if(i>=rank.length){
            rank[i]={rank:i+1,username: s.username,S:0,W:0,score:[]};
            for(var j=0;j<pnum;j++){
                rank[i].score[j]=[];
            }
            rankDynameick.newRow(rank[i]);
        }
        var j;
        for(j=0;j<rank[i].score[s.pid].length;j++){
            if(rank[i].score[s.pid][j].rid== s.rid){
                break;
            }
        }
        if(j>=rank[i].score[s.pid].length){
            rank[i].score[s.pid].push({rid: s.rid,result: s.result,time: s.time});
        }else{
            rank[i].score[s.pid][j]={rid: s.rid,result: s.result,time: s.time};
        }
        rankDynameick.editCell(s.username, s.pid,rank[i].score[s.pid]);
        computeSW(rank[i]);
        if(s.result==1){
            rankDynameick.editSW(s.username,rank[i].S,rank[i].W);
        }
        var newRow=rank[i];
        var k;
        for(k=i-1;k>=0;k--){
            //alert(rank[k].username);
            if(cmp(newRow,rank[k])==1){
                break;
            }
            rank[k+1]=rank[k];
            rank[k+1].rank=k+2;
        }
        rank[k+1]=newRow;
        rank[k+1].rank=k+2;
        rankDynameick.moveRow(i+1,k+2);
    };
    return {
        send:send
    }
};