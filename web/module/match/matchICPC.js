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
    var init=function(){//刚刚启动时做的初始化工作
        //获取status列表 = {pnum,status:[{rid,pid,username,result,time},...]}
        $.getJSON("/StatusJson.action?cid="+cid,function(data){
            pnum=data.pnum;
            for(var i=0;i<data.status.length;i++){
                putStatusToRank(data.status[i]);
            }
            rankToHtml();
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
    var rankToHtml=function(){//根据当前的rank产生样式，初始化时使用
        //计算所有人的分数
        for(var i=0;i<rank.length;i++){
            computeSW(rank[i]);
        }
        //排序
        rank.sort(function(user1,user2){
            if(user1.S==user2.S){
                return user1.W > user2.W?1:-1;
            }else{
                return user1.S < user2.S?1:-1;
            }
        });
        //显示
        $main= $(".main");
        for(var i=0;i<rank.length;i++){
            $main.append("<div class='row'>"+rank[i].username+"["+rank[i].S+"]"+"["+rank[i].W+"]"+"</div>");
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
        //有一个新的status状态改变，可能是一个新的提交，或者是提交结果有变化。s:{rid,pid,username,result}
        //同步改变rank和网页布局
        //如果这个username没出现过，则发送请求，单独获取它的信息
    };
};