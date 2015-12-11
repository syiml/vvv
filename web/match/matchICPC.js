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
    var head=["rank","username","","S","W"];
    var userInfo={};
    var pnum;
    var ws;
    var width;
    function send(text){
        console.log("Send:"+text);
        ws.send(text);
    }
    var init=function(){
        //刚刚启动时做的初始化工作
        //获取status列表 = {pnum,status:[{rid,pid,username,result,time},...]}
        $.getJSON("StatusJson.action?cid="+cid,function(data){
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
                        rankDynameick.chat_log("<font color='fuchsia'><b>"+data.user+"</b>进入观战<br></font>");
                    }else if(data.type=="logout"){
                        rankDynameick.log("logout:"+data.user);
                        rankDynameick.delOnline(data.user);
                        rankDynameick.chat_log("<font color='fuchsia'><b>"+data.user+"</b>离开观战<br></font>");
                    }else if(data.type=="status"){
                        putStatus(data);
                    }else if(data.type=="chat"){
                        rankDynameick.chat(data.user,data.text);
                    }else if(data.type=="OnlineUser"){
                        for(var i=0;i<data.data.length;i++){
                            rankDynameick.addOnline(data.data[i]);
                        }
                    }else if(data.type=="RegisterUserInfo"){
                        userInfo=data.data;
                        putUserInfo(data.data);
                    }
                };
                ws.onclose = function(event) {
                    console.log("Client notified socket has closed",event);
                    alert("连接已经断开，请刷新重连");
                };
            }
        })
    }();
    function putUserInfo(users){
        for(var u in users){
            var $row=$(".row-"+u);
            var user=users[u];
            for(var colname in user){
                var $cell=$row.find("."+colname);
                if($cell!=null){
                    $cell.text(user[colname]);
                }
            }
        }
    }
    function putStatusToRank(s){
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
    }
    function cmp(user1,user2){
        if(user1.S==user2.S){
            return user1.W >= user2.W?1:-1;
        }else{
            return user1.S < user2.S?1:-1;
        }
    }
    function rankToHtml(){
        //根据当前的rank产生样式，初始化时使用
        //计算所有人的分数
        for(var i=0;i<rank.length;i++){
            computeSW(rank[i]);
        }
        //排序
        rank.sort(cmp);
        //显示
        $main= $(".main");
        width=rankDynameick.bulidHead(pnum,head);
        for(var i=0;i<rank.length;i++){
            rank[i].rank=i+1;
            rankDynameick.log(rank[i].username+"["+rank[i].S+"]"+"["+rank[i].W+"]");
            rankDynameick.newRow(rank[i],head,userInfo,width);
            //$main.append("<div class='row'>"+rank[i].username+"["+rank[i].S+"]"+"["+rank[i].W+"]"+"</div>");
        }
    }
    function computeSW(user){
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
    }
    function putStatus(s){
        //有一个新的status状态改变，可能是一个新的提交，或者是提交结果有变化。s:{rid,pid,username,result,time}
        //同步改变rank和网页布局
        //如果这个username没出现过，则发送请求，单独获取它的信息
        rankDynameick.log("接收提交：{rid:"+ s.rid+",pid:"+ s.pid+",username:"+ s.username+",result:"+ s.result +"}");
        if(s.pid<0|| s.pid>=pnum) return;
        if(s.result==-1){//发送聊天窗口显示
            rankDynameick.chat_log("<font color='#5B18C3'><b>"+s.username+"</b>提交了"+ String.fromCharCode(s.pid+65) +"题</font><br>");
        }else if(s.result==1){
            rankDynameick.chat_log("<font color='green'><b>"+s.username+"</b>的"+ String.fromCharCode(s.pid+65) +"题通过了！</font><br>");
        }else{
            rankDynameick.chat_log("<font color='red'><b>"+s.username+"</b>的"+ String.fromCharCode(s.pid+65) +"题未通过</font><br>");
        }
        var i;
        for(i=0;i<rank.length;i++){//查找rank内已经存在的user
            if(rank[i].username== s.username) break;
        }
        if(i>=rank.length){//没有找到原有user
            rank[i]={rank:i+1,username: s.username,S:0,W:0,score:[]};
            for(var j=0;j<pnum;j++){
                rank[i].score[j]=[];
            }
            rankDynameick.newRow(rank[i],head,userInfo,width);
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
    }
    function refresh(){
        head=["rank","username"];
        if($("#setting-nick").is(':checked'))head.push("nick");
        if($("#setting-name").is(':checked'))head.push("name");
        if($("#setting-gender").is(':checked'))head.push("gender");
        if($("#setting-faculty").is(':checked'))head.push("faculty");
        if($("#setting-major").is(':checked'))head.push("major");
        if($("#setting-cla").is(':checked'))head.push("cla");
        if($("#setting-no").is(':checked'))head.push("no");
        head.push("S");
        head.push("W");
        $main= $(".main").html("");
        rankDynameick.rowNum=0;
        var width=rankDynameick.bulidHead(pnum,head);
        for(var i=0;i<rank.length;i++){
            rank[i].rank=i+1;
            //rankDynameick.log(rank[i].username+"["+rank[i].S+"]"+"["+rank[i].W+"]");
            rankDynameick.newRow(rank[i],head,userInfo,width);
            //$main.append("<div class='row'>"+rank[i].username+"["+rank[i].S+"]"+"["+rank[i].W+"]"+"</div>");
        }
    }
    return {
        send:send,
        refresh:refresh
    }
};